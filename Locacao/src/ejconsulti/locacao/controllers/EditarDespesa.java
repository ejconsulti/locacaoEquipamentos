package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.models.Despesa.Status;
import ejconsulti.locacao.models.Despesa.Tipo;
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
		dialog.getCboxStatus().setSelectedItem(despesa.getStatus());
		dialog.getCboxTipo().setSelectedItem(despesa.getTipo());
		
		if (despesa.getStatus() == Despesa.Status.Pago) {
			dialog.getTxtNome().setEnabled(false);
			dialog.getTxtDescricao().setEnabled(false);
			dialog.getDataPagamento().setEnabled(false);
			dialog.getTxtValor().setEnabled(false);
			dialog.getCboxStatus().setEnabled(false);
			dialog.getCboxTipo().setEnabled(false);
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
		Date data = dialog.getDataPagamento().getDate();
		if (data == null) {
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
		Status status = (Status) dialog.getCboxStatus().getSelectedItem();
		if (status == null) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher um status.");
			dialog.getCboxStatus().requestFocus();
			return;
		}
		Tipo tipo = (Tipo) dialog.getCboxTipo().getSelectedItem();
		if (tipo == null) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher um tipo.");
			dialog.getCboxTipo().requestFocus();
			return;
		}
				// Cadastrar despesa
		ContentValues values = new ContentValues();
		values.put(Despesa.NOME, nome);
		values.put(Despesa.DESCRICAO, dialog.getTxtDescricao().getText().trim());
		values.put(Despesa.DATA_PAGAMENTO, data);
		values.put(Despesa.VALOR, dialog.getTxtValor().doubleValue());
		values.put(Despesa.STATUS, status.getId());
		values.put(Despesa.TIPO, tipo.getId());
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
