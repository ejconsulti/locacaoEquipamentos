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
import eso.utils.Text;

public class DialogEmitente extends JDialog {
private static final long serialVersionUID = 1L;

	private JFormattedTextField jtfCpfTitular;
	private JFormattedTextField jtfCnpjTitular;
	private JFormattedTextField jtfTelefoneTitular;
	private JTextField jtfNomeTitular;

	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private ButtonGroup grupoCheckBox;
	private JRadioButton cpf;
	private JRadioButton cnpj;

	public DialogEmitente(Window owner, String title) {
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
		
		// Nome
		
		JLabel lblNomeTitular = new JLabel("Nome do Titular: ");
		contentPanel.add(lblNomeTitular, "cell 0 0");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 0,alignx trailing");
		
		jtfNomeTitular = new JTextField();
		jtfNomeTitular.setColumns(20);
		contentPanel.add(jtfNomeTitular, "cell 2 0");
		
		// Telefone
		
		JLabel lblTelefoneTitular = new JLabel("Telefone do Titular: ");
		contentPanel.add(lblTelefoneTitular, "cell 0 1");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 1,alignx trailing");
		
		jtfTelefoneTitular = new JFormattedTextField(Text.buildMask("(##)####-####"));
		jtfTelefoneTitular.setColumns(20);
		contentPanel.add(jtfTelefoneTitular, "cell 2 1");
		
		// CPF
		
		cpf = new JRadioButton("CPF:  ");
		contentPanel.add(cpf, "cell 0 2");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 2,alignx trailing");
		
		jtfCpfTitular = new JFormattedTextField(Text.buildMask("###.###.###-##"));
		jtfCpfTitular.setColumns(20);
		contentPanel.add(jtfCpfTitular, "cell 2 2");
		
		// CNPJ
		
		cnpj = new JRadioButton("CNPJ: ");
		contentPanel.add(cnpj, "cell 0 3");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 3,alignx trailing");
		
		jtfCnpjTitular = new JFormattedTextField(Text.buildMask("##.###.###/####-##"));
		jtfCnpjTitular.setColumns(20);
		contentPanel.add(jtfCnpjTitular, "cell 2 3");
		
		
		
		
		grupoCheckBox = new ButtonGroup();
		grupoCheckBox.add(cpf);
		grupoCheckBox.add(cnpj);
				
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

	public ButtonGroup getGrupoCheckBox() {
		return grupoCheckBox;
	}

	public void setGrupoCheckBox(ButtonGroup grupoCheckBox) {
		this.grupoCheckBox = grupoCheckBox;
	}

	public JFormattedTextField getJtfCpfTitular() {
		return jtfCpfTitular;
	}

	public void setJtfCpfTitular(JFormattedTextField jtfCpfTitular) {
		this.jtfCpfTitular = jtfCpfTitular;
	}

	public JFormattedTextField getJtfCnpjTitular() {
		return jtfCnpjTitular;
	}

	public void setJtfCnpjTitular(JFormattedTextField jtfCnpjTitular) {
		this.jtfCnpjTitular = jtfCnpjTitular;
	}

	public JFormattedTextField getJtfTelefoneTitular() {
		return jtfTelefoneTitular;
	}

	public void setJtfTelefoneTitular(JFormattedTextField jtfTelefoneTitular) {
		this.jtfTelefoneTitular = jtfTelefoneTitular;
	}

	public JTextField getJtfNomeTitular() {
		return jtfNomeTitular;
	}

	public void setJtfNomeTitular(JTextField jtfNomeTitular) {
		this.jtfNomeTitular = jtfNomeTitular;
	}

	public JRadioButton getCpf() {
		return cpf;
	}

	public void setCpf(JRadioButton cpf) {
		this.cpf = cpf;
	}

	public JRadioButton getCnpj() {
		return cnpj;
	}

	public void setCnpj(JRadioButton cnpj) {
		this.cnpj = cnpj;
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
