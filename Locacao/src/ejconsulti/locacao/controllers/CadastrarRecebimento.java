package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.HistoricoRecebimentoTableModel;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.views.DialogRecebimento;
import eso.database.ContentValues;
import eso.utils.Log;

public class CadastrarRecebimento implements ActionListener, FocusListener {
public static final String TAG = CadastrarRecebimento.class.getSimpleName();
	
	private DialogRecebimento dialog;
	
	private HistoricoRecebimentoTableModel model;
	
	private boolean flag = false;
	
	private List<ProdutoOS> produtoList;
	
	public CadastrarRecebimento() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogRecebimento(Main.getFrame(), "Cadastrar Recebimento");
		
		model = new HistoricoRecebimentoTableModel();
		dialog.getTable().setModel(model);
		
		addEvents();
		
		addOrdens();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtQuantidadeReceber().addFocusListener(this);
		dialog.getJcbOrdemServico().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ResultSet rs = null;
				try {
					rs = DAO.getDatabase().select(null, OrdemDeServico.TABLE, OrdemDeServico.ID + " = " + dialog.getJcbOrdemServico().getSelectedItem(), null, null, null);
					OrdemDeServico ordem = OrdemDeServico.rsToObject(rs);
					dialog.getTxtQuantidadeTotal().setValue(ordem.getValor());
					dialog.getTxtQuantidadeTotal().setFocusable(false);
				}
				catch (SQLException ex) {
					JOptionPane.showMessageDialog(dialog, "ERRO!");
				}
				finally {
					if(rs != null) {
						try {
							rs.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
	}
	
	private void addOrdens (){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, OrdemDeServico.TABLE, OrdemDeServico.STATUS + " IN (1, 2) AND " + OrdemDeServico.RECEBIMENTO + " = 0", null, null, null);
		
			DefaultComboBoxModel<OrdemDeServico> model = (DefaultComboBoxModel<OrdemDeServico>) dialog.getJcbOrdemServico().getModel();
			while(rs.next()) {
				OrdemDeServico o = OrdemDeServico.rsToObject(rs);
				model.addElement(o);
				flag = true;
			}
			
			
		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar recebimentos", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		if (flag == false) {
			JOptionPane.showMessageDialog(dialog, "Sem ordem de serviço a pagar. =D");
		}
	}
	
	private void cadastrar() {
		int id;
		// Verificar campos obrigat�rios
		if (flag == false) {
			JOptionPane.showMessageDialog(dialog, "Sem ordem de serviço a pagar.");
			dialog.getJcbOrdemServico().requestFocus();
			return;
		}
		int tipoRecebimento = dialog.getJcbTipo().getSelectedIndex();
		if(tipoRecebimento == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher o tipo de entrada.");
			dialog.getJcbTipo().requestFocus();
			return;
		}
		String quantidadeRecebimento = dialog.getTxtQuantidadeReceber().getText();
		if(quantidadeRecebimento.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Quantidade a receber'.");
			dialog.getTxtQuantidadeReceber().requestFocus();
			return;
		}
		String quantidadeTotal = dialog.getTxtQuantidadeTotal().getText();
		if(quantidadeTotal.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Quantidade total'.");
			dialog.getTxtQuantidadeTotal().requestFocus();
			return;
		}
		int status = 1;
		if (dialog.getTxtQuantidadeReceber().doubleValue() == dialog.getTxtQuantidadeTotal().doubleValue()) {
			status = 0;
		}
		else if (dialog.getTxtQuantidadeReceber().doubleValue() > dialog.getTxtQuantidadeTotal().doubleValue()) {
			JOptionPane.showMessageDialog(dialog, "Quantidade a receber maior que quantidade total. Favor corrigir.");
			dialog.getTxtQuantidadeReceber().requestFocus();
			return;
		}
		
		// Cadastrar recebimento
		int ident = Integer.parseInt(dialog.getJcbOrdemServico().getSelectedItem().toString());
		ContentValues values = new ContentValues();
		values.put(Recebimento.ID_ORDEM_SERVICO, ident);
		values.put(Recebimento.TIPO, tipoRecebimento);
		values.put(Recebimento.QUANTIDADE_PARCIAL, dialog.getTxtQuantidadeReceber().doubleValue());
		values.put(Recebimento.QUANTIDADE_TOTAL, dialog.getTxtQuantidadeTotal().doubleValue());
		values.put(Recebimento.STATUS, status);
		values.put(Recebimento.QUANTIDADE_RECEBIMENTO, dialog.getTxtQuantidadeReceber().doubleValue());
		Date dataAtual = new Date();
		SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");
		values.put(Recebimento.DATA_RECEBIMENTO, dia.format(dataAtual.getTime()));
		try {
			
			id = DAO.getDatabase().insert(Recebimento.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar recebimento");
			JOptionPane.showMessageDialog(dialog, e.getMessage());
			e.printStackTrace();
			return;
		}
		
		dialog.dispose();
		ResultSet rs2 = null;
		Recebimento rec = null;
		OrdemDeServico ordem = null;
		try {
			rs2 = DAO.getDatabase().select(null, Recebimento.TABLE, Recebimento.ID + " = ?", new Object[]{id}, null, null);
			rec = Recebimento.rsToObject(rs2);
			rs2 = DAO.getDatabase().select(null, OrdemDeServico.TABLE, OrdemDeServico.ID + " = ?", new Object[]{rec.getIdOrdemServico()}, null, null);
			ordem = OrdemDeServico.rsToObject(rs2);
			rs2 = DAO.getDatabase().select(null, ProdutoOS.TABLE, ProdutoOS.ID_ORDEMSERVICO + " = ?", new Object[]{ordem.getId()}, null, null);
			while (rs2.next()) {
				ProdutoOS produto = ProdutoOS.rsToObject(rs2);
				produtoList.add(produto);
			}
			rs2.close();
		}
		catch (SQLException b) {
			
		}
		
		new GerarRecibo(ordem, rec, produtoList);
		
		// Atualiza��o da tabela
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
