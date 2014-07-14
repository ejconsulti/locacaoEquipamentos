/**
 * Dialog da Ordem de Serviço
 * 
 * @author Érico Jr
 *
 */

package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.OrdemDeServicoTableModel;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.views.DialogReciboDevolucao;
import ejconsulti.locacao.views.PanelRecibos;
import eso.utils.Log;

public class ControladorRecibos implements ActionListener {
	public static final String TAG = ControladorRecibos.class.getSimpleName();
	
	private PanelRecibos panel;
	
	private OrdemDeServicoTableModel model;
	private TableRowSorter<OrdemDeServicoTableModel> sorter;
	
	public ControladorRecibos(){
		initialize();
	}
	
	private void initialize() {
		panel = new PanelRecibos();

		model = new OrdemDeServicoTableModel();
		panel.getTable().setModel(model);

		sorter = new TableRowSorter<OrdemDeServicoTableModel>(model);
		panel.getTable().setRowSorter(sorter);

		addEvents();

		carregar();
	}
	
	private void addEvents() {
		panel.getBtnReciboEntrega().addActionListener(this);
		panel.getBtnReciboDevolucao().addActionListener(this);
		panel.getBtnPesquisar().addActionListener(this);

		panel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
					editar();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu menu = new JPopupMenu();

					JMenuItem reeciboEntrega = new JMenuItem("Recibo de Entrega");
					JMenuItem reciboDevolucao = new JMenuItem("Recibo de Devolução");

					reeciboEntrega.addActionListener(ControladorRecibos.this);
					reciboDevolucao.addActionListener(ControladorRecibos.this);

					menu.add(reeciboEntrega);
					menu.add(reciboDevolucao);

					menu.show(panel.getTable(), e.getX(), e.getY());
				}
			}
		});

		panel.getTxtPesquisar().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					pesquisar();
			}
		});
		panel.getBtnPesquisar().addActionListener(this);
	}
	
	public void carregar() {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(new String[] {"o.*", "c."+Cliente.NOME}, 
					OrdemDeServico.TABLE+" o INNER JOIN "+Cliente.TABLE+" c ON c."+Cliente.ID+" = o."+OrdemDeServico.ID_CLIENTE, 
					null, null, null, OrdemDeServico.DATA_ENTREGA);

			while(rs.next()) {
				OrdemDeServico o = OrdemDeServico.rsToObject(rs);
				o.setNomeCliente(rs.getString(Cliente.NOME));
				model.add(o);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar ordens de serviços", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {}
			}
		}
	}
	
	public void editar() {
		int row = panel.getTable().getSelectedRow();
		
		if(row > -1) {
			int index = panel.getTable().convertRowIndexToModel(row);
			OrdemDeServico o = model.get(index);
			new EditarOrdemDeServico(o);
		}
	}
	
	public void pesquisar() {
		final List<RowFilter<OrdemDeServicoTableModel, Integer>> filters = new ArrayList<RowFilter<OrdemDeServicoTableModel, Integer>>(2);
	
		final String text = panel.getTxtPesquisar().getText();
		if(text.length() > 0) {
			RowFilter<OrdemDeServicoTableModel, Integer> filterNomeCliente = new RowFilter<OrdemDeServicoTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends OrdemDeServicoTableModel, ? extends Integer> entry) {
					OrdemDeServico o = entry.getModel().get(entry.getIdentifier());
					return o.getNomeCliente().toUpperCase().contains(text.toUpperCase());
				}
			};
			filters.add(filterNomeCliente);
		}
		//TODO: Adicionar filtros por status e por data
		
		sorter.setRowFilter(RowFilter.andFilter(filters));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row;
		int index;
		OrdemDeServico o;
		switch(e.getActionCommand()){
			case "Recibo de Entrega":
				row = panel.getTable().getSelectedRow();
				
				if (row < 0)
					return;

				index = panel.getTable().convertRowIndexToModel(row);

				o = model.get(index);
				
				if (o.getStatus() == OrdemDeServico.Status.Cancelada)
					return;
				
				new GerarReciboEntrega(o);
				break;
			case "Recibo de Devolução":
				row = panel.getTable().getSelectedRow();
				
				if (row < 0)
					return;

				index = panel.getTable().convertRowIndexToModel(row);

				o = model.get(index);
				
				if (o.getStatus() == OrdemDeServico.Status.Cancelada)
					return;
				
				if (o.getStatus() == OrdemDeServico.Status.EmAndamento)
					new DevolverProdutos(o);

				break;
		}
	}

	public PanelRecibos getContentPanel() {
		return panel;
	}
}
