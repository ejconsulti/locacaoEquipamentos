package ejconsulti.locacao.controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.OrdemDeServicoTableModel;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoTableModel;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.ContentValues;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

/**
 * Consultar ordem de servico
 * 
 * @author Érico Jr
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
		
		final TableCellRenderer renderer = panel.getTable().getDefaultRenderer(OrdemDeServico.class);
		
		panel.getTable().setDefaultRenderer(OrdemDeServico.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				//Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
			//	if (row > 0)
				System.out.println("i'm here!");
					//c.setBackground(Color.RED);
				return null;
			}
		});

		panel.getBtnEditar().setText("Visualisar");
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
					visualisar();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu menu = new JPopupMenu();

					JMenuItem visualisar = new JMenuItem("Visualisar");
					JMenuItem cancelar = new JMenuItem("Cancelar");

					visualisar.addActionListener(ConsultarOrdemDeServico.this);
					cancelar.addActionListener(ConsultarOrdemDeServico.this);

					menu.add(visualisar);
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
			rs = DAO.getDatabase().select(null, OrdemDeServico.TABLE, null, null, null, OrdemDeServico.DATA);

			while(rs.next()) {
				OrdemDeServico o = OrdemDeServico.rsToObject(rs);
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

	public void visualisar() {
		int row = panel.getTable().getSelectedRow();
		
		if (row < 0)
			return;

		int index = panel.getTable().convertRowIndexToModel(row);

		OrdemDeServico o = model.get(index);

		new VisualizarOrdemDeServico(o);
	}

	public void cancelar() {

		int[] delRows = panel.getTable().getSelectedRows();


		if(delRows.length > 0) {

			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente cancelar %d Ordem(s) de Serviço(s)?", delRows.length), 
					"Excluir Ordem(s) de Serviço(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(option == JOptionPane.YES_OPTION) {
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				ArrayList<Integer> quantidades = new ArrayList<Integer>();

				// Remover da base
				SQLiteDatabase db = DAO.getDatabase();
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					
					if (!model.get(index).getStatus().equals("Cancelada")){
						try {
							//altera o status da ordem de serviço
							OrdemDeServico o = model.get(index);
							ContentValues contValues = new ContentValues();
							contValues.put(OrdemDeServico.STATUS, "Cancelada");
							db.update(OrdemDeServico.TABLE, contValues, OrdemDeServico.ID + "=?", new Object[]{o.getId()});
						} catch (Exception e) {
							Log.e(TAG, "Erro ao exluir ordem de serviço na linha "+index, e);
						}

						//Busca os produtos locados e as quantidades
						ResultSet rs = null;
						ResultSet rsProduto = null;					
						try {
							rs = DAO.getDatabase().select(null, "PRODUTOSLOCADOS", OrdemDeServico.ID + "=?", 
									new Integer[]{model.get(index).getId()}, null, null);

							while (rs.next()){
								rsProduto = DAO.getDatabase().select(null, Produto.TABLE, 
										Produto.ID + "=?", new Integer[]{rs.getInt(Produto.ID)}, null, null);
								Produto p = Produto.rsToObject(rsProduto);
								produtos.add(p);
								quantidades.add(rs.getInt("QUANTIDADE"));
								rsProduto.close();
							}
							rs.close();
							
						} catch (SQLException e) {
							Log.e(TAG, "Erro ao buscar produtos.");
						}
						
						
					}
					

					//Aumenta o estoque dos produtos contidos da ordem cancelada
					for (int i = 0; i < produtos.size(); i++){
						CadastrarOrdemDeServico.alterarEstoque(produtos.get(i), -quantidades.get(i));
					}
				}

				// Recarregar lista
				model.clear();
				carregar();
			}
		}
	}
	

	public void pesquisar() {
		final String text = panel.getTxtPesquisar().getText();
		if(text.length() > 0) {
			RowFilter<OrdemDeServicoTableModel, Integer> filter = new RowFilter<OrdemDeServicoTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends OrdemDeServicoTableModel, ? extends Integer> entry) {
					OrdemDeServico p = entry.getModel().get(entry.getIdentifier());
					Cliente c = null;
					ResultSet rs = null;
					try {
						rs = DAO.getDatabase().select(null, Cliente.TABLE, Cliente.ID + "=?", new Integer[]{p.getIdCliente()}, null, null);
						c = Cliente.rsToObject(rs);
					}catch(SQLException e){
						e.printStackTrace();
					}finally{
						if (rs != null)
							try {
								rs.close();
							} catch (SQLException e) {}
					}

					return c.getNome().toUpperCase().contains(text.toUpperCase()) || p.getData().contains(text.toUpperCase());
				}
			};
			sorter.setRowFilter(filter);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarOrdemDeServico();
			break;
		case "Visualisar":
			visualisar();
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
