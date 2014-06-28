package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * Dialog de login
 * 
 * @author Edison Jr
 *
 */
public class DialogLogin extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtUsuario;
	private JPasswordField txtSenha;

	private JButton btnEntrar;
	private JButton btnCancelar;

	public DialogLogin(Window owner) {
		super(owner, "Login");
		intialize();
	}
	
	private void intialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow][][][grow]", "[grow][][][grow]"));
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		contentPanel.add(lblUsurio, "cell 1 1,alignx trailing");
		
		txtUsuario = new JTextField();
		contentPanel.add(txtUsuario, "cell 2 1");
		txtUsuario.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		contentPanel.add(lblSenha, "cell 1 2,alignx trailing");
		
		txtSenha = new JPasswordField();
		txtSenha.setColumns(10);
		contentPanel.add(txtSenha, "cell 2 2");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnEntrar = new JButton("Entrar");
		buttonPanel.add(btnEntrar);
		getRootPane().setDefaultButton(btnEntrar);
		
		btnCancelar = new JButton("Cancelar");
		buttonPanel.add(btnCancelar);
	}
	
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}
	
	public JPasswordField getTxtSenha() {
		return txtSenha;
	}
	
	public JButton getBtnEntrar() {
		return btnEntrar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
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
	
	@Override
	public void dispose() {
		txtUsuario.setText(null);
		txtSenha.setText(null);
		super.dispose();
	}

}
