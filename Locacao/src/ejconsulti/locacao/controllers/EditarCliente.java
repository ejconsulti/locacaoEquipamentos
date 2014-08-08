package ejconsulti.locacao.controllers;

import java.awt.Window;
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

	public EditarCliente(Window window, Cliente cliente) {
		dialog = new DialogCliente(window, "Editar cliente");
		this.cliente = cliente;
		initialize();
	}
	
	private void initialize() {
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

		if (cliente.getIdEndereco() != cliente.getIdEnderecoEntrega()) {
			dialog.getCbEntregarOutroEnd().setSelected(true);
			enderecoEntrega = ControladorEndereco.getEndereco(cliente.getIdEnderecoEntrega());
			PanelEndereco pEndEntrega = dialog.getPanelEnderecoEntrega();
			pEndEntrega.getBoxUf().setSelectedItem(enderecoEntrega.getUf());
			pEndEntrega.getTxtCidade().setText(enderecoEntrega.getCidade().getNome());
			pEndEntrega.getTxtBairro().setText(enderecoEntrega.getBairro().getNome());
			pEndEntrega.getTxtRua().setText(enderecoEntrega.getRua().getNome());
			pEndEntrega.getTxtNumero().setText(enderecoEntrega.getNumero());
			pEndEntrega.getTxtComplemento().setText(enderecoEntrega.getComplemento());
			pEndEntrega.getTxtReferencia().setText(enderecoEntrega.getReferencia());
		} else {
			dialog.getCbEntregarOutroEnd().setSelected(false);
		}
		
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
		String cpf = Text.toString(dialog.getTxtCpf().getValue());
		if(Text.isEmpty(cpf)) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'CPF'.");
			dialog.getTxtCpf().requestFocus();
			return;
		}
		String telefone = Text.toString(dialog.getTxtTelefone().getValue()); // Apenas dígitos
		if(telefone == null) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Telefone'.");
			dialog.getTxtTelefone().requestFocus();
			return;
		}
		
		// Editar endereço
		
		int idEndereco = ControladorEndereco.editar(dialog, dialog.getPanelEndereco(), endereco);
		if(idEndereco < 1)
			return;
		
		int idEnderecoEntrega = idEndereco;
		
		// Se o endereco de entrega NÂO É o mesmo endereço do cliente
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
				System.out.println("Não era, mas agora é: "+idEnderecoEntrega);
			}
		}

		cliente.setNome(nome);
		cliente.setRg(dialog.getTxtRg().getText().trim());
		cliente.setCpf(cpf);
		cliente.setTelefone(telefone);
		cliente.setEmail(dialog.getTxtEmail().getText().trim());
		cliente.setIdEndereco(idEndereco);
		cliente.setIdEnderecoEntrega(idEnderecoEntrega);
		
		// Editar cliente
		ContentValues values = new ContentValues();
		values.put(Cliente.NOME, cliente.getNome());
		values.put(Cliente.RG, cliente.getRg());
		values.put(Cliente.CPF, cliente.getCpf());
		values.put(Cliente.TELEFONE, cliente.getTelefone());
		values.put(Cliente.EMAIL, cliente.getEmail());
		values.put(Cliente.ID_ENDERECO,  cliente.getIdEndereco());
		values.put(Cliente.ID_ENDERECO_ENTREGA, cliente.getIdEnderecoEntrega());
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
	
	public Cliente getCliente() {
		return cliente;
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
