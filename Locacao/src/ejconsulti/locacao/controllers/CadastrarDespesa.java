package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.views.DialogDespesa;
import eso.database.ContentValues;
import eso.utils.Log;

/**
 * Cadastrar cliente
 * 
 * @author Edison Jr
 *
 */
public class CadastrarDespesa implements ActionListener {
	public static final String TAG = CadastrarDespesa.class.getSimpleName();
	
	private DialogDespesa dialog;

	public CadastrarDespesa() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogDespesa(Main.getFrame(), "Cadastrar Despesa");
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void cadastrar() {
		
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
		Despesa.Status status = (Despesa.Status) dialog.getCboxStatus().getSelectedItem();
		if (status == null) {
			JOptionPane.showMessageDialog(dialog, "Favor escolher um status.");
			dialog.getCboxStatus().requestFocus();
			return;
		}
		Despesa.Tipo tipo = (Despesa.Tipo) dialog.getCboxTipo().getSelectedItem();
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
			DAO.getDatabase().insert(Despesa.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar despesa");
			return;
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnDespesas().doClick();
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

}
