package ejconsulti.locacao.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.peer.SystemTrayPeer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

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
public class CadastrarOrdemDeServico implements ActionListener, TableModelListener {
	public static final String TAG = CadastrarOrdemDeServico.class.getSimpleName();
	
	private DialogOrdemDeServico dialog;
	
	private Cliente cliente;
	private Integer idEndereco;
	private String data = "__/__/____";
	private double total = 0;

	public CadastrarOrdemDeServico() {
		initialize();
	}
	
	private void initialize() {
		dialog = new DialogOrdemDeServico(Main.getFrame(), "Ordem de Serviço");
		
		addEvents();
		
		addClientes();
		addProdutos();
		
			
		dialog.setVisible(true);
	}
	
	public static String converterData (Calendar c){
		String dt = new DecimalFormat("00").format(c.get(Calendar.DAY_OF_MONTH)) + "/";
		dt += new DecimalFormat("00").format(c.get(Calendar.MONTH)+1) + "/";
		dt += new DecimalFormat("00").format(c.get(Calendar.YEAR));
		
		return dt;
	}
	
	private void addEvents() {
		dialog.getCboxNome().addActionListener(this);
		dialog.getBtnAdicionar().addActionListener(this);
		dialog.getBtnExcluir().addActionListener(this);
		dialog.getBtnSalvar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
		dialog.getTxtTotal().addActionListener(this);
		dialog.getProdutoOrdemTableModel().addTableModelListener(this);
		
		dialog.getTxtData().getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e){}
			public void insertUpdate(DocumentEvent e) {
				String texto = dialog.getTxtData().getText().replaceAll("/", "").replaceAll("_", "");
				if (texto.length() == 8 && !validarData(texto)){
					Calendar dataAtual = Calendar.getInstance();
					String dia = new DecimalFormat("00").format(dataAtual.get(Calendar.DATE)).toString();
					String mes = new DecimalFormat("00").format(dataAtual.get(Calendar.MONTH) + 1).toString();
					String ano =  "" + dataAtual.get(Calendar.YEAR);
					data = dia + "/" + mes + "/" + ano;
					Runnable doHighlight = new Runnable() {
				        @Override
				        public void run() {
				        	dialog.getTxtData().setText(data);
				        }
				    };       
				    SwingUtilities.invokeLater(doHighlight);
					
				}
				else
					data = dialog.getTxtData().getText();
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
	
	private void addClientes (){
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Cliente.TABLE, null, null, null, Cliente.NOME);
			
			while(rs.next()) {
				Cliente c = Cliente.rsToObject(rs);
				DefaultComboBoxModel<Cliente> model = (DefaultComboBoxModel<Cliente>) dialog.getCboxNome().getModel();
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
			rs = DAO.getDatabase().select(null, Produto.TABLE, null, null, null, Produto.ID);

			while(rs.next()){
				Produto p = Produto.rsToObject(rs);
				
				if (p.getQuantidade() > 0){
					DefaultComboBoxModel<Produto> model = (DefaultComboBoxModel<Produto>) dialog.getCboxProdutos().getModel();
					model.addElement(p);
				}
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
	
	private void limparCampos (){
		dialog.getTxtTelefone().setText("");
		dialog.getPanelEnderecoEntrega().getTxtRua().setText("");
		dialog.getPanelEnderecoEntrega().getTxtNumero().setText("");
		dialog.getPanelEnderecoEntrega().getTxtComplemento().setText("");
		dialog.getPanelEnderecoEntrega().getTxtBairro().setText("");
		dialog.getPanelEnderecoEntrega().getTxtCidade().setText("");
		dialog.getPanelEnderecoEntrega().getBoxUf().setSelectedItem("");
		dialog.getPanelEnderecoEntrega().getTxtReferencia().setText("");
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
		
//		 Verificar campos obrigatórios
		
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
		
		//Cadastra a ordem de serviço
		ContentValues values = new ContentValues();
		values.put(OrdemDeServico.ID_CLIENTE, cliente.getId());
		values.put(OrdemDeServico.ID_ENDERECO_ENTREGA, idEndereco);
		values.put(OrdemDeServico.DATA, data);
		values.put(OrdemDeServico.TOTAL, total);
		values.put(OrdemDeServico.STATUS, "Em Andamento");
		
		
		int idOrdemServicoAtual = 0;
		
		try {
			DAO.getDatabase().insert(OrdemDeServico.TABLE, values);
			
			ResultSet r = DAO.getDatabase().getConnection().createStatement().executeQuery("SELECT MAX(IDORDEMSERVICO) AS \"ULTIMO\" FROM ORDEMSERVICO");
			if (r.next())
				idOrdemServicoAtual = r.getInt("ULTIMO");
			
			r.close();
			
		} catch (SQLException e){
			Log.e(TAG, "Erro ao cadastrar Ordem de Serviço.");
			return;
		}
		
		Calendar cal;
		
		for (int i = 0; i < dialog.getProdutoOrdemTableModel().getRowCount(); i++){
			ContentValues valuesLocacao = new ContentValues();
			valuesLocacao.put("IDPRODUTO", dialog.getProdutoOrdemTableModel().get(i).getId());
			valuesLocacao.put(OrdemDeServico.ID, idOrdemServicoAtual);
			
			if (data.equals("__/__/____"))
				valuesLocacao.put("DATAENTREGA", "__/__/____");
			else{
				cal = Calendar.getInstance();
				cal.set(Integer.parseInt(data.substring(6)), Integer.parseInt(data.substring(3, 5)), Integer.parseInt(data.substring(0, 2)));
				cal.add(Calendar.DATE, Integer.parseInt(dialog.getProdutoOrdemTableModel().getValueAt(i, 4).toString()));
				valuesLocacao.put("DATAENTREGA", converterData(cal));
			}
			valuesLocacao.put("DIAS", dialog.getProdutoOrdemTableModel().getValueAt(i, 4));
			valuesLocacao.put("QUANTIDADE", Integer.parseInt(dialog.getProdutoOrdemTableModel().getValueAt(i, 3).toString()));
			valuesLocacao.put("VALOR", Double.parseDouble(dialog.getProdutoOrdemTableModel().getValueAt(i, 5).toString()));
			
			alterarEstoque(dialog.getProdutoOrdemTableModel().get(i), Integer.parseInt(dialog.getProdutoOrdemTableModel().getValueAt(i, 3).toString()));
			try {
				DAO.getDatabase().insert("PRODUTOSLOCADOS", valuesLocacao);
			} catch (SQLException e){
				e.printStackTrace();
				//Log.e(TAG, "Erro ao cadastrar Ordem de Serviço.");
				return;
			}
		}
		
		
		JOptionPane.showMessageDialog(dialog, "Ordem de Serviço cadastrada!");
		Main.getFrame().getBtnOrdemdeServico().doClick();
		
		dialog.dispose();
	}
	
	public static void alterarEstoque (Produto p, int quantidade){
		try {
			Object []id = new Integer[]{p.getId()};
			ContentValues c = new ContentValues();
			c.put(Produto.QUANTIDADE, (p.getQuantidade()-quantidade) );
			
			DAO.getDatabase().update(Produto.TABLE, c, Produto.ID +  " =? ", id);
			

		} catch (SQLException e){
			Log.e(TAG, "Erro ao alterar o estoque do produto.", e);
			return;
		}
	}
	
	public void adicionar (){
		Produto p = (Produto) dialog.getCboxProdutos().getSelectedItem();
		dialog.getProdutoOrdemTableModel().addRow(p, 1, 1);
		dialog.getProdutoOrdemTableModel().fireTableDataChanged();
	}
	
	public void excluir (){
		if (dialog.getTabela().getSelectedRow() >= 0)
			dialog.getProdutoOrdemTableModel().removeRow(dialog.getTabela().getSelectedRow());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == dialog.getCboxNome()){
			
			if (dialog.getCboxNome().getSelectedIndex() < 0)
				limparCampos();
			else
				preencherCampos((Cliente) dialog.getCboxNome().getSelectedItem());			
		}
		
//		if (e.getSource() == dialog.getTxtData()){
//			dialog.getTxtData().setCaretPosition(0);
//			//System.out.println(dialog.getTxtData().getText());
//		}
		
		switch(e.getActionCommand()) {
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
		double valor = 0 ;
		for (int i = 0 ; i < dialog.getProdutoOrdemTableModel().getRowCount(); i++){			
			valor += Double.parseDouble(dialog.getProdutoOrdemTableModel().getValueAt(i, 5).toString());
		}
		total = valor;
		
		//impede que o campo "total" tenha dízimas periódicas
		//permite apenas dois dígitos após a vírgula
		String real = Double.toString(total);
		real = real.substring(0, real.indexOf("."));
		
		String centavos = Double.toString(total);
		centavos = centavos.substring(centavos.indexOf(".")+1);
		
		if (centavos.length() > 2)
			centavos = centavos.substring(0, 2);
		else
		if (centavos.length() < 2)
			centavos += "0";
		
		total = Double.parseDouble(real + "." + centavos);
		dialog.getTxtTotal().setText("R$" + real + "." + centavos);
	}

}

