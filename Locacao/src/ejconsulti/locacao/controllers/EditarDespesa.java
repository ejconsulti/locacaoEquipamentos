package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.views.DialogDespesa;
import eso.database.ContentValues;
import eso.utils.Log;

public class EditarDespesa implements ActionListener {
	public static final String TAG = EditarDespesa.class.getSimpleName();
	
	private DialogDespesa dialog;

	private Despesa despesa;
	
	public EditarDespesa(Despesa despesa) {
		this.despesa = despesa;
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogDespesa(Main.getFrame(), "Editar Despesa");
		dialog.getTxtNome().setText(despesa.getNome());
		dialog.getTxtDescricao().setText(despesa.getDescricao());
		dialog.getDataPagamento().setDate(despesa.getDataPagamento());
		dialog.getTxtValor().setValue(despesa.getValor());
		dialog.getJcbStatus().setSelectedIndex(despesa.getStatus());
		dialog.getJcbTipo().setSelectedIndex(despesa.getTipo());
		
		if (despesa.getStatus() == 1) {
			dialog.getTxtNome().setEnabled(false);
			dialog.getTxtDescricao().setEnabled(false);
			dialog.getDataPagamento().setEnabled(false);
			dialog.getTxtValor().setEnabled(false);
			dialog.getJcbStatus().setEnabled(false);
			dialog.getJcbTipo().setEnabled(false);
			dialog.getBtnSalvar().setEnabled(false);
		}
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void editar() {
		
		// Verificar campos obrigat�rios
		String nome = dialog.getTxtNome().getText().trim();
		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Nome'.");
			dialog.getTxtNome().requestFocus();
			return;
		}
				
		String data = dialog.getDataPagamento().getText().trim();
		if (data.equals("__/__/____")) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Data Pagamento'.");
			dialog.getDataPagamento().requestFocus();
			return;
		}
				
		String valor = dialog.getTxtValor().getText().trim();
		if (valor.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Valor'.");
			dialog.getTxtValor().requestFocus();
			return;
		}
				
		int status = dialog.getJcbStatus().getSelectedIndex();
		if (status == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher uma opção de status.");
			dialog.getJcbStatus().requestFocus();
			return;
		}
				
		int tipo = dialog.getJcbTipo().getSelectedIndex();
		if (tipo == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher uma opção de tipo.");
			dialog.getJcbTipo().requestFocus();
			return;
		}
				// Cadastrar despesa
		ContentValues values = new ContentValues();
		values.put(Despesa.NOME, dialog.getTxtNome().getText().trim());
		values.put(Despesa.DESCRICAO, dialog.getTxtDescricao().getText().trim());
		values.put(Despesa.DATA_PAGAMENTO, dialog.getDataPagamento().getDate());
		values.put(Despesa.VALOR, dialog.getTxtValor().doubleValue());
		values.put(Despesa.STATUS, dialog.getJcbStatus().getSelectedIndex());
		values.put(Despesa.TIPO, dialog.getJcbTipo().getSelectedIndex());
		try {
			DAO.getDatabase().update(Despesa.TABLE, values, Despesa.ID_DESPESA+" = ?", despesa.getId());
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar despesa");
			return;
		}
			
		dialog.dispose();
		
		// Atualiza��o da tabela
		Main.getFrame().getBtnDespesas().doClick();
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
