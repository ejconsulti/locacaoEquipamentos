package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.HistoricoRecebimentoTableModel;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.Recebimento.Tipo;
import ejconsulti.locacao.views.DialogCartao;
import ejconsulti.locacao.views.DialogCheque;
import ejconsulti.locacao.views.DialogEmitente;
import ejconsulti.locacao.views.DialogRecebimento;
import eso.database.ContentValues;
import eso.utils.Log;

public class CadastrarRecebimento implements ActionListener, FocusListener {
public static final String TAG = CadastrarRecebimento.class.getSimpleName();
	
	private DialogRecebimento dialog;
	
	public DialogCheque dialogCheque;
	
	private HistoricoRecebimentoTableModel model;
	
	public CadastrarRecebimento() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogRecebimento(Main.getFrame(), "Cadastrar Recebimento");
		
		dados(true, "Cartão", "Adicionar Cartão");
		
		model = new HistoricoRecebimentoTableModel();
		dialog.getTable().setModel(model);
		
		addEvents();
		
		addOrdens();

		if (dialog.getCboxOrdemServico().getModel().getSize() == 0)
			JOptionPane.showMessageDialog(dialog, "Sem ordem de serviço a pagar.");
		else
			dialog.setVisible(true);
		
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtValorReceber().addFocusListener(this);
		dialog.getCboxOrdemServico().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OrdemServico os = (OrdemServico) dialog.getCboxOrdemServico().getSelectedItem();
				if(os != null) {
					dialog.getTxtValorTotal().setValue(os.getValor());
					dialog.getTxtValorTotal().setEditable(false);
				}
				else {
					dialog.getTxtValorTotal().setText("");
				}
			}
		});
		dialog.getCboxTipo().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dialog.getCboxTipo().getSelectedIndex() == 2) {
					dados(true, "Cheque", "Adicionar Cheque");
					new DialogCartao(Main.getFrame(), "Adicionar Cartão").setVisible(true);
				}
				else if (dialog.getCboxTipo().getSelectedIndex() == 0) {
					dados(true, "Cartão", "Adicionar Cartão");
				}
				else {
					dados(false, "Cartão", "Adicionar Cartão");
				}
			}
		});
	}
	
	private void addOrdens (){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, OrdemServico.TABLE, OrdemServico.STATUS + " IN (1, 2) AND " + OrdemServico.RECEBIMENTO + " = 0", null, null, null);
		
			DefaultComboBoxModel<OrdemServico> model = (DefaultComboBoxModel<OrdemServico>) dialog.getCboxOrdemServico().getModel();
			model.addElement(null);
			while(rs.next()) {
				OrdemServico o = OrdemServico.rsToObject(rs);
				model.addElement(o);
			}
			
		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar ordens de serviço", ex);
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
	
	public void dados(boolean condition, String tipo, String button) {
		dialog.getLblEmitente().setEnabled(condition);
		dialog.getL6().setEnabled(condition);
		dialog.getCboxEmitente().setEnabled(condition);
		//dialog.getBtnAdicionarEmitente().setEnabled(condition);
		
		dialog.getLblCartaoCheque().setEnabled(condition);
		dialog.getL7().setEnabled(condition);
		dialog.getCboxCartaoCheque().setEnabled(condition);
		dialog.getLblCartaoCheque().setText(tipo);
		//dialog.getBtnAdicionarCartaoCheque().setEnabled(condition);
		//dialog.getBtnAdicionarCartaoCheque().setText(button);
		
		dialog.getPanelEmitente().getLblNome().setEnabled(condition);
		dialog.getPanelEmitente().getLblTelefone().setEnabled(condition);
		dialog.getPanelEmitente().getLblCpfCnpj().setEnabled(condition);
		dialog.getPanelEmitente().getTxtNomeTitular().setEnabled(condition);
		dialog.getPanelEmitente().getTxtTelefoneTitular().setEnabled(condition);
		dialog.getPanelEmitente().getTxtCpfCnpj().setEnabled(condition);
		
		dialog.getPanelCartaoCheque().getLblNome().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblNumero().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblBandeiraBanco().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblData().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtNomeTitular().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtNumeroCartaoCheque().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtBandeiraBanco().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtDataVencimento().setEnabled(condition);
	}
	
	private void cadastrar() {
		// Verificar campos obrigat�rios
		OrdemServico os = (OrdemServico) dialog.getCboxOrdemServico().getSelectedItem();
		Tipo tipo = (Tipo) dialog.getCboxTipo().getSelectedItem();
		if(tipo == null) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher o tipo de entrada.");
			dialog.getCboxTipo().requestFocus();
			return;
		}
		String valorRecebimento = dialog.getTxtValorReceber().getText();
		if(valorRecebimento.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor a receber'.");
			dialog.getTxtValorReceber().requestFocus();
			return;
		}
		String strValorTotal = dialog.getTxtValorTotal().getText();
		if(strValorTotal.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor total'.");
			dialog.getTxtValorTotal().requestFocus();
			return;
		}
		double valorTotal = dialog.getTxtValorTotal().doubleValue();
		double valorReceber = dialog.getTxtValorReceber().doubleValue(); 
		Recebimento.Status status = Recebimento.Status.NaoFinalizado;
		if (valorReceber == dialog.getTxtValorTotal().doubleValue())
			status = Recebimento.Status.Finalizado;
		else if (dialog.getTxtValorReceber().doubleValue() > dialog.getTxtValorTotal().doubleValue()) {
			JOptionPane.showMessageDialog(dialog, "Quantidade a receber maior que quantidade total. Favor corrigir.");
			dialog.getTxtValorReceber().requestFocus();
			return;
		}
		
		// Cadastrar recebimento
		ContentValues values = new ContentValues();
		values.put(Recebimento.ID_ORDEM_SERVICO, os.getId());
		values.put(Recebimento.TIPO, tipo.getId());
		values.put(Recebimento.VALOR_PARCIAL, valorReceber); //TODO: Verificar esta linha (valor parcial é o mesmo do a receber?)
		values.put(Recebimento.VALOR_TOTAL, valorTotal);     // Sim. Nesse caso o registro está sendo criado, então o valor parcial é o valor recebido quando a compra é parcelada.
		values.put(Recebimento.STATUS, status);
		values.put(Recebimento.VALOR_RECEBIMENTO, valorReceber);
		values.put(Recebimento.DATA_RECEBIMENTO, new Date());
		
		int id = -1;
		try {
			id = DAO.getDatabase().insert(Recebimento.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar recebimento.");
		}
		
		if(id < 1)
			return;
		
		ResultSet rs = null;
		List<ProdutoOS> list = null;
		OrdemServico ordem = null;
		try {
			rs = DAO.getDatabase().select(null, ProdutoOS.TABLE, ProdutoOS.ID_ORDEMSERVICO + " = ?", new Object[]{os.getId()}, null, null);
			list = new ArrayList<ProdutoOS>();
			while (rs.next()) {
				ProdutoOS produto = ProdutoOS.rsToObject(rs);
				list.add(produto);
			}
			
			new GerarRecibo(ordem, list);
		} catch (SQLException b) {
			Log.e(TAG, "Erro ao buscar produtos da OS.");
		}  finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnRecebimentos().doClick();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Salvar":
			cadastrar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}
}
