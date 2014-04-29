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
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.Funcionario;
import ejconsulti.locacao.models.FuncionarioTableModel;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

/**
 * Consultar funcionarios
 * 
 * @author Edison Jr
 *
 */
public class ConsultarFuncionarios implements Runnable, ActionListener {
	public static final String TAG = ConsultarFuncionarios.class.getSimpleName();
	
	private PanelConsultar panel;
	
	private FuncionarioTableModel model;
	private TableRowSorter<FuncionarioTableModel> sorter;
	
	public ConsultarFuncionarios(){
		initialize();
	}
	
	private void initialize() {
		panel = new PanelConsultar();
		
		model = new FuncionarioTableModel();
		panel.getTable().setModel(model);

		sorter = new TableRowSorter<FuncionarioTableModel>(model);
		panel.getTable().setRowSorter(sorter);
		
		addEvents();
		
		Thread t = new Thread(this);
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
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
		            JMenuItem excluir = new JMenuItem("Excluir");
		  
		            editar.addActionListener(ConsultarFuncionarios.this);
		            excluir.addActionListener(ConsultarFuncionarios.this);
		            
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
	
	public void run() {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Funcionario.TABLE, null, null, null, Funcionario.NOME);
			
			while(rs.next()) {
				Funcionario o = Funcionario.rsToObject(rs);
				model.add(o);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar funcionarios", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void editar() {
		int row = panel.getTable().getSelectedRow();
		
		if(row > -1) {
			// Converte a linha do filtro para a linha do modelo
			int index = panel.getTable().convertRowIndexToModel(row);
			Funcionario f = model.get(index);
			new EditarFuncionario(f);
		}
	}
	
	public void excluir() {

		int[] delRows = panel.getTable().getSelectedRows();
		
		if(delRows.length > 0) {
			
			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente excluir %d funcionário(s)?", delRows.length), "Excluir funcionário(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.YES_OPTION) {

				// Remover da base
				SQLiteDatabase db = DAO.getDatabase();
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					try {
						Funcionario f = model.get(index);
						db.delete(Funcionario.TABLE, Funcionario.ID+" = ?", f.getId());
						db.delete(Endereco.TABLE, Endereco.ID+" = ", f.getIdEndereco());
					} catch (Exception e) {
						Log.e(TAG, "Erro ao exluir funcionário na linha "+index, e);
					}
				}
				
				// Recarregar lista
				model.clear();
				Thread t = new Thread(this);
				t.setPriority(Thread.MIN_PRIORITY);
				t.start();
			}
		}
	}
	
	public void pesquisar() {
		final String text = panel.getTxtPesquisar().getText();
		if(text.length() > 0) {
			RowFilter<FuncionarioTableModel, Integer> filter = new RowFilter<FuncionarioTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends FuncionarioTableModel, ? extends Integer> entry) {
					Funcionario f = entry.getModel().get(entry.getIdentifier());
					return f.getNome().toUpperCase().contains(text.toUpperCase());
				}
			};
			sorter.setRowFilter(filter);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarFuncionario();
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
