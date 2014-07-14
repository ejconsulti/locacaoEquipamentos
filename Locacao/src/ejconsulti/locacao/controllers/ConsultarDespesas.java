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
import ejconsulti.locacao.models.Caixa;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.models.DespesaTableModel;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

public class ConsultarDespesas implements ActionListener {
	public static final String TAG = ConsultarDespesas.class.getSimpleName();
	
	private PanelConsultar panel;
	private DespesaTableModel model;
	private TableRowSorter<DespesaTableModel> sorter;
	
	public ConsultarDespesas() {
		initialize();
	}
	
	private void initialize() {
		panel = new PanelConsultar();
		
		model = new DespesaTableModel();
		panel.getTable().setModel(model);
		
		sorter = new TableRowSorter<DespesaTableModel>(model);
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
		  
		            editar.addActionListener(ConsultarDespesas.this);
		            excluir.addActionListener(ConsultarDespesas.this);
		            
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
			rs = DAO.getDatabase().select(null, Despesa.TABLE, null, null, null, Despesa.ID_DESPESA);
			
			while(rs.next()) {
				Despesa d = Despesa.rsToObject(rs);
				model.add(d);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar despesas", ex);
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
			Despesa d = model.get(index);
			new EditarDespesa(d);
		}
	}
	
	public void excluir() {
		
		int[] delRows = panel.getTable().getSelectedRows();
		
		if(delRows.length > 0) {
			
			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente excluir %d despesa(s)?", delRows.length), "Excluir despesa(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.YES_OPTION) {
				
				// Remover da base
				SQLiteDatabase helper = DAO.getDatabase();
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					try {
						Despesa d = model.get(index);
						helper.delete(Caixa.TABLE, Caixa.ID_SAIDA+" = ?", d.getId());
						helper.delete(Despesa.TABLE, Despesa.ID_DESPESA+" = ?", d.getId());
					} catch (Exception e) {
						Log.e(TAG, "Erro ao exluir despesa na linha "+index, e);
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
			RowFilter<DespesaTableModel, Integer> filter = new RowFilter<DespesaTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends DespesaTableModel, ? extends Integer> entry) {
					Despesa d = entry.getModel().get(entry.getIdentifier());
					return d.getNome().toUpperCase().contains(text.toUpperCase());
				}
			};
			sorter.setRowFilter(filter);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarDespesa();
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
