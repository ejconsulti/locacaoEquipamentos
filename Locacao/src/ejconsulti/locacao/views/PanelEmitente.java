package ejconsulti.locacao.views;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

/**
 * Painel de emitente
 * 
 * @author Sadao
 *
 */
public class PanelEmitente extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField txtNomeTitular;
	private JTextField txtTelefoneTitular;
	private JTextField txtCpfCnpj;
	
	private JLabel lblNome;
	private JLabel lblTelefone;
	private JLabel lblCpfCnpj;
	
	public JTextField getTxtCpfCnpj() {
		return txtCpfCnpj;
	}

	public void setTxtCpfCnpj(JTextField txtCpfCnpj) {
		this.txtCpfCnpj = txtCpfCnpj;
	}

	public JLabel getLblNome() {
		return lblNome;
	}

	public void setLblNome(JLabel lblNome) {
		this.lblNome = lblNome;
	}

	public JLabel getLblTelefone() {
		return lblTelefone;
	}

	public void setLblTelefone(JLabel lblTelefone) {
		this.lblTelefone = lblTelefone;
	}

	public JLabel getLblCpfCnpj() {
		return lblCpfCnpj;
	}

	public void setLblCpfCnpj(JLabel lblCpfCnpj) {
		this.lblCpfCnpj = lblCpfCnpj;
	}

	public PanelEmitente() {
		initialize();
	}

	private void initialize() {
		setLayout(new MigLayout("", "[right]2[][][][][]", "[][][][][][]"));

		lblNome = new JLabel("Nome Titular");
		add(lblNome, "cell 0 0");
		
		txtNomeTitular = new JTextField(27);
		add(txtNomeTitular, "flowx,cell 1 0 4 1");

		lblTelefone = new JLabel("Telefone Cartão");
		add(lblTelefone, "flowx,cell 0 1");
		
		txtTelefoneTitular = new JTextField(27);
		add(txtTelefoneTitular, "flowx,cell 1 1");
		
		lblCpfCnpj = new JLabel("CPF / CNPJ");
		add(lblCpfCnpj, "cell 0 2,alignx right");
		
		txtCpfCnpj = new JTextField(27);
		add(txtCpfCnpj, "cell 1 2");
	}

	public JTextField getTxtNomeTitular() {
		return txtNomeTitular;
	}

	public void setTxtNomeTitular(JTextField txtNomeTitular) {
		this.txtNomeTitular = txtNomeTitular;
	}

	public JTextField getTxtTelefoneTitular() {
		return txtTelefoneTitular;
	}

	public void setTxtTelefoneTitular(JTextField txtTelefoneTitular) {
		this.txtTelefoneTitular = txtTelefoneTitular;
	}

	public JTextField getCpfCnpj() {
		return txtCpfCnpj;
	}

	public void setCpfCnpj(JTextField txtCpfCnpj) {
		this.txtCpfCnpj = txtCpfCnpj;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for(Component c : getComponents())
			c.setEnabled(enabled);
		super.setEnabled(enabled);
	}
	
	public void setEditable(boolean editable) {
		for(Component c : getComponents()) {
			if(c instanceof JTextComponent)
				((JTextComponent) c).setEditable(editable);
			else
				c.setEnabled(false);
		}
	}

}
