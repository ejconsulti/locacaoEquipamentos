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
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.OrdemDeServicoTableModel;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.ContentValues;
import eso.utils.Log;

/**
 * Consultar ordem de servico
 * 
 * @author Érico Jr
 * @author Edison Jr
 *
 */
public class ConsultarOrdemDeServico implements ActionListener {
	public static final String TAG = ConsultarOrdemDeServico.class.getSimpleName();

	private PanelConsultar panel;

	private OrdemDeServicoTableModel model;
	private TableRowSorter<OrdemDeServicoTableModel> sorter;

	public ConsultarOrdemDeServico(){
		initialize();
	}

	private void initialize() {
		panel = new PanelConsultar();

		model = new OrdemDeServicoTableModel();
		panel.getTable().setModel(model);

		sorter = new TableRowSorter<OrdemDeServicoTableModel>(model);
		panel.getTable().setRowSorter(sorter);

		panel.getBtnExcluir().setText("Cancelar");

		addEvents();

		carregar();
	}

	private void addEvents() {
		panel.getBtnAdicionar().addActionListener(this);
		panel.getBtnEditar().addActionListener(this);
		panel.getBtnExcluir().addActionListener(this);

		panel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
					editar();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu menu = new JPopupMenu();

					JMenuItem editar = new JMenuItem("Editar");
					JMenuItem cancelar = new JMenuItem("Cancelar");

					editar.addActionListener(ConsultarOrdemDeServico.this);
					cancelar.addActionListener(ConsultarOrdemDeServico.this);

					menu.add(editar);
					menu.add(cancelar);

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

	public void cancelar() {

		int[] delRows = panel.getTable().getSelectedRows();

		if(delRows.length > 0) {

			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente cancelar %d Ordem(s) de Serviço(s)?", delRows.length), 
					"Cancelar Ordem(s) de Serviço(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(option == JOptionPane.YES_OPTION) {

				// Cancelar Ordens de Serviço
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					
					OrdemDeServico o = model.get(index);
					if(o.getStatus().getId() > 0) {
						try {
							//altera o status da ordem de serviço
							ContentValues contValues = new ContentValues();
							contValues.put(OrdemDeServico.STATUS, Status.Cancelada.getId());
							DAO.getDatabase().update(OrdemDeServico.TABLE, contValues, OrdemDeServico.ID+" = ?", new Object[]{o.getId()});
						} catch (Exception e) {
							Log.e(TAG, "Erro ao cancelar ordem de serviço na linha "+index, e);
						}
					}
				}

				// Recarregar lista
				model.clear();
				carregar();
			}
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
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarOrdemDeServico();
			break;
		case "Editar":
			editar();
			break;
		case "Cancelar":
			cancelar();
			break;
		case "Pesquisar":
			pesquisar();
			break;
		}	
	}

	public PanelConsultar getContentPanel() {
		return panel;
	}

}
