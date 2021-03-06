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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.OrdemServico.Status;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.ProdutoOSTableModel;
import ejconsulti.locacao.models.ValorCellRenderer;
import ejconsulti.locacao.views.DialogReciboDevolucao;
import eso.database.ContentValues;
import eso.utils.Log;

public class DevolverProdutos implements ActionListener, TableModelListener{
	public static final String TAG = DevolverProdutos.class.getSimpleName();

	private DialogReciboDevolucao dialog;

	private OrdemServico ordem;

	public DevolverProdutos(OrdemServico ordem) {
		initialize(ordem);
	}

	private void initialize(OrdemServico ordem) {
		dialog = new DialogReciboDevolucao(Main.getFrame(), "Recibo de Devolu��o");
		this.ordem = ordem;

		addEvents();
		carregarProdutos(ordem);
		carregarCliente();
		addEvents();
		
		//Organizar colunas
		TableColumnModel model = dialog.getTabela().getColumnModel();
		model.getColumn(ProdutoOSTableModel.NOME.getIndex()).setPreferredWidth(300);
		TableColumn c = model.getColumn(ProdutoOSTableModel.VALOR_DIARIO.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		c = model.getColumn(ProdutoOSTableModel.VALOR_MENSAL.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		model.getColumn(ProdutoOSTableModel.QUANTIDADE.getIndex()).setPreferredWidth(50);
		model.getColumn(ProdutoOSTableModel.DIAS.getIndex()).setPreferredWidth(50);
		c = model.getColumn(ProdutoOSTableModel.TOTAL.getIndex());
		c.setPreferredWidth(70);
		c.setCellRenderer(new ValorCellRenderer());
		
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
	
	private void carregarProdutos(OrdemServico ordem) {
		ResultSet rs = null;
		try {
			//Carrega produtos da ordem de servi�o
			rs = DAO.getDatabase().select(null, ProdutoOS.VIEW, ProdutoOS.LOCADO+" = 1 AND " + ProdutoOS.ID_ORDEMSERVICO+" = ?", new Object[]{ordem.getId()}, null, null);

			DefaultComboBoxModel<ProdutoOS> model = (DefaultComboBoxModel<ProdutoOS>) dialog.getCboxProdutos().getModel();
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
		ProdutoOS p = (ProdutoOS) dialog.getCboxProdutos().getSelectedItem();
		if(p != null)
			dialog.getProdutoOrdemTableModel().add(p);
	}
	
	
	public void excluir() {
		int row = dialog.getTabela().getSelectedRow();
		if (row >= 0)
			dialog.getProdutoOrdemTableModel().remove(row);
	}
	
	private void salvar() {		
		for (ProdutoOS p : dialog.getProdutoOrdemTableModel().getRows()) {
			try {
				//altera o status dos produtos locados
				ContentValues contValues = new ContentValues();
				contValues.put(ProdutoOS.LOCADO, 0);
				contValues.put(ProdutoOS.DATA_DEVOLUCAO, new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()).toString());
				DAO.getDatabase().update(ProdutoOS.TABLE, contValues, ProdutoOS.ID+" = ? and " + ProdutoOS.ID_ORDEMSERVICO+" = ?", new Object[]{p.getId(), ordem.getId()});
			} catch (Exception e) {
				Log.e(TAG, "Erro ao Devolver produto: "+p.getNome(), e);
			}
		}
		
		//verifica se existe algum produto sem devolver na ordem. Se a ordem j� foi paga, altera status para conclu�do. 
		//Se n�o, altera o status para Pagamento Pendente
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().executeQuery("SELECT * FROM v_produtoslocados WHERE IDORDEMSERVICO = ? AND LOCADO > 0", new Object[]{ordem.getId()});
			
			if (!rs.next()){
				if (ordem.getRecebimento() == 0) {
					ContentValues contValues = new ContentValues();
					contValues.put(OrdemServico.STATUS, Status.PagamentoPendente.getId());
					DAO.getDatabase().update(OrdemServico.TABLE, contValues, OrdemServico.ID+" = ?", new Object[]{ordem.getId()});
				}
				else{
					ContentValues contValues = new ContentValues();
					contValues.put(OrdemServico.STATUS, Status.Concluida.getId());
					DAO.getDatabase().update(OrdemServico.TABLE, contValues, OrdemServico.ID+" = ?", new Object[]{ordem.getId()});
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
		
		Main.getFrame().getBtnOrdemdeServico().doClick();
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
