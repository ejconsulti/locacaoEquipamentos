package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.Produto;
import ejconsulti.locacao.models.ProdutoOSTableModel;
import eso.components.DateField;
import eso.components.DoubleField;
import eso.document.AutoCompleteDecorator;
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
	private DoubleField txtTotal;
	private DateField txtDataEntrega;
	private DateField txtDataDevolucao;
	private ProdutoOSTableModel produtoOrdemTableModel;
	private JTable tabela;

	private JButton btnAdicionar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnExcluir;

	public DialogOrdemDeServico(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right][][fill]", "[][][][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "flowx,cell 0 0");

		cboxNome = new JComboBox<Cliente>();
		cboxNome.setMinimumSize(new Dimension(400, 10));
		cboxNome.addItem(null);
		AutoCompleteDecorator.enable(cboxNome);
		contentPanel.add(cboxNome, "cell 1 0 2 1");

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setEnabled(false);
		contentPanel.add(lblTelefone, "flowx,cell 0 1");

		txtTelefone = new JFormattedTextField(Text.buildMask("(##)####-####"));
		txtTelefone.setColumns(9);
		txtTelefone.setEditable(false);
		contentPanel.add(txtTelefone, "cell 1 1");

		panelEnderecoEntrega = new PanelEndereco();
		panelEnderecoEntrega.setBorder(new TitledBorder(null, "Endere\u00E7o de entrega", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEnderecoEntrega.setEditable(false);
		contentPanel.add(panelEnderecoEntrega, "cell 1 2,alignx left");

		cboxProdutos = new JComboBox<Produto>();
		contentPanel.add(cboxProdutos, "cell 0 3 2 1,growx");

		btnAdicionar = new JButton("Adicionar");
		contentPanel.add(btnAdicionar, "cell 2 3");	

		produtoOrdemTableModel = new ProdutoOSTableModel();

		tabela = new JTable(produtoOrdemTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(600, 100));
		contentPanel.add(new JScrollPane(tabela), "cell 0 4 2 1");

		btnExcluir = new JButton("Excluir");
		contentPanel.add(btnExcluir, "cell 2 4");

		JLabel lblData = new JLabel("Data entrega");
		contentPanel.add(lblData, "flowx,cell 0 5 2 1,alignx center");

		txtDataEntrega = new DateField();
		txtDataEntrega.setColumns(7);
		contentPanel.add(txtDataEntrega, "cell 0 5 2 1");

		JLabel lblDataDevoluo = new JLabel("Data devolu\u00E7\u00E3o");
		contentPanel.add(lblDataDevoluo, "cell 0 5 2 1");

		txtDataDevolucao = new DateField();
		txtDataDevolucao.setColumns(7);
		contentPanel.add(txtDataDevolucao, "cell 0 5 2 1");

		JLabel lblTotal = new JLabel("Total");
		contentPanel.add(lblTotal, "cell 0 5 2 1,gapx 60");

		txtTotal = new DoubleField();
		txtTotal.setValue(0.0);
		txtTotal.setColumns(10);
		contentPanel.add(txtTotal, "cell 0 5 2 1");

		JLabel l2 = new JLabel("*");
		l2.setEnabled(false);
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 0 1");

		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 0 0");

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

	public ProdutoOSTableModel getProdutoOrdemTableModel() {
		return produtoOrdemTableModel;
	}

	public DoubleField getTxtTotal() {
		return txtTotal;
	}

	public DateField getTxtDataEntrega() {
		return txtDataEntrega;
	}

	public DateField getTxtDataDevolucao() {
		return txtDataDevolucao;
	}

	public JTable getTabela() {
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
