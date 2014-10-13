package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import eso.components.DateField;
import eso.utils.Text;

public class DialogCartao extends JDialog {
private static final long serialVersionUID = 1L;

	private JTextField jtfNomeTitular;
	private DateField dfDataCartao;
	private JFormattedTextField jtfNumeroCartao;

	private JComboBox<String> cboxBandeira;
	
	private JButton btnSalvarCartao;
	private JButton btnCancelarCartao;

	public DialogCartao(Window owner, String title) {
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
		
		JLabel lblNomeCheque = new JLabel("Nome do Titular: ");
		contentPanel.add(lblNomeCheque, "cell 0 0");

		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 0,alignx trailing");
		
		jtfNomeTitular = new JTextField();
		jtfNomeTitular.setColumns(20);
		contentPanel.add(jtfNomeTitular, "cell 2 0");
		
		// Numero
		
		JLabel lblNumeroCheque = new JLabel("Numero do Cartão: ");
		contentPanel.add(lblNumeroCheque, "cell 0 1");
		
		JLabel l4 = new JLabel("*");
		l4.setForeground(Color.RED);
		contentPanel.add(l4, "cell 1 1,alignx trailing");
		
		jtfNumeroCartao = new JFormattedTextField(Text.buildMask("################"));
		jtfNumeroCartao.setColumns(20);
		contentPanel.add(jtfNumeroCartao, "cell 2 1");
		
		// Banco
		
		JLabel lblBanco = new JLabel("Banco: ");
		contentPanel.add(lblBanco, "cell 0 2");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 2,alignx trailing");
		
		String bandeira[] = {"MasterCard", "Visa", "HiperCard", "Elo", "American Express"};
		cboxBandeira = new JComboBox<String>(bandeira);
		contentPanel.add(cboxBandeira, "cell 2 2");
		
		// Data vencimento
		
		JLabel lblDataCheque = new JLabel("Data Vencimento: ");
		contentPanel.add(lblDataCheque, "cell 0 3");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 3,alignx trailing");
		
		dfDataCartao = new DateField();
		dfDataCartao.setColumns(10);
		contentPanel.add(dfDataCartao, "cell 2 3");
		
				
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvarCartao = new JButton("Salvar Cartão");
		btnSalvarCartao.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvarCartao);
		getRootPane().setDefaultButton(btnSalvarCartao);
		
		btnCancelarCartao = new JButton("Cancelar Cartão");
		btnCancelarCartao.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelarCartao);
		
	}
	
	public JButton getBtnSalvarCartao() {
		return btnSalvarCartao;
	}

	public JButton getBtnCancelar() {
		return btnCancelarCartao;
	}

	public JTextField getJtfNomeTitular() {
		return jtfNomeTitular;
	}

	public void setJtfNomeTitular(JTextField jtfNomeTitular) {
		this.jtfNomeTitular = jtfNomeTitular;
	}

	public DateField getDfDataCartao() {
		return dfDataCartao;
	}

	public void setDfDataCartao(DateField dfDataCartao) {
		this.dfDataCartao = dfDataCartao;
	}

	public JFormattedTextField getJtfNumeroCartao() {
		return jtfNumeroCartao;
	}

	public void setJtfNumeroCartao(JFormattedTextField jtfNumeroCartao) {
		this.jtfNumeroCartao = jtfNumeroCartao;
	}

	public JComboBox<String> getCboxBandeira() {
		return cboxBandeira;
	}

	public void setCboxBandeira(JComboBox<String> cboxBandeira) {
		this.cboxBandeira = cboxBandeira;
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
