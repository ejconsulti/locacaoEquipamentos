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
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.Recebimento.Tipo;
import ejconsulti.locacao.views.DialogRecebimento;
import eso.database.ContentValues;
import eso.utils.Log;

public class EditarRecebimento implements ActionListener {
public static final String TAG = EditarRecebimento.class.getSimpleName();
	
	private DialogRecebimento dialog;
	
	private Recebimento recebimento;

	double valorPendente;
	
	public EditarRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
		initialize(recebimento);
	}
	
	private void initialize(Recebimento recebimento) {
		dialog = new DialogRecebimento(Main.getFrame(), "Editar Recebimento");
		dialog.getCboxOrdemServico().setEnabled(false);
		dialog.getCboxTipo().setSelectedItem(recebimento.getTipo());
		valorPendente = recebimento.getValorTotal() - recebimento.getValorParcial();
		dialog.getTxtValorTotal().setValue(valorPendente);
		dialog.getTxtValorTotal().setFocusable(false);
		
		if (recebimento.getStatus() == Recebimento.Status.Finalizado) {
			dialog.getCboxTipo().setEnabled(false);
			dialog.getTxtValorReceber().setEnabled(false);
			dialog.getTxtValorTotal().setEnabled(false);
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
		
		Tipo tipo = (Tipo) dialog.getCboxTipo().getSelectedItem();
		if(tipo == null) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher o tipo de entrada.");
			dialog.getCboxTipo().requestFocus();
			return;
		}
		String ValorRecebimento = dialog.getTxtValorReceber().getText();
		if(ValorRecebimento.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor à receber'.");
			dialog.getTxtValorReceber().requestFocus();
			return;
		}
		double valor = dialog.getTxtValorReceber().doubleValue();
		int status = 1;
		if (valor > valorPendente) {
			//TODO: Mostrar troco
			JOptionPane.showMessageDialog(dialog, "Valor parcial mais valor recebido maior que o pendente.");
			dialog.getTxtValorReceber().requestFocus();
			return;
		} else if (valor == valorPendente) {
			status = 0;
		}
		// Cadastrar produto
		ContentValues values = new ContentValues();
		values.put(Recebimento.ID_ORDEM_SERVICO, recebimento.getIdOrdemServico());
		values.put(Recebimento.TIPO, tipo.getId());
		values.put(Recebimento.VALOR_PARCIAL, valor + recebimento.getValorParcial());
		values.put(Recebimento.VALOR_TOTAL, recebimento.getValorTotal());
		values.put(Recebimento.STATUS, status);
		values.put(Recebimento.VALOR_RECEBIMENTO, dialog.getTxtValorReceber().doubleValue());
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
