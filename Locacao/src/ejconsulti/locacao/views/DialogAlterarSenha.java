package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

/**
 * Dialog de alterar senha
 * 
 * @author Edison Jr
 *
 */
public class DialogAlterarSenha extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JPasswordField txtSenha;
	private JPasswordField txtConfirmarSenha;
	
	private JButton btnSalvar;
	private JButton btnCancelar;

	public DialogAlterarSenha(Window owner) {
		super(owner, "Alterar senha");
		initialize();
	}
	
	private void initialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblSenha = new JLabel("Senha");
		contentPanel.add(lblSenha, "cell 0 0,alignx trailing");
		
		txtSenha = new JPasswordField(10);
		contentPanel.add(txtSenha, "cell 1 0");
		
		JLabel lblReinserirSenha = new JLabel("Confirmar senha");
		contentPanel.add(lblReinserirSenha, "cell 0 1,alignx trailing");
		
		txtConfirmarSenha = new JPasswordField(10);
		contentPanel.add(txtConfirmarSenha, "cell 1 1");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogAlterarSenha.class.getResource("/icones/confirmar.png")));
		panel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogAlterarSenha.class.getResource("/icones/cancelar.png")));
		panel.add(btnCancelar);
	}

	public JPasswordField getTxtSenha() {
		return txtSenha;
	}

	public JPasswordField getTxtConfirmarSenha() {
		return txtConfirmarSenha;
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
			setModal(true);
		}
		super.setVisible(b);
	}
}
