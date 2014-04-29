package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.Funcionario;
import ejconsulti.locacao.views.DialogFuncionario;
import ejconsulti.locacao.views.PanelEndereco;
import eso.database.ContentValues;
import eso.utils.Log;
import eso.utils.Text;

/**
 * Editar funcionário
 * 
 * @author Edison Jr
 *
 */
public class EditarFuncionario implements ActionListener {
	public static final String TAG = EditarFuncionario.class.getSimpleName();
	
	private DialogFuncionario dialog;

	private Funcionario funcionario;
	private Endereco endereco;
	
	public EditarFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogFuncionario(Main.getFrame(), "Editar funcionario");
		dialog.getTxtNome().setText(funcionario.getNome());
		dialog.getTxtRg().setText(funcionario.getRg());
		dialog.getTxtCpf().setValue(funcionario.getCpf());
		dialog.getTxtTelefone().setValue(funcionario.getTelefone());
		dialog.getTxtSalario().setValue(funcionario.getSalario());
		dialog.getDataEntrada().setDate(funcionario.getDataEntrada());
		dialog.getSpnDiaPagamento().setValue(funcionario.getDiaPagamento());
		dialog.getPrevisaoFerias().setDate(funcionario.getPrevisaoFerias());
		
		endereco = ControladorEndereco.getEndereco(funcionario.getIdEndereco());
		PanelEndereco pEnd = dialog.getPanelEndereco();
		pEnd.getBoxUf().setSelectedItem(endereco.getUf());
		pEnd.getTxtCidade().setText(endereco.getCidade().getNome());
		pEnd.getTxtBairro().setText(endereco.getBairro().getNome());
		pEnd.getTxtRua().setText(endereco.getRua().getNome());
		pEnd.getTxtNumero().setText(endereco.getNumero());
		pEnd.getTxtComplemento().setText(endereco.getComplemento());
		pEnd.getTxtReferencia().setText(endereco.getReferencia());
		
		addEvents();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}
	
	private void editar() {
		
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
		
		Date previsaoFerias = dialog.getPrevisaoFerias().getDate();
		if(previsaoFerias == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataEntrada);
			while(cal.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR))
				cal.add(Calendar.YEAR, 1);
			previsaoFerias = new Date(cal.getTimeInMillis());
		}
		// Editar endereço
		
		int result = ControladorEndereco.editar(dialog, dialog.getPanelEndereco(), endereco);
		if(result < 1)
			return;
		
		// Editar funcionario
		ContentValues values = new ContentValues();
		values.put(Funcionario.NOME, nome);
		values.put(Funcionario.RG, dialog.getTxtRg().getText().trim());
		values.put(Funcionario.CPF, Text.toString(dialog.getTxtCpf().getValue())); // Apenas números
		values.put(Funcionario.TELEFONE, telefone);
		values.put(Funcionario.SALARIO, dialog.getTxtSalario().doubleValue()); // Converter separadores monetários para o formato padrão
		values.put(Funcionario.DATA_ENTRADA, dataEntrada);
		values.put(Funcionario.DIA_PAGAMENTO, dialog.getSpnDiaPagamento().getValue());
		values.put(Funcionario.PREVISAO_FERIAS, previsaoFerias);
		try {
			DAO.getDatabase().update(Funcionario.TABLE, values, Funcionario.ID+" = ?", funcionario.getId());
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar funcionário");
			return;
		}
		
		dialog.dispose();
		
		// Atualizar da tabela
		Main.getFrame().getBtnFuncionarios().doClick();
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
