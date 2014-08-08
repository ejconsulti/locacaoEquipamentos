package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import eso.utils.Text;
import javax.swing.ImageIcon;

/**
 * Dialog de cliente
 * 
 * @author Edison Jr
 *
 */
public class DialogCliente extends JDialog implements ItemListener {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome;
	private JTextField txtEmail;
	private JTextField txtRg;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtTelefone;

	private PanelEndereco panelEndereco;
	private PanelEndereco panelEnderecoEntrega;
	private JCheckBox cbEntregarOutroEnd;

	private JButton btnSalvar;
	private JButton btnCancelar;
	private JLabel label;
	
	public DialogCliente(Window owner, String title) {
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

		JLabel lblRg = new JLabel("RG");
		contentPanel.add(lblRg, "cell 0 1");

		txtRg = new JTextField(10);
		contentPanel.add(txtRg, "cell 2 1");
		
		label = new JLabel("*");
		label.setForeground(Color.RED);
		contentPanel.add(label, "flowx,cell 1 2");

		JLabel lblCpf = new JLabel("CPF");
		contentPanel.add(lblCpf, "cell 0 2");

		txtCpf = new JFormattedTextField(Text.buildMask("###.###.###-##"));
		txtCpf.setColumns(10);
		contentPanel.add(txtCpf, "cell 2 2");

		JLabel lblTelefone = new JLabel("Telefone");
		contentPanel.add(lblTelefone, "cell 0 3,alignx trailing");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 3,alignx trailing");

		txtTelefone = new JFormattedTextField(Text.buildMask("(##)####-####"));
		contentPanel.add(txtTelefone, "cell 2 3");
		txtTelefone.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		contentPanel.add(lblEmail, "cell 0 4,alignx trailing");

		txtEmail = new JTextField(20);
		contentPanel.add(txtEmail, "cell 2 4");
		
		cbEntregarOutroEnd = new JCheckBox("Entregar em outro endere\u00E7o");
		cbEntregarOutroEnd.addItemListener(this);
		contentPanel.add(cbEntregarOutroEnd, "cell 3 4");
		
		panelEndereco = new PanelEndereco();
		panelEndereco.setBorder(new TitledBorder(null, "Endere\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelEndereco, "cell 0 5 3 1");
		
		panelEnderecoEntrega = new PanelEndereco();
		panelEnderecoEntrega.setBorder(new TitledBorder(null, "Endere\u00E7o de entrega", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEnderecoEntrega.setEnabled(false);
		contentPanel.add(panelEnderecoEntrega, "cell 3 5");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogCliente.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogCliente.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);
		
		pack();
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
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

	public PanelEndereco getPanelEndereco() {
		return panelEndereco;
	}
	
	public JCheckBox getCbEntregarOutroEnd() {
		return cbEntregarOutroEnd;
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
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		panelEnderecoEntrega.setEnabled(cbEntregarOutroEnd.isSelected());
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			pack();
			setLocationRelativeTo(null);
			setModal(true);
		}
		super.setVisible(b);
	}

}
