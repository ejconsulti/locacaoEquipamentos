package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.HistoricoRecebimento;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.views.DialogRecebimento;
import eso.database.ContentValues;
import eso.utils.Log;

public class EditarRecebimento implements ActionListener {
public static final String TAG = EditarRecebimento.class.getSimpleName();
	
	private DialogRecebimento dialog;
	
	private Recebimento recebimento;

	double value;
	
	ArrayList<Produto> produto = new ArrayList<Produto>();
	
	public EditarRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
		initialize(recebimento);
	}
	
	private void initialize(Recebimento recebimento) {
		dialog = new DialogRecebimento(Main.getFrame(), "Editar Recebimento");
		dialog.getJcbOrdemServico().setEnabled(false);
		dialog.getJcbTipo().setSelectedIndex(recebimento.getTipo());
		value = recebimento.getQuantidadeTotal() - recebimento.getQuantidadeParcial();
		dialog.getTxtQuantidadeTotal().setValue(value);
		dialog.getTxtQuantidadeTotal().setFocusable(false);
		if (recebimento.getStatus() == 0) {
			dialog.getJcbTipo().setEnabled(false);
			dialog.getTxtQuantidadeReceber().setEnabled(false);
			dialog.getTxtQuantidadeTotal().setEnabled(false);
			dialog.getBtnSalvar().setEnabled(false);
		}
		addEvents();
		
		addHistorico(recebimento);
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void editar() {
		
		// Verificar campos obrigat�rios
		
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
		double valor = dialog.getTxtQuantidadeReceber().doubleValue();
		int status = 1;
		if (valor > value) {
			JOptionPane.showMessageDialog(dialog, "Valor parcial mais valor recebido maior que o total. Favor corrigir.");
			dialog.getTxtQuantidadeReceber().requestFocus();
			return;
		}
		else if (valor == value) {
			status = 0;
		}
		// Cadastrar produto
		ContentValues values = new ContentValues();
		values.put(Recebimento.ID_ORDEM_SERVICO, recebimento.getIdOrdemServico());
		values.put(Recebimento.TIPO, tipoRecebimento);
		values.put(Recebimento.QUANTIDADE_PARCIAL, valor + recebimento.getQuantidadeParcial());
		values.put(Recebimento.QUANTIDADE_TOTAL, recebimento.getQuantidadeTotal());
		values.put(Recebimento.STATUS, status);
		values.put(Recebimento.QUANTIDADE_RECEBIMENTO, dialog.getTxtQuantidadeReceber().doubleValue());
		Date dataAtual = new Date();
		SimpleDateFormat dia = new SimpleDateFormat("yyyy-MM-dd");  
		values.put(Recebimento.DATA_RECEBIMENTO, dia.format(dataAtual.getTime()));
		try {
			DAO.getDatabase().update(Recebimento.TABLE, values, Recebimento.ID+" = ?", recebimento.getId());
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar recebimento");
			JOptionPane.showMessageDialog(dialog, e.getMessage());
			return;
		}
		
		dialog.dispose();
		//new GerarRecibo(ordem, produto, recebimento);
		
		// Atualizar da tabela
		Main.getFrame().getBtnRecebimentos().doClick();
	}
	
	private void addHistorico(Recebimento recebimento) {
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, HistoricoRecebimento.TABLE, HistoricoRecebimento.ID_RECEBIMENTO + " = ?", new Object[]{recebimento.getId()}, null, null);
			List<HistoricoRecebimento> list = new ArrayList<HistoricoRecebimento>();
			while(rs.next()){
				HistoricoRecebimento p = HistoricoRecebimento.rsToObject(rs);
				list.add(p);
			}
			dialog.getHistoricoRecebimentoTableModel().addAll(list);

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Salvar":
			editar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

}
