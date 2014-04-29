package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.views.DialogCliente;
import eso.database.ContentValues;
import eso.utils.Log;
import eso.utils.Text;

/**
 * Cadastrar cliente
 * 
 * @author Edison Jr
 *
 */
public class CadastrarCliente implements ActionListener {
	public static final String TAG = CadastrarCliente.class.getSimpleName();
	
	private DialogCliente dialog;

	public CadastrarCliente() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogCliente(Main.getFrame(), "Cadastrar cliente");
		
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
		
		// Cadastrar endereço
		
		int idEndereco = ControladorEndereco.cadastrar(dialog, dialog.getPanelEndereco());
		if(idEndereco < 1)
			return; // Endereço não cadastrado
		
		int idEnderecoEntrega = idEndereco;
		
		// Se o endereco de entrega não é o mesmo endereço
		if(dialog.getCbEntregarOutroEnd().isSelected()) {
			
			// Cadastrar endereco de entrega
			
			idEnderecoEntrega = ControladorEndereco.cadastrar(dialog, dialog.getPanelEnderecoEntrega());
			if(idEnderecoEntrega < 1)
				return; // Endereço não cadastrado
		}
		
		// Cadastrar cliente
		ContentValues values = new ContentValues();
		values.put(Cliente.NOME, nome);
		values.put(Cliente.RG, dialog.getTxtRg().getText().trim());
		values.put(Cliente.CPF, Text.toString(dialog.getTxtCpf().getValue())); // Apenas números
		values.put(Cliente.TELEFONE, telefone);
		values.put(Cliente.EMAIL, dialog.getTxtEmail().getText().trim());
		values.put(Cliente.ID_ENDERECO, idEndereco);
		values.put(Cliente.ID_ENDERECO_ENTREGA, idEnderecoEntrega);
		try {
			DAO.getDatabase().insert(Cliente.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar cliente");
			return;
		}
		
		dialog.dispose();
		
		// Atualização da tabela
		Main.getFrame().getBtnClientes().doClick();
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
