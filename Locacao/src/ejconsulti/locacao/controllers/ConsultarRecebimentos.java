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
import javax.swing.table.TableRowSorter;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.HistoricoRecebimento;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.RecebimentoTableModel;
import ejconsulti.locacao.views.PanelConsultar;
import eso.database.SQLiteDatabase;
import eso.utils.Log;

public class ConsultarRecebimentos implements ActionListener {
	public static final String TAG = ConsultarRecebimentos.class.getSimpleName();
	
	private PanelConsultar panel;
	
	private RecebimentoTableModel model;
	private TableRowSorter<RecebimentoTableModel> sorter;
	
	public ConsultarRecebimentos(){
		initialize();
	}
	
	private void initialize() {
		panel = new PanelConsultar();
		
		model = new RecebimentoTableModel();
		panel.getTable().setModel(model);
		
		sorter = new TableRowSorter<RecebimentoTableModel>(model);
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
		  
		            editar.addActionListener(ConsultarRecebimentos.this);
		            excluir.addActionListener(ConsultarRecebimentos.this);
		            
		            menu.add(editar);
		            menu.add(excluir);
		            
		            menu.show(panel.getTable(), e.getX(), e.getY());
		        }
			}
		});
		
		panel.getTxtPesquisar().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER);
					//pesquisar();
			}
		});
		panel.getBtnPesquisar().addActionListener(this);
	}
	
	public void carregar() {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Recebimento.TABLE, null, null, null, null);
			
			while(rs.next()) {
				Recebimento r = Recebimento.rsToObject(rs);
				model.add(r);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar recebimentos", ex);
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
			Recebimento r = model.get(index);
			new EditarRecebimento(r);
		}
	}
	
	public void excluir() {
		
		int[] delRows = panel.getTable().getSelectedRows();
		
		if(delRows.length > 0) {
			
			int option = JOptionPane.showConfirmDialog(panel, 
					String.format("Deseja realmente excluir %d recebimentos(s)?", delRows.length), "Excluir recebimentos(s)", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.YES_OPTION) {
				
				// Remover da base
				SQLiteDatabase helper = DAO.getDatabase();
				for(int row : delRows) {
					// Converte a linha do filtro para a linha do modelo
					int index = panel.getTable().convertRowIndexToModel(row);
					try {
						Recebimento r = model.get(index);
						helper.delete(Recebimento.TABLE, Recebimento.ID+" = ?", r.getId());
						helper.delete(HistoricoRecebimento.TABLE, HistoricoRecebimento.ID_RECEBIMENTO+" = ?", r.getId());
					} catch (Exception e) {
						Log.e(TAG, "Erro ao exluir recebimento na linha "+index, e);
					}
				}
				
				// Recarregar lista
				model.clear();
				carregar();
			}
		}
	}
	/*
	public void pesquisar() {
		final String text = panel.getTxtPesquisar().getText();
		if(text.length() > 0) {
			RowFilter<RecebimentoTableModel, Integer> filter = new RowFilter<RecebimentoTableModel, Integer>() {			
				@Override
				public boolean include(RowFilter.Entry<? extends RecebimentoTableModel, ? extends Integer> entry) {
					Recebimento r = entry.getModel().get(entry.getIdentifier());
					return r.getNome().toUpperCase().contains(text.toUpperCase());
				}
			};
			sorter.setRowFilter(filter);
		}
	}
	*/
	@Override
	public void actionPerformed(ActionEvent evt) {
		switch(evt.getActionCommand()) {
		case "Adicionar":
			new CadastrarRecebimento();
			break;
		case "Editar":
			editar();
			break;
		case "Excluir":
			excluir();
			break;
		case "Pesquisar":
			//pesquisar();
			break;
		}
	}
	
	public PanelConsultar getContentPanel() {
		return panel;
	}
}
