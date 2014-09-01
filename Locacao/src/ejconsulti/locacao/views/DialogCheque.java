package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import eso.components.DateField;
import eso.components.DoubleField;
import eso.utils.Text;

public class DialogCheque extends JDialog {
private static final long serialVersionUID = 1L;

	private JFormattedTextField jtfCpfCheque;
	private JTextField jtfNomeCheque;
	private DateField dfDataCheque;
	private JTextField jtfNumeroCheque;
	private ButtonGroup grupoCheckBox;
	private JRadioButton sim;
	private JRadioButton nao;
	private DoubleField dfValorCheque;

	private JButton btnSalvar;
	private JButton btnCancelar;

	public DialogCheque(Window owner, String title) {
		super(owner, title);
		intialize();
	}
	
	private void intialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		
		
		JLabel lblCpfCheque = new JLabel("CPF: ");
		contentPanel.add(lblCpfCheque, "cell 0 0,alignx trailing");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0,alignx trailing");
		
		jtfCpfCheque = new JFormattedTextField(Text.buildMask("###.###.###-##"));
		jtfCpfCheque.setColumns(10);
		contentPanel.add(jtfCpfCheque, "cell 2 0");
		
		
		
		JLabel lblNomeCheque = new JLabel("Nome do Titular: ");
		contentPanel.add(lblNomeCheque, "cell 0 1");

		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 1,alignx trailing");
		
		jtfNomeCheque = new JTextField();
		jtfNomeCheque.setColumns(10);
		contentPanel.add(jtfNomeCheque, "cell 2 1");
		
		
		
		JLabel lblDataCheque = new JLabel("Data do Cheque: ");
		contentPanel.add(lblDataCheque, "cell 20 2");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 2,alignx trailing");
		
		dfDataCheque = new DateField();
		dfDataCheque.setColumns(10);
		contentPanel.add(dfDataCheque, "cell 2 2");
		
		
		
		JLabel lblNumeroCheque = new JLabel("Numero do Cheque: ");
		contentPanel.add(lblNumeroCheque, "cell 0 3");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 3,alignx trailing");
		
		jtfNumeroCheque = new JTextField();
		jtfNumeroCheque.setColumns(20);
		contentPanel.add(jtfNumeroCheque, "cell 2 3");
		
		
		
		JLabel lblDonoCheque = new JLabel("Dono do Cheque? ");
		contentPanel.add(lblDonoCheque, "cell 0 4");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 4,alignx trailing");
		
		sim = new JRadioButton("Sim");
		contentPanel.add(sim, "cell 2 4");
		
		nao = new JRadioButton("Não");
		contentPanel.add(nao, "cell 2 4");
		
		grupoCheckBox = new ButtonGroup();
		grupoCheckBox.add(sim);
		grupoCheckBox.add(nao);
		
		
		
		JLabel lblValorCheque = new JLabel("Valor do Cheque: ");
		contentPanel.add(lblValorCheque, "cell 0 5");
		
		JLabel l6 = new JLabel("*");
		l6.setForeground(Color.RED);
		contentPanel.add(l6, "cell 1 5,alignx trailing");
		
		dfValorCheque = new DoubleField();
		dfValorCheque.setColumns(20);
		contentPanel.add(dfValorCheque, "cell 2 5");
		
		
				
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);
		
	}
	
	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JFormattedTextField getJtfCpfCheque() {
		return jtfCpfCheque;
	}

	public void setJtfCpfCheque(JFormattedTextField jtfCpfCheque) {
		this.jtfCpfCheque = jtfCpfCheque;
	}

	public JTextField getJtfNomeCheque() {
		return jtfNomeCheque;
	}

	public void setJtfNomeCheque(JTextField jtfNomeCheque) {
		this.jtfNomeCheque = jtfNomeCheque;
	}

	public DateField getDfDataCheque() {
		return dfDataCheque;
	}

	public void setDfDataCheque(DateField dfDataCheque) {
		this.dfDataCheque = dfDataCheque;
	}

	public JTextField getJtfNumeroCheque() {
		return jtfNumeroCheque;
	}

	public void setJtfNumeroCheque(JTextField jtfNumeroCheque) {
		this.jtfNumeroCheque = jtfNumeroCheque;
	}

	public ButtonGroup getGrupoCheckBox() {
		return grupoCheckBox;
	}

	public void setGrupoCheckBox(ButtonGroup grupoCheckBox) {
		this.grupoCheckBox = grupoCheckBox;
	}

	public JRadioButton getSim() {
		return sim;
	}

	public void setSim(JRadioButton sim) {
		this.sim = sim;
	}

	public JRadioButton getNao() {
		return nao;
	}

	public void setNao(JRadioButton nao) {
		this.nao = nao;
	}

	public DoubleField getDfValorCheque() {
		return dfValorCheque;
	}

	public void setDfValorCheque(DoubleField dfValorCheque) {
		this.dfValorCheque = dfValorCheque;
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
