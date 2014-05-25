package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Endereco;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.views.DialogOrdemDeServico;
import eso.database.ContentValues;
import eso.utils.Log;



/**
 * Cadastrar Ordem de Serviço
 * 
 * @author Érico Jr
 *
 */
public class VisualizarOrdemDeServico implements ActionListener {
	public static final String TAG = VisualizarOrdemDeServico.class.getSimpleName();

	private DialogOrdemDeServico dialog;

	private Cliente cliente;
	OrdemDeServico ordem;

	public VisualizarOrdemDeServico(OrdemDeServico ordem) {
		initialize(ordem);
	}

	@SuppressWarnings("static-access")
	private void initialize(OrdemDeServico ordem) {
		dialog = new DialogOrdemDeServico(Main.getFrame(), "Ordem de Serviço");
		this.ordem = ordem;

		addEvents();
		addProdutosTabela(ordem);

		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Cliente.TABLE, Cliente.ID + " =? ", new Integer[]{ordem.getIdCliente()}, null, null);
			cliente = cliente.rsToObject(rs);
			DefaultComboBoxModel<Cliente> model = (DefaultComboBoxModel<Cliente>) dialog.getCboxNome().getModel();
			model.addElement(cliente);
			dialog.getCboxNome().setSelectedItem(cliente);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
			} catch (SQLException e) {}
		}

		preencherCampos(cliente);

		dialog.getCboxNome().setEnabled(false);
		dialog.getCboxProdutos().setEnabled(false);
		dialog.getTabela().setEnabled(false);
		dialog.getBtnAdicionar().setEnabled(false);
		dialog.getBtnCancelar().setText("Fechar");
		dialog.getBtnExcluir().setEnabled(false);

		if (ordem.getStatus().equals("Cancelada")){
			dialog.getTxtData().setEnabled(false);
			dialog.getBtnSalvar().setEnabled(false);
		}
		dialog.setVisible(true);
	}

	private void addEvents() {
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtData().getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e){}
			public void insertUpdate(DocumentEvent e) {
				String texto = dialog.getTxtData().getText().replaceAll("/", "").replaceAll("_", "");
				if (texto.length() == 8 && !validarData(texto)){
					Calendar dataAtual = Calendar.getInstance();
					String dia = new DecimalFormat("00").format(dataAtual.get(Calendar.DATE)).toString();
					String mes = new DecimalFormat("00").format(dataAtual.get(Calendar.MONTH) + 1).toString();
					String ano =  "" + dataAtual.get(Calendar.YEAR);
					final String data = dia + "/" + mes + "/" + ano;
					Runnable doHighlight = new Runnable() {
						@Override
						public void run() {
							dialog.getTxtData().setText(data);
						}
					};       
					SwingUtilities.invokeLater(doHighlight);

				}
			}
			public void changedUpdate(DocumentEvent e){}
		});
	}

	public boolean validarData (String texto){
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");  
		sdf.setLenient(false);  
		try {  
			sdf.parse(texto);  
			return true;  
		}  
		catch (ParseException ex) {  
			return false;  
			// ex.getMessage();  
		}
	}

	private void addProdutosTabela (OrdemDeServico ordem){
		ResultSet rs = null;
		ResultSet rsProduto = null;
		try {
			//busca os codigos de produto da ordem de serviçi na tabela ProdutosLocados
			rs = DAO.getDatabase().select(null, "PRODUTOSLOCADOS", OrdemDeServico.ID + " =? ", new Integer[]{ordem.getId()}, null, Produto.ID);

			while(rs.next()){
				int codigoProduto = rs.getInt(Produto.ID);
				rsProduto = DAO.getDatabase().select(null, Produto.TABLE, Produto.ID + "=?", new Integer[]{codigoProduto}, null, null);

				Produto p = Produto.rsToObject(rsProduto);

				dialog.getProdutoOrdemTableModel().addRow(p, rs.getInt("QUANTIDADE"), rs.getInt("DIAS"));
				dialog.getProdutoOrdemTableModel().fireTableDataChanged();

				rsProduto.close();
			}	

		} catch (SQLException ex) {
			Log.e(TAG, "Erro ao carregar clientes", ex);
		} finally {
			//if(rs != null) {
			try {
				rs.close();
				rsProduto.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}		
	}


	private void preencherCampos (Cliente c){		
		cliente = c;

		Endereco endereco = ControladorEndereco.getEndereco(c.getIdEnderecoEntrega());

		//	idEndereco = endereco.getId();

		dialog.getTxtTelefone().setText(c.getTelefone());
		dialog.getPanelEnderecoEntrega().getTxtRua().setText(endereco.getRua().getNome());
		dialog.getPanelEnderecoEntrega().getTxtNumero().setText(endereco.getNumero());
		dialog.getPanelEnderecoEntrega().getTxtComplemento().setText(endereco.getComplemento());
		dialog.getPanelEnderecoEntrega().getTxtBairro().setText(endereco.getBairro().getNome());
		dialog.getPanelEnderecoEntrega().getTxtCidade().setText(endereco.getCidade().getNome());
		dialog.getPanelEnderecoEntrega().getBoxUf().setSelectedItem(endereco.getUf().getNome());
		dialog.getPanelEnderecoEntrega().getTxtReferencia().setText(endereco.getComplemento());
		dialog.getTxtData().setText(ordem.getData());
		dialog.getTxtTotal().setText("R$" + ordem.getValor());
	}

	private void salvar() {
		//altera a data da ordem de serviço e dos produtos

		try {
			ContentValues values = new ContentValues();
			values.put(OrdemDeServico.DATA, dialog.getTxtData().getText());
			DAO.getDatabase().update(OrdemDeServico.TABLE, values, OrdemDeServico.ID + "=?", new Object[]{ordem.getId()});
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao alterar ordem de serviço.");
		}
		
		
		
		if (dialog.getTxtData().getText().equals("__/__/____")){
			ContentValues values = new ContentValues();
			values.put("DATAENTREGA", dialog.getTxtData().getText());
			try {
				DAO.getDatabase().update("PRODUTOSLOCADOS", values, OrdemDeServico.ID+"=?", new Object[]{ordem.getId()});
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao alterar dara de entrega do produto." + e);
			}
		}else{ 
			ResultSet rs = null;
			
			try {
				ArrayList<String> datas = new ArrayList<String>();
				ArrayList<Integer> id = new ArrayList<Integer>();
				
				rs = DAO.getDatabase().select(null, "PRODUTOSLOCADOS", OrdemDeServico.ID + "=?", new Object[]{ordem.getId()}, null, "IDPRODUTOLOCADO");
				
				while (rs.next()){
					int dias = rs.getInt("DIAS");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					sdf.setLenient(false);
					
					try {
						sdf.parse(dialog.getTxtData().getText());
					} catch (ParseException e) {
						e.printStackTrace();
					} 
					
					Calendar dt = sdf.getCalendar();
	
					id.add(rs.getInt("IDPRODUTOLOCADO"));
					dt.add(Calendar.DAY_OF_MONTH, dias);
					
					datas.add(CadastrarOrdemDeServico.converterData(dt));
				}
				rs.close();
				
				for (int i = 0; i < id.size(); i++) {
					ContentValues vl = new ContentValues();
					vl.put("DATAENTREGA", datas.get(i));
					DAO.getDatabase().update("PRODUTOSLOCADOS", vl, "IDPRODUTOLOCADO = ?",new Object[]{id.get(i)});
				}
				
			} catch (SQLException e) {
				
			}
		}

		Main.getFrame().getBtnOrdemdeServico().doClick();

		dialog.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		switch(e.getActionCommand()) {
		case "Salvar":
			salvar();
			break;
		case "Fechar":
			dialog.dispose();
			break;
		}
	}
}

