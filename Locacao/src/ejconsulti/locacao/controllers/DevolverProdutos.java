package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.views.DialogReciboDevolucao;
import eso.database.ContentValues;
import eso.utils.Log;

public class DevolverProdutos implements ActionListener, TableModelListener{
	public static final String TAG = DevolverProdutos.class.getSimpleName();

	private DialogReciboDevolucao dialog;

	private OrdemDeServico ordem;

	public DevolverProdutos(OrdemDeServico ordem) {
		initialize(ordem);
	}

	private void initialize(OrdemDeServico ordem) {
		dialog = new DialogReciboDevolucao(Main.getFrame(), "Recibo de Devolução");
		this.ordem = ordem;

		addEvents();
		carregarProdutos(ordem);
		carregarCliente();
		addEvents();
		
		dialog.getTxtDataEntrega().setDate(ordem.getData());
		dialog.getTxtDataDevolucao().setDate(new Date(System.currentTimeMillis()));
		
		dialog.getTxtDataEntrega().setEditable(false);
		dialog.getCboxNome().setEnabled(false);
		dialog.getTxtTelefone().setEditable(false);
		dialog.getTxtTotal().setEditable(false);
		dialog.setVisible(true);
	}
	

	private void addEvents() {
		dialog.getBtnAdicionar().addActionListener(this);
		dialog.getBtnExcluir().addActionListener(this);
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtTotal().addActionListener(this);
		dialog.getProdutoOrdemTableModel().addTableModelListener(this);
	}
	
	private void carregarCliente() {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(new String[]{Cliente.NOME, Cliente.TELEFONE}, 
					Cliente.TABLE, Cliente.ID+" = ?", new Integer[]{ordem.getIdCliente()}, null, null);
			
			if(rs.next()) {
				dialog.getTxtTelefone().setText(rs.getString(Cliente.TELEFONE));
				dialog.getCboxNome().setSelectedItem(rs.getString(Cliente.NOME));
			}
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao carregar cliente", e);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private void carregarProdutos(OrdemDeServico ordem) {
		ResultSet rs = null;
		try {
			//Carrega produtos da ordem de serviço
			rs = DAO.getDatabase().select(null, ProdutoOS.VIEW, ProdutoOS.LOCADO+" = 1 AND " + ProdutoOS.ID_ORDEMSERVICO+" = ?", new Object[]{ordem.getId()}, null, null);

			DefaultComboBoxModel<Produto> model = (DefaultComboBoxModel<Produto>)dialog.getCboxProdutos().getModel();
			while(rs.next()){
				ProdutoOS p = ProdutoOS.rsToObject(rs);
				model.addElement(p);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar produtos", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {}
			}
		}		
	}

	public void adicionar() {
		Produto p = (Produto) dialog.getCboxProdutos().getSelectedItem();
		if(p != null) {		
			ResultSet rs = null;
			try {
				//Carrega produtos da ordem de serviço
				rs = DAO.getDatabase().select(null, ProdutoOS.VIEW, ProdutoOS.ID+" = ?", new Object[]{p.getId()}, null, null);

				if (rs.next()){
					dialog.getProdutoOrdemTableModel().add(new ProdutoOS(p, p.getQuantidade(), rs.getInt("dias"), 1));
				}

			} catch (SQLException ex) {
				Log.e(TAG, "Erro ao adicionar produto", ex);
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {}
				}
			}	
			
		}
	}
	
	
	public void excluir() {
		int row = dialog.getTabela().getSelectedRow();
		if (row >= 0)
			dialog.getProdutoOrdemTableModel().remove(row);
	}
	
	private void salvar() {
		int row = dialog.getProdutoOrdemTableModel().getRowCount();
		
		for (int i = 0; i < row; i++){
			try {
				//altera o status dos produtos locados
				ProdutoOS p = dialog.getProdutoOrdemTableModel().get(i);
				ContentValues contValues = new ContentValues();
				contValues.put(ProdutoOS.LOCADO, 0);
				contValues.put(ProdutoOS.DATA_DEVOLUCAO, new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).toString());
				DAO.getDatabase().update(ProdutoOS.TABLE, contValues, ProdutoOS.ID+" = ? and " + ProdutoOS.ID_ORDEMSERVICO+" = ?", new Object[]{p.getId(), ordem.getId()});
			} catch (Exception e) {
				Log.e(TAG, "Erro ao Devolver produtos ", e);
			}
		}
		
		//verifica se existe algum produto sem devolver na ordem. Se a ordem já foi paga, altera status para concluído. 
		//Se não, altera o status para Pagamento Pendente
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().executeQuery("SELECT * FROM v_produtoslocados WHERE IDORDEMSERVICO = ? AND LOCADO = 1", new Object[]{ordem.getId()});
			
			if (!rs.next()){
				if (ordem.getRecebimento() == 0){
					ContentValues contValues = new ContentValues();
					contValues.put(OrdemDeServico.STATUS, Status.PagamentoPendente.getId());
					DAO.getDatabase().update(OrdemDeServico.TABLE, contValues, OrdemDeServico.ID+" = ?", new Object[]{ordem.getId()});
				}
				else{
					ContentValues contValues = new ContentValues();
					contValues.put(OrdemDeServico.STATUS, Status.Concluida.getId());
					DAO.getDatabase().update(OrdemDeServico.TABLE, contValues, OrdemDeServico.ID+" = ?", new Object[]{ordem.getId()});
				}
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao devolver produtos", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {}
			}
		}
		
		Main.getFrame().getBtnRecibos().doClick();
		new GerarReciboDevolucao(ordem, dialog.getProdutoOrdemTableModel().getRows());
		
		dialog.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Adicionar":
			adicionar();
			break;
		case "Excluir":
			excluir();
			break;
		case "Salvar":
			salvar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
		
	}
	
	public void tableChanged(TableModelEvent e) {
		double total = 0;
		for(ProdutoOS p : dialog.getProdutoOrdemTableModel().getRows())
			total += p.getTotal();
		dialog.getTxtTotal().setValue(total);
	}
}
