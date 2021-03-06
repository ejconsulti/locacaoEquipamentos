package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.OrdemServico.Status;
import ejconsulti.locacao.models.OrdemServicoTableModel;
import ejconsulti.locacao.models.ValorCellRenderer;
import ejconsulti.locacao.views.PanelConsultarOS;
import eso.database.ContentValues;
import eso.utils.Log;

/**
 * Consultar ordem de servico
 * 
 * @author �rico Jr
 * @author Edison Jr
 *
 */
public class ConsultarOrdemServico implements ActionListener {
	public static final String TAG = ConsultarOrdemServico.class.getSimpleName();

	private PanelConsultarOS panel;

	private OrdemServicoTableModel model;
	private TableRowSorter<OrdemServicoTableModel> sorter;

	public ConsultarOrdemServico() {
		initialize();
	}

	private void initialize() {
		panel = new PanelConsultarOS();

		model = new OrdemServicoTableModel();
		panel.getTable().setModel(model);

		sorter = new TableRowSorter<OrdemServicoTableModel>(model);
		panel.getTable().setRowSorter(sorter);

		panel.getBtnExcluir().setText("Cancelar");

		addEvents();

		carregar();
		
		//Organizar colunas
		TableColumnModel model = panel.getTable().getColumnModel();
		model.getColumn(OrdemServicoTableModel.CODIGO.getIndex()).setPreferredWidth(30);
		model.getColumn(OrdemServicoTableModel.NOME_CLIENTE.getIndex()).setPreferredWidth(400);
		model.getColumn(OrdemServicoTableModel.DATA.getIndex()).setPreferredWidth(50);
		TableColumn c = model.getColumn(OrdemServicoTableModel.VALOR_TOTAL.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		model.getColumn(OrdemServicoTableModel.STATUS.getIndex()).setPreferredWidth(70);
	}

	private void addEvents() {
		panel.getBtnAdicionar().addActionListener(this);
		panel.getBtnEditar().addActionListener(this);
		panel.getBtnExcluir().addActionListener(this);
		panel.getBtnReciboEntrega().addActionListener(this);
		panel.getBtnReciboDevolucao().addActionListener(this);
		panel.getBtnFiltrar().addActionListener(this);

		panel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
					editar();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
			
					JPopupMenu menu = new JPopupMenu();

					JMenuItem editar = new JMenuItem("Editar");
					editar.addActionListener(ConsultarOrdemServico.this);
					menu.add(editar);
					
					JMenuItem cancelar = new JMenuItem("Cancelar");
					cancelar.addActionListener(ConsultarOrdemServico.this);
					menu.add(cancelar);

					JMenu recibo = new JMenu("Recibo");
					menu.add(recibo);
					
					JMenuItem reeciboEntrega = new JMenuItem("Entrega");
					reeciboEntrega.setActionCommand("Recibo de Entrega");
					reeciboEntrega.addActionListener(ConsultarOrdemServico.this);
					recibo.add(reeciboEntrega);
					
					JMenuItem reciboDevolucao = new JMenuItem("Devolução");
					reciboDevolucao.setActionCommand("Recibo de Devolução");
					reciboDevolucao.addActionListener(ConsultarOrdemServico.this);
					recibo.add(reciboDevolucao);

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
					OrdemServico.TABLE+" o INNER JOIN "+Cliente.TABLE+" c ON c."+Cliente.ID+" = o."+OrdemServico.ID_CLIENTE, 
					null, null, null, OrdemServico.DATA_ENTREGA);

			while(rs.next()) {
				OrdemServico o = OrdemServico.rsToObject(rs);
				o.setNomeCliente(rs.getString(Cliente.NOME));
				model.add(o);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar ordens de servi�os", ex);
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
			OrdemServico o = model.get(index);
			new EditarOrdemServico(o);
		}
	}

	public void cancelar() {

		int[] delRows = panel.getTable().getSelectedRows();

		if(delRows.length > 0) {

			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente cancelar %d Ordem(s) de Servi�o(s)?", delRows.length), 
					"Cancelar Ordem(s) de Servi�o(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(option == JOptionPane.YES_OPTION) {

				// Cancelar Ordens de Servi�o
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					
					OrdemServico o = model.get(index);
					if(o.getStatus().getId() > 0) {
						try {
							//altera o status da ordem de servi�o
							ContentValues contValues = new ContentValues();
							contValues.put(OrdemServico.STATUS, Status.Cancelada.getId());
							DAO.getDatabase().update(OrdemServico.TABLE, contValues, OrdemServico.ID+" = ?", new Object[]{o.getId()});
						} catch (Exception e) {
							Log.e(TAG, "Erro ao cancelar ordem de servi�o na linha "+index, e);
						}
					}
				}

				// Recarregar lista
				model.clear();
				carregar();
			}
		}
	}
	
	private void reciboEntrega() {
		int row = panel.getTable().getSelectedRow();
		
		if(row > -1) {
			int index = panel.getTable().convertRowIndexToModel(row);
			OrdemServico o = model.get(index);

			o = model.get(index);

			if (o.getStatus() == OrdemServico.Status.Cancelada)
				JOptionPane.showMessageDialog(panel, "Ordem de serviço cancelada.");
			else 
				new GerarReciboEntrega(o);
		}
	}
	
	private void reciboDevolucao() {
		int row = panel.getTable().getSelectedRow();
		
		if(row > -1) {
			int index = panel.getTable().convertRowIndexToModel(row);
			OrdemServico o = model.get(index);

			o = model.get(index);

			if (o.getStatus() == OrdemServico.Status.EmAndamento || 
					o.getStatus() == OrdemServico.Status.DevolucaoPendente)
				new DevolverProdutos(o);
			else
				JOptionPane.showMessageDialog(panel, "Ordem de serviço "+o.getStatus().toString().toLowerCase());
		}
	}

	public void pesquisar() {
		final String text = panel.getTxtPesquisar().getText();
		sorter.setRowFilter(new RowFilter<OrdemServicoTableModel, Integer>() {			
			@Override
			public boolean include(RowFilter.Entry<? extends OrdemServicoTableModel, ? extends Integer> entry) {
				OrdemServico o = entry.getModel().get(entry.getIdentifier());
				return o.getNomeCliente().toUpperCase().contains(text.toUpperCase());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarOrdemServico();
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
		case "Recibo de Entrega":
			reciboEntrega();
			break;
		case "Recibo de Devolução":
			reciboDevolucao();
			break;
		}
	}

	public PanelConsultarOS getContentPanel() {
		return panel;
	}

}
