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
			DAO.getDatabase().insert(Despesa.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar despesa");
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
			cadastrar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

}
