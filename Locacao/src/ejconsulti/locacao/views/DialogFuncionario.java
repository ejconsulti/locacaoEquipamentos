package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import eso.components.DateFiled;
import eso.components.DoubleField;
import eso.utils.Text;

/**
 * Dialog de funcionário
 * 
 * @author Edison Jr
 *
 */
public class DialogFuncionario extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome;
	private JTextField txtRg;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtTelefone;

	private PanelEndereco panelEndereco;
	
	private DoubleField txtSalario;
	private DateFiled dataEntrada;
	private JSpinner spnDiaPagamento;

	private JButton btnSalvar;
	private JButton btnCancelar;

	private DateFiled previsaoFerias;
	
	public DialogFuncionario(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][][][][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "cell 0 0");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0");

		txtNome = new JTextField(30);
		contentPanel.add(txtNome, "flowx,cell 2 0");

		JLabel lblRg = new JLabel("RG");
		contentPanel.add(lblRg, "cell 0 1");

		txtRg = new JTextField(10);
		contentPanel.add(txtRg, "cell 2 1");

		JLabel lblCpf = new JLabel("CPF");
		contentPanel.add(lblCpf, "cell 0 2");

		txtCpf = new JFormattedTextField(Text.buildMask("###.###.###-##"));
		txtCpf.setColumns(10);
		contentPanel.add(txtCpf, "cell 2 2");

		JLabel lblTelefone = new JLabel("Telefone");
		contentPanel.add(lblTelefone, "cell 0 3,alignx trailing");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 3");

		txtTelefone = new JFormattedTextField(Text.buildMask("(##)####-####"));
		contentPanel.add(txtTelefone, "cell 2 3");
		txtTelefone.setColumns(10);
		
		JLabel lblSalario = new JLabel("Sal\u00E1rio");
		contentPanel.add(lblSalario, "cell 0 4");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 4,alignx trailing");
		
		txtSalario = new DoubleField(0.0);
		txtSalario.setColumns(10);
		contentPanel.add(txtSalario, "cell 2 4");
		
		JLabel lblDataEntrada = new JLabel("Data de entrada");
		contentPanel.add(lblDataEntrada, "cell 0 5");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 5,alignx trailing");
		
		dataEntrada = new DateFiled();
		contentPanel.add(dataEntrada, "cell 2 5");
		
		JLabel lblDiaPagamento = new JLabel("Dia de pagamento");
		contentPanel.add(lblDiaPagamento, "cell 0 6");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 6");
		
		spnDiaPagamento = new JSpinner();
		spnDiaPagamento.setModel(new SpinnerNumberModel(1, 1, 28, 1));
		contentPanel.add(spnDiaPagamento, "cell 2 6");
		
		JLabel lblPrevisoDeFrias = new JLabel("Previs\u00E3o de F\u00E9rias");
		contentPanel.add(lblPrevisoDeFrias, "cell 0 7");
		
		previsaoFerias = new DateFiled();
		contentPanel.add(previsaoFerias, "cell 2 7");
		
		panelEndereco = new PanelEndereco();
		panelEndereco.setBorder(new TitledBorder(null, "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelEndereco, "cell 0 8 3 1");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		buttonPanel.add(btnCancelar);
		
		pack();
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public JTextField getTxtRg() {
		return txtRg;
	}

	public JFormattedTextField getTxtCpf() {
		return txtCpf;
	}

	public JFormattedTextField getTxtTelefone() {
		return txtTelefone;
	}
	
	public DoubleField getTxtSalario() {
		return txtSalario;
	}
	
	public DateFiled getDataEntrada() {
		return dataEntrada;
	}
	
	public JSpinner getSpnDiaPagamento() {
		return spnDiaPagamento;
	}
	
	public DateFiled getPrevisaoFerias() {
		return previsaoFerias;
	}

	public PanelEndereco getPanelEndereco() {
		return panelEndereco;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
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
