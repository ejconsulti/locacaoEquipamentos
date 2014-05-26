package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.views.DialogCliente;
import ejconsulti.locacao.views.PanelEndereco;
import eso.database.ContentValues;
import eso.utils.Log;
import eso.utils.Text;

/**
 * Editar cliente
 * 
 * @author Edison Jr
 *
 */
public class EditarCliente implements ActionListener {
	public static final String TAG = EditarCliente.class.getSimpleName();
	
	private DialogCliente dialog;
	
	private Cliente cliente;
	private Endereco endereco;
	private Endereco enderecoEntrega;

	public EditarCliente(Cliente cliente) {
		this.cliente = cliente;
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogCliente(Main.getFrame(), "Editar cliente");
		dialog.getTxtNome().setText(cliente.getNome());
		dialog.getTxtRg().setText(cliente.getRg());
		dialog.getTxtCpf().setValue(cliente.getCpf());
		dialog.getTxtTelefone().setValue(cliente.getTelefone());
		dialog.getTxtEmail().setText(cliente.getEmail());
		
		endereco = ControladorEndereco.getEndereco(cliente.getIdEndereco());
		PanelEndereco pEnd = dialog.getPanelEndereco();
		pEnd.getBoxUf().setSelectedItem(endereco.getUf());
		pEnd.getTxtCidade().setText(endereco.getCidade().getNome());
		pEnd.getTxtBairro().setText(endereco.getBairro().getNome());
		pEnd.getTxtRua().setText(endereco.getRua().getNome());
		pEnd.getTxtNumero().setText(endereco.getNumero());
		pEnd.getTxtComplemento().setText(endereco.getComplemento());
		pEnd.getTxtReferencia().setText(endereco.getReferencia());

		enderecoEntrega = ControladorEndereco.getEndereco(cliente.getIdEnderecoEntrega());
		PanelEndereco pEndEntrega = dialog.getPanelEndereco();
		pEndEntrega.getBoxUf().setSelectedItem(enderecoEntrega.getUf());
		pEndEntrega.getTxtCidade().setText(enderecoEntrega.getCidade().getNome());
		pEndEntrega.getTxtBairro().setText(enderecoEntrega.getBairro().getNome());
		pEndEntrega.getTxtRua().setText(enderecoEntrega.getRua().getNome());
		pEndEntrega.getTxtNumero().setText(enderecoEntrega.getNumero());
		pEndEntrega.getTxtComplemento().setText(enderecoEntrega.getComplemento());
		pEndEntrega.getTxtReferencia().setText(enderecoEntrega.getReferencia());
		
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
		
		// Editar endereço
		
		int result = ControladorEndereco.editar(dialog, dialog.getPanelEndereco(), endereco);
		if(result < 1)
			return;
		
		int idEnderecoEntrega = enderecoEntrega.getId();
		
		// Se o endereco de entrega NÂO É o mesmo endereço
		if(dialog.getCbEntregarOutroEnd().isSelected()) {
			
			// Se o endereco de entrega ERA é o mesmo endereço
			if(cliente.getIdEndereco() == cliente.getIdEnderecoEntrega()) {
				
				// Cadastrar endereco de entrega
				
				idEnderecoEntrega = ControladorEndereco.cadastrar(dialog, dialog.getPanelEnderecoEntrega());
				if(idEnderecoEntrega < 1)
					return; // Endereço não cadastrado
				
			} else {
				
				// Editar endereco de entrega
				
				idEnderecoEntrega = ControladorEndereco.editar(dialog, dialog.getPanelEnderecoEntrega(), enderecoEntrega);
				if(idEnderecoEntrega < 1)
					return;
			}
		} else {
			
			// Se o endereco de entrega NÂO ERA é o mesmo endereço
			if(cliente.getIdEndereco() != cliente.getIdEnderecoEntrega()) {
				try {
					DAO.getDatabase().delete(Endereco.TABLE, Endereco.ID+" = ?", cliente.getIdEnderecoEntrega());
				} catch (SQLException e) {
					Log.e(TAG, "Erro ao excluir endereço de entrega");
					return;
				}
				idEnderecoEntrega = cliente.getIdEndereco();
			}
		}
		
		// Editar cliente
		ContentValues values = new ContentValues();
		values.put(Cliente.NOME, nome);
		values.put(Cliente.RG, dialog.getTxtRg().getText().trim());
		values.put(Cliente.CPF, Text.toString(dialog.getTxtCpf().getValue())); // Apenas números
		values.put(Cliente.TELEFONE, telefone);
		values.put(Cliente.EMAIL, dialog.getTxtEmail().getText().trim());
		values.put(Cliente.ID_ENDERECO_ENTREGA, idEnderecoEntrega);
		try {
			DAO.getDatabase().update(Cliente.TABLE, values, Cliente.ID+" = ?", cliente.getId());
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao editar cliente");
			return;
		}
		
		dialog.dispose();
		
		// Atualizar da tabela
		Main.getFrame().getBtnClientes().doClick();
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
