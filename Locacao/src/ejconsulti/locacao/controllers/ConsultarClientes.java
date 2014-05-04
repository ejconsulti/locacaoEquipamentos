package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.ClienteTableModel;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

/**
 * Consultar clientes
 * 
 * @author Edison Jr
 *
 */
public class ConsultarClientes implements ActionListener {
	public static final String TAG = ConsultarClientes.class.getSimpleName();
	
	private PanelConsultar panel;
	
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;
	
	public ConsultarClientes(){
		initialize();
	}
	
	private void initialize() {
		panel = new PanelConsultar();
		
		model = new ClienteTableModel();
		panel.getTable().setModel(model);
		
		sorter = new TableRowSorter<ClienteTableModel>(model);
		panel.getTable().setRowSorter(sorter);
		
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
				} else if (e.getButton() == MouseEvent.BUTTON3) {
		            JPopupMenu menu = new JPopupMenu();
		            
		            JMenuItem editar = new JMenuItem("Editar");
		            JMenuItem excluir = new JMenuItem("Excluir");
		  
		            editar.addActionListener(ConsultarClientes.this);
		            excluir.addActionListener(ConsultarClientes.this);
		            
		            menu.add(editar);
		            menu.add(excluir);
		            
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
			rs = DAO.getDatabase().select(null, Cliente.TABLE, null, null, null, Cliente.NOME);
			
			while(rs.next()) {
				Cliente o = Cliente.rsToObject(rs);
				model.add(o);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar clientes", ex);
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
			// Converte a linha do filtro para a linha do modelo
			int index = panel.getTable().convertRowIndexToModel(row);
			Cliente c = model.get(index);
			new EditarCliente(c);
		}
	}
	
	public void excluir() {
		
		int[] delRows = panel.getTable().getSelectedRows();
		
		if(delRows.length > 0) {
			
			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente excluir %d cliente(s)?", delRows.length), "Excluir cliente(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.YES_OPTION) {
				
				// Remover da base
				SQLiteDatabase helper = DAO.getDatabase();
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					try {
						Cliente c = model.get(index);
						helper.delete(Cliente.TABLE, Cliente.ID+" = ?", c.getId());
						helper.delete(Endereco.TABLE, Endereco.ID+" = ", c.getIdEndereco());
						if(c.getIdEndereco() != c.getIdEnderecoEntrega())
							helper.delete(Endereco.TABLE, Endereco.ID+" = ", c.getIdEnderecoEntrega());
					} catch (Exception e) {
						Log.e(TAG, "Erro ao exluir cliente na linha "+index, e);
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
			RowFilter<ClienteTableModel, Integer> filter = new RowFilter<ClienteTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends ClienteTableModel, ? extends Integer> entry) {
					Cliente c = entry.getModel().get(entry.getIdentifier());
					return c.getNome().toUpperCase().contains(text.toUpperCase());
				}
			};
			sorter.setRowFilter(filter);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarCliente();
			break;
		case "Editar":
			editar();
			break;
		case "Excluir":
			excluir();
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
