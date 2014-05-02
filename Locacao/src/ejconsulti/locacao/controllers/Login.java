package ejconsulti.locacao.controllers;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Usuario;
import ejconsulti.locacao.views.DialogLogin;
import eso.utils.Hash;
import eso.utils.Log;

/**
 * Login
 * 
 * @author Edison Jr
 *
 */
public class Login implements ActionListener {
	public static final String TAG = Login.class.getSimpleName();

	private DialogLogin dialog;

	private boolean autorizar = false;

	public boolean autorizar() {
		return autorizar;
	}

	public Login(Window owner) {
		initialize(owner);
	}

	// Inicializar os componentes
	private void initialize(Window owner) {
		dialog = new DialogLogin(owner);

		addEvents();
		
		dialog.setVisible(true);
	}

	// Adicionar eventos
	private void addEvents() {
		dialog.getBtnEntrar().addActionListener(this);
		dialog.getBtnCancelar().addActionListener(this);
	}

	// Validar usu�rio e senha
	private void validate() {
		// Se n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null)
			new Configuracoes(dialog); // Inicia as configura��es

		// Se ainda n�o existir um banco de dados configurado
		if(DAO.getDatabase() == null) {
			dialog.dispose();
			return;
		}
		
		String usuario = dialog.getTxtUsuario().getText();
		if(usuario.length() == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Usu�rio'.", 
					"Acesso negado", JOptionPane.INFORMATION_MESSAGE);
			dialog.getTxtUsuario().requestFocus();
			return;
		}
		char[] senha = dialog.getTxtSenha().getPassword();

		//Se o usu�rio for 'Admin' abre a tela de configura��o
		if(usuario.equalsIgnoreCase(Usuario.ADMIN)) {
			ResultSet rs = null;
			try {
				rs = DAO.getDatabase().select(null, Usuario.TABLE, Usuario.NOME+" = ?", 
						new Object[]{Usuario.ADMIN}, null, null);

				boolean admin = false;
				// Se houver um 'Admin' cadastrado
				if(rs.next()) {
					String senhaAdmin = rs.getString(Usuario.SENHA);
					// Verifica a senha
					if(senhaAdmin.equals( Hash.md5((new String(senha)+Usuario.ADMIN+Usuario.KEY).getBytes()) ))
						admin = true;
					
				} else// Se n�o, o campo 'Senha' deve estar vazio
					if(senha.length == 0) {
						admin = true;
				}
				
				if(admin) {
					dialog.getTxtUsuario().requestFocus();
					new Configuracoes(dialog);
					dialog.getTxtUsuario().setText(null);
					dialog.getTxtSenha().setText(null);
				} else {
					JOptionPane.showMessageDialog(dialog, "Usu�rio e senha inv�lidos", "Acesso negado",
							JOptionPane.WARNING_MESSAGE);
				}

			} catch (SQLException e) {
				Log.e(TAG, "Erro ao fazer login com administrador", e);
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {}
				}
			}
			return;
		}

		// Verifica a senha
		if(senha.length == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Senha'.", 
					"Acesso negado", JOptionPane.INFORMATION_MESSAGE);
			dialog.getTxtSenha().requestFocus();
			return;
		}

		// Buscar usu�rio e senha
		ResultSet rs = null;
		try {
			rs = DAO.getDatabase().select(null, Usuario.TABLE, Usuario.NOME+" = ? AND "+Usuario.SENHA+" = ?", 
					new Object[]{usuario, Hash.md5((new String(senha)+usuario+Usuario.KEY).getBytes())}, null, null);

			if(rs.next())
				autorizar = true;

		} catch (SQLException e) {
			Log.e(TAG, "Erro ao fazer login", e);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}

		if(autorizar) // Acesso permitido
			dialog.dispose(); // Fecha o di�logo
		else //Acesso negado
			JOptionPane.showMessageDialog(dialog, "Usu�rio e senha inv�lidos", "Acesso negado",
					JOptionPane.WARNING_MESSAGE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Entrar":
			validate();
			break;
		case "Cancelar":
			dialog.dispose();
			break;
		}
	}

}
