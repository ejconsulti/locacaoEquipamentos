package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Funcionario;
import ejconsulti.locacao.views.DialogFuncionario;
import eso.database.ContentValues;
import eso.utils.Log;
import eso.utils.Text;

/**
 * Cadastrar funcionário
 * 
 * @author Edison Jr
 *
 */
public class CadastrarFuncionario implements ActionListener {
	public static final String TAG = CadastrarFuncionario.class.getSimpleName();
	
	private DialogFuncionario dialog;

	public CadastrarFuncionario() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogFuncionario(Main.getFrame(), "Cadastrar funcionario");
		dialog.getDataEntrada().setDate(new Date(System.currentTimeMillis()));
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void cadastrar() {
		
		// Verificar campos obrigatórios
		
		String nome = dialog.getTxtNome().getText().trim();
		if(nome.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Nome'.");
			dialog.getTxtNome().requestFocus();
			return;
		}
		String telefone = Text.toString(dialog.getTxtTelefone().getValue()); // Apenas dígitos
		if(telefone == null) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Telefone'.");
			dialog.getTxtTelefone().requestFocus();
			return;
		}
		String salario = dialog.getTxtSalario().getText(); // Apenas dígitos
		if(salario.isEmpty()) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Salário'.");
			dialog.getTxtSalario().requestFocus();
			return;
		}
		Date dataEntrada = dialog.getDataEntrada().getDate();
		if(dataEntrada == null) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Data de entrada'.");
			dialog.getDataEntrada().requestFocus();
			return;
		}
		
		// Cadastrar endereço
		
		int idEndereco = ControladorEndereco.cadastrar(dialog, dialog.getPanelEndereco());
		if(idEndereco < 1)
			return; // Endereço não cadastrado
		
		Date previsaoFerias = dialog.getPrevisaoFerias().getDate();
		if(previsaoFerias == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataEntrada);
			while(cal.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR))
				cal.add(Calendar.YEAR, 1);
			previsaoFerias = new Date(cal.getTimeInMillis());
		}
		// Cadastrar funcionário
		ContentValues values = new ContentValues();
		values.put(Funcionario.NOME, nome);
		values.put(Funcionario.RG, dialog.getTxtRg().getText().trim());
		values.put(Funcionario.CPF, Text.toString(dialog.getTxtCpf().getValue())); // Apenas números
		values.put(Funcionario.TELEFONE, telefone);
		values.put(Funcionario.SALARIO, dialog.getTxtSalario().doubleValue());
		values.put(Funcionario.DATA_ENTRADA, dataEntrada);
		values.put(Funcionario.DIA_PAGAMENTO, dialog.getSpnDiaPagamento().getValue());
		values.put(Funcionario.PREVISAO_FERIAS, previsaoFerias);
		values.put(Funcionario.ID_ENDERECO, idEndereco);
		try {
			DAO.getDatabase().insert(Funcionario.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar funcionário");
			return;
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnFuncionarios().doClick();
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
