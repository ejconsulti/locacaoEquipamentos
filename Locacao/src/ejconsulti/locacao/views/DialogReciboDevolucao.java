package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Cliente;
import ejconsulti.locacao.models.ProdutoOS;
import ejconsulti.locacao.models.ProdutoOSTableModel;
import eso.components.DateField;
import eso.components.DoubleField;
import eso.document.AutoCompleteDecorator;
import eso.utils.Text;

/**
 * Dialog do Recibo de Devolução
 * 
 * @author Érico Jr
 *
 */

public class DialogReciboDevolucao extends JDialog{
	private static final long serialVersionUID = 1L;

	private JComboBox<Cliente> cboxNome;
	private JFormattedTextField txtTelefone;
	private JComboBox<ProdutoOS> cboxProdutos;
	private DoubleField txtTotal;
	private DateField txtDataEntrega;
	private DateField txtDataDevolucao;
	private ProdutoOSTableModel produtoOrdemTableModel;
	private JTable tabela;

	private JButton btnAdicionar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnExcluir;
	private JPanel panel;

	public DialogReciboDevolucao(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right][grow][][fill]", "[][][][][grow]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "cell 0 0");

		cboxNome = new JComboBox<Cliente>();
		cboxNome.setMinimumSize(new Dimension(400, 10));
		cboxNome.addItem(null);
		AutoCompleteDecorator.enable(cboxNome);
		contentPanel.add(cboxNome, "cell 1 0 3 1");

		JLabel lblTelefone = new JLabel("Telefone");
		contentPanel.add(lblTelefone, "flowx,cell 0 1");

		txtTelefone = new JFormattedTextField(Text.buildMask("(##)####-####"));
		txtTelefone.setColumns(9);
		contentPanel.add(txtTelefone, "cell 1 1");

		cboxProdutos = new JComboBox<ProdutoOS>();
		cboxProdutos.setMinimumSize(new Dimension(430, 10));
		contentPanel.add(cboxProdutos, "cell 0 2 2 1,growx");

		produtoOrdemTableModel = new ProdutoOSTableModel();

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(DialogReciboDevolucao.class.getResource("/icones/adicionar.png")));
		contentPanel.add(btnAdicionar, "cell 2 2,alignx right");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(DialogReciboDevolucao.class.getResource("/icones/excluir.png")));
		contentPanel.add(btnExcluir, "cell 3 2,alignx right");

		tabela = new JTable(produtoOrdemTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(600, 100));
		contentPanel.add(new JScrollPane(tabela), "cell 0 3 4 1,growx");

		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		l2.setEnabled(false);
		contentPanel.add(l2, "cell 0 1");

		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		l1.setEnabled(false);
		contentPanel.add(l1, "cell 0 0");

		panel = new JPanel();
		contentPanel.add(panel, "cell 0 4 2 1,grow");


		JLabel lblData = new JLabel("Data entrega");
		panel.add(lblData);

		txtDataEntrega = new DateField();
		panel.add(txtDataEntrega);
		txtDataEntrega.setColumns(7);

		JLabel lblDataDevoluo = new JLabel("Data devolu\u00E7\u00E3o");
		panel.add(lblDataDevoluo);

		txtDataDevolucao = new DateField();
		panel.add(txtDataDevolucao);
		txtDataDevolucao.setColumns(7);

		JLabel lblTotal = new JLabel("Total");
		contentPanel.add(lblTotal, "flowx,cell 2 4,alignx right,gapx 60");

		txtTotal = new DoubleField();
		txtTotal.setValue(0.0);
		txtTotal.setColumns(10);
		contentPanel.add(txtTotal, "cell 3 4");

		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogReciboDevolucao.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogReciboDevolucao.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);

		pack();
	}

	public JComboBox<Cliente> getCboxNome() {
		return cboxNome;
	}

	public JFormattedTextField getTxtTelefone() {
		return txtTelefone;
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

	public JComboBox<ProdutoOS> getCboxProdutos (){
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
