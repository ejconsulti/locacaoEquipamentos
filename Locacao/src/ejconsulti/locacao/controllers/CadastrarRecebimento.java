package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cartao;
import ejconsulti.locacao.models.Cheque;
import ejconsulti.locacao.models.Emitente;
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
	
	private HistoricoRecebimentoTableModel model;
	
	private DialogEmitente dialogEmitente;
	
	private DialogCartao dialogCartao;
	
	private DialogCheque dialogCheque;
	
	public CadastrarRecebimento() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogRecebimento(Main.getFrame(), "Cadastrar Recebimento");
		
		dados(true, "Cartão");
		
		model = new HistoricoRecebimentoTableModel();
		dialog.getTable().setModel(model);
		
		addOrdens();
		
		addEmitentes();
		addCartao((Emitente) dialog.getCboxEmitente().getSelectedItem());

		addCartao((Emitente) dialog.getCboxEmitente().getSelectedItem());
		
		addEvents();
		
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
					dialog.getCboxParcelas().setVisible(true);
					dados(true, "Cheque");
					addEmitentes();
					addCheque((Emitente) dialog.getCboxEmitente().getSelectedItem());
				}
				else if (dialog.getCboxTipo().getSelectedIndex() == 0) {
					dialog.getCboxParcelas().setVisible(true);
					dados(true, "Cartão");
					addEmitentes();
					addCartao((Emitente) dialog.getCboxEmitente().getSelectedItem());
				}
				else {
					dialog.getCboxParcelas().setVisible(false);
					dados(false, "Cartão");
				}
			}
		});
		dialog.getCboxEmitente().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dialog.getCboxTipo().getSelectedIndex() == 0) addCartao((Emitente) dialog.getCboxEmitente().getSelectedItem());
				else if (dialog.getCboxTipo().getSelectedIndex() == 2) addCheque((Emitente) dialog.getCboxEmitente().getSelectedItem());
				Emitente emitente = (Emitente) dialog.getCboxEmitente().getSelectedItem();
				dialog.getPanelEmitente().getTxtNomeTitular().setText(emitente.getNome());
				dialog.getPanelEmitente().getTxtCpfCnpj().setText(emitente.getCpfCnpj());
				dialog.getPanelEmitente().getTxtTelefoneTitular().setText(emitente.getTelefone());
			}
		});
		dialog.getCboxCartaoCheque().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (dialog.getCboxTipo().getSelectedIndex() == 0) {
					Cartao cartao = (Cartao) dialog.getCboxCartaoCheque().getSelectedItem();
					dialog.getPanelCartaoCheque().getTxtNomeTitular().setText(cartao.getNome());
					dialog.getPanelCartaoCheque().getTxtNumeroCartaoCheque().setText(cartao.getNumero());
					dialog.getPanelCartaoCheque().getTxtDataVencimento().setText(cartao.getDataVencimento().toString());
					dialog.getPanelCartaoCheque().getTxtBandeiraBanco().setText(cartao.getBandeira());
				}
				else if (dialog.getCboxTipo().getSelectedIndex() == 2) {
					Cheque cheque = (Cheque) dialog.getCboxCartaoCheque().getSelectedItem();
					dialog.getPanelCartaoCheque().getTxtNomeTitular().setText(cheque.getNome());
					dialog.getPanelCartaoCheque().getTxtNumeroCartaoCheque().setText(cheque.getNumero());
					dialog.getPanelCartaoCheque().getTxtDataVencimento().setText(cheque.getDataVencimento().toString());
					dialog.getPanelCartaoCheque().getTxtBandeiraBanco().setText(cheque.getBanco());
				}
			}
		});
		dialog.getBtnAdicionarEmitente().addActionListener(this);
		dialog.getBtnAdicionarCartaoCheque().addActionListener(this);
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
	
	private void addEmitentes(){
		ResultSet rs = null;

		int condition = dialog.getCboxTipo().getSelectedIndex();
		
		try {
			if (dialog.getCboxTipo().getSelectedIndex() == 0)
				rs = DAO.getDatabase().executeQuery("SELECT DISTINCT * FROM " + Emitente.TABLE + " o INNER JOIN " + Cartao.TABLE + " c ON c." + Cartao.ID_EMITENTE + " = o." + Emitente.ID_EMITENTES + " GROUP BY " + Emitente.NOME_EMITENTES, null);
			else if (dialog.getCboxTipo().getSelectedIndex() == 2)
				rs = DAO.getDatabase().executeQuery("SELECT DISTINCT * FROM " + Emitente.TABLE + " o INNER JOIN " + Cheque.TABLE + " c ON c." + Cheque.ID_EMITENTE + " = o." + Emitente.ID_EMITENTES + " GROUP BY " + Emitente.NOME_EMITENTES, null);
			DefaultComboBoxModel<Emitente> model = new DefaultComboBoxModel<Emitente>();

			while(rs.next()) {
				Emitente e = Emitente.rsToObject(rs);
				model.addElement(e);
			}
			dialog.getCboxEmitente().setModel(model);
		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar emitentes", ex);
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
	
	private void addCartao (Emitente e){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Cartao.TABLE, Cartao.ID_EMITENTE + " = ?", new Object[]{e.getId()}, null, null);
		
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			while(rs.next()) {
				Cartao c = Cartao.rsToObject(rs);
				model.addElement(c);
			}
			dialog.getCboxCartaoCheque().setModel(model);
		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar cartões", ex);
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
	
	private void addCheque (Emitente e){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Cheque.TABLE, Cheque.ID_EMITENTE + " = ?", new Object[]{e.getId()}, null, null);
		
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			while(rs.next()) {
				Cheque c = Cheque.rsToObject(rs);
				model.addElement(c);
			}
			dialog.getCboxCartaoCheque().setModel(model);
		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar cheques", ex);
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
	
	public void dados(boolean condition, String tipo) {
		dialog.getLblEmitente().setEnabled(condition);
		dialog.getL6().setEnabled(condition);
		dialog.getCboxEmitente().setEnabled(condition);
		dialog.getBtnAdicionarEmitente().setEnabled(condition);
		dialog.getBtnAdicionarEmitente().setText("Emitente");
		
		dialog.getLblCartaoCheque().setEnabled(condition);
		dialog.getL7().setEnabled(condition);
		dialog.getCboxCartaoCheque().setEnabled(condition);
		dialog.getLblCartaoCheque().setText(tipo);
		dialog.getBtnAdicionarCartaoCheque().setEnabled(condition);
		dialog.getBtnAdicionarCartaoCheque().setText(tipo);
		
		dialog.getPanelEmitente().getLblNome().setEnabled(condition);
		dialog.getPanelEmitente().getLblTelefone().setEnabled(condition);
		dialog.getPanelEmitente().getLblCpfCnpj().setEnabled(condition);
		dialog.getPanelEmitente().getTxtNomeTitular().setEnabled(condition);
		dialog.getPanelEmitente().getTxtNomeTitular().setText("");
		dialog.getPanelEmitente().getTxtTelefoneTitular().setEnabled(condition);
		dialog.getPanelEmitente().getTxtTelefoneTitular().setText("");
		dialog.getPanelEmitente().getTxtCpfCnpj().setEnabled(condition);
		dialog.getPanelEmitente().getTxtCpfCnpj().setText("");
		
		dialog.getPanelCartaoCheque().getLblNome().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblNumero().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblBandeiraBanco().setEnabled(condition);
		dialog.getPanelCartaoCheque().getLblData().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtNomeTitular().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtNomeTitular().setText("");
		dialog.getPanelCartaoCheque().getTxtNumeroCartaoCheque().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtNumeroCartaoCheque().setText("");
		dialog.getPanelCartaoCheque().getTxtBandeiraBanco().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtBandeiraBanco().setText("");
		dialog.getPanelCartaoCheque().getTxtDataVencimento().setEnabled(condition);
		dialog.getPanelCartaoCheque().getTxtDataVencimento().setText("");
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
		if (os != null)
			values.put(Recebimento.ID_ORDEM_SERVICO, os.getId());
		values.put(Recebimento.TIPO, tipo.getId());
		values.put(Recebimento.VALOR_PARCIAL, valorReceber); //TODO: Verificar esta linha (valor parcial é o mesmo do a receber?)
		values.put(Recebimento.VALOR_TOTAL, valorTotal);     // Sim. Nesse caso o registro está sendo criado, então o valor parcial é o valor recebido quando a compra é parcelada.
		values.put(Recebimento.STATUS, status);
		values.put(Recebimento.VALOR_RECEBIMENTO, valorReceber);
		Date dataAtual = new Date();
		SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");
		values.put(Recebimento.DATA_RECEBIMENTO, dia.format(dataAtual.getTime()));
		if (dialog.getCboxTipo().getSelectedIndex() == 0 || dialog.getCboxTipo().getSelectedIndex() == 2) {
			Emitente emitente = (Emitente) dialog.getCboxEmitente().getSelectedItem();
			values.put(Recebimento.ID_EMITENTE, emitente.getId());
		}
		
		int id = -1;
		try {
			id = DAO.getDatabase().insert(Recebimento.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar recebimento.");
		}
		
		if(id < 1)
			return;
		
		if (os != null) {
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
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnRecebimentos().doClick();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dialog.getBtnSalvar()) {
			cadastrar();
		}
		else if (e.getSource() == dialog.getBtnCancelar()) {
			dialog.dispose();
		}
		else if (e.getSource() == dialog.getBtnAdicionarEmitente()) {
			dialogEmitente = new DialogEmitente(dialog, "Adicionar Emitente");
			dialogEmitente.setVisible(true);
			dialogEmitente.getBtnSalvarEmitente().addActionListener(this);
		}
		else if (e.getSource() == dialog.getBtnAdicionarCartaoCheque()) {
			if (dialog.getCboxTipo().getSelectedIndex() == 0) {
				dialogCartao = new DialogCartao(dialog, "Adicionar Cartão de Crédito");
				dialogCartao.setVisible(true);
				dialogCartao.getBtnSalvarCartao().addActionListener(this);
			}
			else if (dialog.getCboxTipo().getSelectedIndex() == 2) {
				dialogCheque = new DialogCheque(dialog, "Adicionar Cheque");
				dialogCheque.setVisible(true);
				dialogCheque.getBtnSalvarCheque().addActionListener(this);
			}
		}
		else if (e.getSource() == dialogCartao.getBtnSalvarCartao()) {
			ContentValues values = new ContentValues();
			values.put(Cartao.NOME_CARTAO, dialogCartao.getJtfNomeTitular().getText());
			values.put(Cartao.NUMERO_CARTAO, dialogCartao.getJtfNumeroCartao().getText());
			values.put(Cartao.DATA_VENCIMENTO_CARTAO, dialogCartao.getDfDataCartao().getDate());
			values.put(Cartao.BANDEIRA_CARTAO, dialogCartao.getCboxBandeira().getSelectedItem());
			Emitente emitente = (Emitente) dialog.getCboxEmitente().getSelectedItem();
			values.put(Cartao.ID_EMITENTE, emitente.getId());
			
			int id = -1;
			try {
				id = DAO.getDatabase().insert(Cartao.TABLE, values);
			} catch (SQLException sqlException) {
				Log.e(TAG, "Erro ao adicionar cartão.");
			}
			
			if(id < 1)
				return;
			dialogCartao.dispose();
			addCartao((Emitente) dialog.getCboxEmitente().getSelectedItem());
		}
		else if (e.getSource() == dialogCheque.getBtnSalvarCheque()) {
			JOptionPane.showMessageDialog(dialogCheque, e.getSource());
			ContentValues values = new ContentValues();
			values.put(Cheque.NOME, dialogCheque.getJtfNomeTitular().getText());
			values.put(Cheque.NUMERO, dialogCheque.getJtfNumeroCheque().getText());
			values.put(Cheque.DATA, dialogCheque.getDfDataCheque().getDate());
			values.put(Cheque.BANCO, dialogCheque.getCboxBanco().getSelectedItem());
			Emitente emitente = (Emitente) dialog.getCboxEmitente().getSelectedItem();
			values.put(Cheque.ID_EMITENTE, emitente.getId());
			
			int id = -1;
			try {
				id = DAO.getDatabase().insert(Cheque.TABLE, values);
			} catch (SQLException sqlException) {
				Log.e(TAG, "Erro ao adicionar cheque.");
			}
			
			if(id < 1)
				return;
			dialogCheque.dispose();
			addCheque((Emitente) dialog.getCboxEmitente().getSelectedItem());
		}
		else if (e.getSource() == dialogEmitente.getBtnSalvarEmitente()) {
			ContentValues values = new ContentValues();
			values.put(Emitente.NOME_EMITENTES, dialogEmitente.getJtfNomeTitular().getText());
			if (dialogEmitente.getCpf().isSelected())
				values.put(Emitente.CPF_CNPJ_EMITENTES, dialogEmitente.getJtfCpfTitular().getText());
			else if (dialogEmitente.getCnpj().isSelected())
				values.put(Emitente.CPF_CNPJ_EMITENTES, dialogEmitente.getJtfCnpjTitular().getText());
			values.put(Emitente.TELEFONE_EMITENTES, dialogEmitente.getJtfTelefoneTitular().getText());
			
			int id = -1;
			try {
				id = DAO.getDatabase().insert(Emitente.TABLE, values);
			} catch (SQLException sqlException) {
				Log.e(TAG, "Erro ao adicionar emitente.");
			}
			
			if(id < 1)
				return;
			dialogEmitente.dispose();
			addEmitentes();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}
}
