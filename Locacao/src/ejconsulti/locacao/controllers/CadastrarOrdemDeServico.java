package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.views.DialogOrdemDeServico;
import eso.database.ContentValues;
import eso.utils.ComponentUtils;
import eso.utils.Log;

/**
 * Cadastrar Ordem de Servi�o
 * 
 * @author �rico Jr
 * @author Edison Junior
 *
 */
public class CadastrarOrdemDeServico implements ActionListener, TableModelListener {
	public static final String TAG = CadastrarOrdemDeServico.class.getSimpleName();
	
	private DialogOrdemDeServico dialog;
	
	private Cliente cliente;
	private Integer idEndereco;

	public CadastrarOrdemDeServico() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogOrdemDeServico(Main.getFrame(), "Ordem de Servi\u00e7o");
		dialog.getTxtDataEntrega().setDate(new Date(System.currentTimeMillis()));
		
		addEvents();
		
		addClientes();
		addProdutos();
		
		dialog.setVisible(true);
	}
	
	private void addEvents() {
		dialog.getCboxNome().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialog.getCboxNome().getSelectedItem() == null) {
					dialog.getTxtTelefone().setValue(null);
					ComponentUtils.free(dialog.getPanelEnderecoEntrega());
					dialog.getBtnEditarCliente().setEnabled(false);
				} else {
					preencherCampos((Cliente) dialog.getCboxNome().getSelectedItem());
					dialog.getBtnEditarCliente().setEnabled(true);
				}
			}
		});
		dialog.getBtnAdicionarCliente().addActionListener(this);
		dialog.getBtnEditarCliente().addActionListener(this);
		dialog.getBtnAdicionar().addActionListener(this);
		dialog.getBtnExcluir().addActionListener(this);
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtTotal().addActionListener(this);
		dialog.getProdutoOrdemTableModel().addTableModelListener(this);
	}
	
	private void addClientes (){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Cliente.TABLE, null, null, null, Cliente.NOME);

			DefaultComboBoxModel<Cliente> model = (DefaultComboBoxModel<Cliente>) dialog.getCboxNome().getModel();
			while(rs.next()) {
				Cliente c = Cliente.rsToObject(rs);
				model.addElement(c);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar clientes", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private void addProdutos (){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Produto.TABLE, Produto.QUANTIDADE+" > 0", null, null, Produto.ID);

			DefaultComboBoxModel<Produto> model = (DefaultComboBoxModel<Produto>) dialog.getCboxProdutos().getModel();
			while(rs.next()){
				Produto p = Produto.rsToObject(rs);
				model.addElement(p);
			}

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar produtos", ex);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}		
	}
	
	private void preencherCampos (Cliente c){		
		cliente = c;

		Endereco endereco = ControladorEndereco.getEndereco(c.getIdEnderecoEntrega());

		idEndereco = endereco.getId();

		dialog.getTxtTelefone().setText(c.getTelefone());
		dialog.getPanelEnderecoEntrega().getTxtRua().setText(endereco.getRua().getNome());
		dialog.getPanelEnderecoEntrega().getTxtNumero().setText(endereco.getNumero());
		dialog.getPanelEnderecoEntrega().getTxtComplemento().setText(endereco.getComplemento());
		dialog.getPanelEnderecoEntrega().getTxtBairro().setText(endereco.getBairro().getNome());
		dialog.getPanelEnderecoEntrega().getTxtCidade().setText(endereco.getCidade().getNome());
		dialog.getPanelEnderecoEntrega().getBoxUf().setSelectedItem(endereco.getUf().getNome());
		dialog.getPanelEnderecoEntrega().getTxtReferencia().setText(endereco.getComplemento());
	}
	
	private void cadastrar() {
		
//		 Verificar campos obrigat�rios
		
		if (dialog.getCboxNome().getSelectedIndex() <= 0){
			JOptionPane.showMessageDialog(null, "Selecione um Cliente.");
			dialog.getCboxNome().requestFocus();
			return;
		}
		if (dialog.getTabela().getRowCount() <= 0){
			JOptionPane.showMessageDialog(null, "Nenhum produto foi adicionado.");
			dialog.getCboxProdutos().requestFocus();
			return;
		}
		Date data = dialog.getTxtDataEntrega().getDate();
		if(data == null) {
			JOptionPane.showMessageDialog(null, "Preencha corretamente o campo 'Data de entrega'.");
			dialog.getTxtDataEntrega().requestFocus();
			return;
		}
		
		int idOrdemServicoAtual = -1;
		try {
			//Cadastra a ordem de servi�o
			ContentValues values = new ContentValues();
			values.put(OrdemDeServico.ID_CLIENTE, cliente.getId());
			values.put(OrdemDeServico.ID_ENDERECO_ENTREGA, idEndereco);
			values.put(OrdemDeServico.DATA_ENTREGA, data);
			values.put(OrdemDeServico.TOTAL, dialog.getTxtTotal().doubleValue());
			values.put(OrdemDeServico.STATUS, Status.EmAndamento.getId());
			values.put(OrdemDeServico.RECEBIMENTO, 0);
			
			idOrdemServicoAtual = DAO.getDatabase().insert(OrdemDeServico.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao cadastrar Ordem de Servi\u00e7o.", e);
			return;
		}
		
		for(ProdutoOS p : dialog.getProdutoOrdemTableModel().getRows()) {
			ContentValues valuesLocacao = new ContentValues();
			valuesLocacao.put(ProdutoOS.ID, p.getId());
			valuesLocacao.put(ProdutoOS.ID_ORDEMSERVICO, idOrdemServicoAtual);
			valuesLocacao.put(ProdutoOS.DIAS, p.getDias());
			valuesLocacao.put(ProdutoOS.QUANTIDADE_LOCADA, p.getQuantidade());
			valuesLocacao.put(ProdutoOS.VALOR, p.getTotal());
			valuesLocacao.put(ProdutoOS.LOCADO, p.getLocado());
			
			try {
				DAO.getDatabase().insert(ProdutoOS.TABLE, valuesLocacao);
			} catch (SQLException e){
				Log.e(TAG, "Erro ao locar produtos.", e);
				return;
			}
		}
		
		JOptionPane.showMessageDialog(dialog, "Ordem de Servi\u00e7o cadastrada!");
		Main.getFrame().getBtnOrdemdeServico().doClick();
		
		dialog.dispose();
	}
	
	public void adicionar() {
		Produto p = (Produto) dialog.getCboxProdutos().getSelectedItem();
		if(p != null) {
			int dias = 1;
			
			Date dataDevolucao = dialog.getTxtDataDevolucao().getDate();
			// Se a data de devolu��o n�o for vazia, 
			// os dias ser�o a diferen�a em dias entre a data de entrega e a data de devolu��o
			if(dataDevolucao != null) {
				
				Calendar calDev = Calendar.getInstance();
				calDev.setTime(dataDevolucao);

				Date dataEntrega = dialog.getTxtDataEntrega().getDate();
				Calendar calEnt = Calendar.getInstance();
				// Se o campo n�o estiver preenchido, 
				// a data de entrega vai ser a atual
				if(dataEntrega != null)
					calEnt.setTime(dataEntrega);
				
				// Se a data de devolu��o for maior que a data de entrega
				if(calDev.compareTo(calEnt) > 0)
					dias = calDev.get(Calendar.DAY_OF_YEAR) - calEnt.get(Calendar.DAY_OF_YEAR);
			}
			
			dialog.getProdutoOrdemTableModel().add(new ProdutoOS(p, 1, dias, 1));
		}
	}
	
	public void excluir() {
		int row = dialog.getTabela().getSelectedRow();
		if (row >= 0)
			dialog.getProdutoOrdemTableModel().remove(row);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		switch(e.getActionCommand()) {
		case "Adicionar cliente":
			new CadastrarCliente(dialog);
			break;
		case "Editar cliente":
			if (cliente != null) {
				EditarCliente edtCliente = new EditarCliente(dialog, cliente);
				preencherCampos(edtCliente.getCliente());
			}
			break;
		case "Adicionar":
			adicionar();
			break;
		case "Salvar":
			cadastrar();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		case "Excluir":
			excluir();
			break;
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		double total = 0;
		for(ProdutoOS p : dialog.getProdutoOrdemTableModel().getRows())
			total += p.getTotal();
		dialog.getTxtTotal().setValue(total);
	}

}

