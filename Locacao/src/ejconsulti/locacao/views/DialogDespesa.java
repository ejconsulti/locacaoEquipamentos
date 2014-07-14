package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.StatusDespesa;
import ejconsulti.locacao.models.TipoDespesa;
import eso.components.DateField;
import eso.components.DoubleField;

/**
 * Dialog de despesa
 * 
 * @author Sadao
 *
 */
public class DialogDespesa extends JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome;
	private JTextField txtDescricao;
	private DateField dataPagamento;
	private DoubleField txtValor;
	private JComboBox<StatusDespesa> jcbStatus;
	private JComboBox<TipoDespesa> jcbTipo;

	private JButton btnSalvar;
	private JButton btnCancelar;
	
	public DialogDespesa(Window owner, String title) {
		super(owner, title);
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][][]", "[][][][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNome = new JLabel("Nome");
		contentPanel.add(lblNome, "cell 0 0");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0,alignx trailing");

		txtNome = new JTextField(30);
		contentPanel.add(txtNome, "flowx,cell 2 0");

		JLabel lblDescricao = new JLabel("Descrição");
		contentPanel.add(lblDescricao, "cell 0 1");

		txtDescricao = new JTextField(30);
		contentPanel.add(txtDescricao, "cell 2 1");

		JLabel lblDataPagamento = new JLabel("Data Pagamento");
		contentPanel.add(lblDataPagamento, "cell 0 2");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 2,alignx trailing");

		dataPagamento = new DateField();
		dataPagamento.setColumns(7);
		contentPanel.add(dataPagamento, "cell 2 2");

		JLabel lblValor = new JLabel("Valor");
		contentPanel.add(lblValor, "cell 0 3,alignx trailing");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 3,alignx trailing");

		txtValor = new DoubleField(0.0);
		contentPanel.add(txtValor, "cell 2 3");
		txtValor.setColumns(10);

		JLabel lblStatus = new JLabel("Status");
		contentPanel.add(lblStatus, "cell 0 4,alignx trailing");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 4,alignx trailing");

		jcbStatus = new JComboBox<StatusDespesa>(StatusDespesa.values());
		contentPanel.add(jcbStatus, "cell 2 4");
		
		JLabel lblTipo = new JLabel("Tipo");
		contentPanel.add(lblTipo, "cell 0 5, alignx trailing");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 5,alignx trailing");
		
		jcbTipo = new JComboBox<TipoDespesa>(TipoDespesa.values());
		contentPanel.add(jcbTipo, "cell 2 5");
		
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

	public JTextField getTxtDescricao() {
		return txtDescricao;
	}

	public DateField getDataPagamento() {
		return dataPagamento;
	}

	public DoubleField getTxtValor() {
		return txtValor;
	}

	public JComboBox<StatusDespesa> getJcbStatus() {
		return jcbStatus;
	}
	
	public JComboBox<TipoDespesa> getJcbTipo() {
		return jcbTipo;
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
