package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;




import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOrdemTableModel;
import eso.utils.Text;

/**
 * Dialog da Ordem de Serviço
 * 
 * @author Érico Jr
 *
 */

public class DialogOrdemDeServico extends JDialog{
	private static final long serialVersionUID = 1L;

	private JComboBox<Cliente> cboxNome;
	private JFormattedTextField txtTelefone;
	private PanelEndereco panelEnderecoEntrega;
	private JComboBox<Produto> cboxProdutos;
	private JButton btnAdicionar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnExcluir;
	private JTextField txtTotal;
	private JFormattedTextField txtData;
	ProdutoOrdemTableModel produtoOrdemTableModel;
	private JTable tabela;
	
	
	public DialogOrdemDeServico(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[][][]", "[][][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "cell 0 0, span 2, center");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 0 0");

		cboxNome = new JComboBox<Cliente>();
		cboxNome.setMinimumSize(new Dimension(400, 10));
		Cliente novo = new Cliente();
		novo.setNome("");
		cboxNome.addItem(novo);
		eso.document.AutoCompleteDecorator.enable(cboxNome);
		contentPanel.add(cboxNome, "cell 0 0");
		
		JLabel lblTelefone = new JLabel("Telefone");
		contentPanel.add(lblTelefone, "cell 0 1, span2, gapx 103");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 0 1");

		txtTelefone = new JFormattedTextField(Text.buildMask("(##)####-####"));
		txtTelefone.setEnabled(false);
		contentPanel.add(txtTelefone, "cell 0 1");
		txtTelefone.setColumns(10);
		
		panelEnderecoEntrega = new PanelEndereco();
		panelEnderecoEntrega.setBorder(new TitledBorder(null, "Endere\u00E7o de entrega", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEnderecoEntrega.setEnabled(false);
		contentPanel.add(panelEnderecoEntrega, "cell 0 2, span 2, center");
		
		cboxProdutos = new JComboBox<Produto>();
		contentPanel.add(cboxProdutos, "cell 0 3, span 1, growx");
		
		btnAdicionar = new JButton("Adicionar");
		contentPanel.add(btnAdicionar, "cell 1 3, center");	
		
		produtoOrdemTableModel = new ProdutoOrdemTableModel();
		
		tabela = new JTable(produtoOrdemTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(600, 70));
		contentPanel.add(new JScrollPane(tabela), "cell 0 4");
		
		btnExcluir = new JButton("Excluir");
		contentPanel.add(btnExcluir, "cell 1 4,center");
		
		JLabel lblData = new JLabel("Data");
		contentPanel.add(lblData, "cell 0 5, span 2, center");
		
		txtData = new JFormattedTextField(Text.buildMask("##/##/####"));
		txtData.setColumns(7);
		contentPanel.add(txtData, "cell 0 5");
		
		JLabel lblTotal = new JLabel("Total");
		contentPanel.add(lblTotal, "cell 0 5, gapx 60");
				
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setMinimumSize(new Dimension(100, 10));
		txtTotal.setText("R$0,00");
		contentPanel.add(txtTotal, "cell 0 5");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		buttonPanel.add(btnCancelar);
		
		pack();
	}

	public JComboBox<Cliente> getCboxNome() {
		return cboxNome;
	}

	public JFormattedTextField getTxtTelefone() {
		return txtTelefone;
	}
	
	public PanelEndereco getPanelEnderecoEntrega() {
		return panelEnderecoEntrega;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JButton getBtnAdicionar() {
		return btnAdicionar;
	}
	
	public JButton getBtnExcluir() {
		return btnExcluir;
	}
	
	public JComboBox<Produto> getCboxProdutos (){
		return cboxProdutos;
	}
	
	public ProdutoOrdemTableModel getProdutoOrdemTableModel() {
		return produtoOrdemTableModel;
	}
	
	public JTextField getTxtTotal (){
		return txtTotal;
	}
	
	public JFormattedTextField getTxtData (){
		return txtData;
	}
	
	public JTable getTabela (){
		return tabela;
	}

	@Override
	public void setVisible(boolean b) {
		if(b) {
			pack();
			setLocationRelativeTo(null);
		}
		super.setVisible(b);
	}


}
