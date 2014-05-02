package ejconsulti.locacao.controllers;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import ejconsulti.locacao.assets.Config;
import ejconsulti.locacao.assets.DAO;
import ejconsulti.locacao.models.Usuario;
import ejconsulti.locacao.models.UsuarioTableModel;
import ejconsulti.locacao.views.DialogAlterarSenha;
import ejconsulti.locacao.views.DialogConfiguracoes;
import eso.database.ContentValues;
import eso.utils.FileFilterFactory;
import eso.utils.Hash;
import eso.utils.Log;

/**
 * Configurações
 * 
 * @author Edison Jr
 *
 */
public class Configuracoes implements ActionListener {
	public static final String TAG = Configuracoes.class.getSimpleName();

	private DialogConfiguracoes dialog;

	private UsuarioTableModel modelUsuarios;

	public Configuracoes(Window owner) {
		initialize(owner);
	}

	private void initialize(Window owner) {
		dialog = new DialogConfiguracoes(owner);
		dialog.getTxtBanco().setText(Config.getProperty(DAO.DATABASE));

		modelUsuarios = new UsuarioTableModel();
		dialog.getTable().setModel(modelUsuarios);

		carregarUsuarios();

		addEvents();

		dialog.setVisible(true);
	}

	private void addEvents() {
		dialog.getBtnAlterarSenhaAdmin().addActionListener(this);
		dialog.getBtnAdicionar().addActionListener(this);
		dialog.getBtnAlterarSenha().addActionListener(this);
		dialog.getBtnExcluir().addActionListener(this);

		dialog.getBtnCarregarBanco().addActionListener(this);
		dialog.getBtnSalvarBanco().addActionListener(this);
		dialog.getBtnRedefinirBanco().addActionListener(this);
		
		dialog.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
					alterarSenha();
				} else if (e.getButton() == MouseEvent.BUTTON3) {
		            JPopupMenu menu = new JPopupMenu();
		            
		            JMenuItem alterarSenha = new JMenuItem("Alterar senha");
		            JMenuItem excluir = new JMenuItem("Excluir");
		  
		            alterarSenha.addActionListener(Configuracoes.this);
		            excluir.addActionListener(Configuracoes.this);
		            
		            menu.add(alterarSenha);
		            menu.add(excluir);
		            
		            menu.show(dialog.getTable(), e.getX(), e.getY());
		        }
			}
		});
	}

	private void carregarUsuarios() {
		// Verificar conexão com banco de dados e carregar usuários
		if(DAO.getDatabase() != null) {
			ResultSet rs = null;
			try {
				rs = DAO.getDatabase().select(null, Usuario.TABLE, Usuario.NOME+" <> ?", new Object[]{Usuario.ADMIN}, null, Usuario.NOME);

				while(rs.next()) {
					Usuario o = Usuario.rsToObject(rs);
					modelUsuarios.add(o);
				}

			} catch (SQLException ex) {
				Log.e(TAG, "Erro ao carregar usuários", ex);
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {}
				}
			}
		}
	}

	private void carregarBanco() {
		File banco = null;

		// Cria a janela de seleção de arquivo
		JFileChooser fc = new JFileChooser(banco);
		fc.setDialogTitle("Banco de dados");
		fc.setPreferredSize(new Dimension(600, 400));
		fc.setMultiSelectionEnabled(false);
		fc.setFileFilter(new FileFilterFactory("Banco de dados (.db)", ".db"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Verifica o texto do banco de dados e carrega, se existir
		String caminhoBanco = dialog.getTxtBanco().getText();
		if(caminhoBanco.length() > 0) {
			banco = new File(caminhoBanco);
			if(banco.exists())
				fc.setSelectedFile(banco);
		}

		File novoBanco = null;

		// Abre janela de seleção de arquivo
		int retorno = fc.showOpenDialog(dialog);
		if(retorno == JFileChooser.APPROVE_OPTION)
			novoBanco = fc.getSelectedFile();

		// Se o banco de dados existe 
		if(novoBanco != null && novoBanco.exists()) {
			try { // Alterar o caminho do banco
				dialog.getTxtBanco().setText(novoBanco.getCanonicalPath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void salvarBanco() {
		String local = dialog.getTxtBanco().getText();
		if(local.length() == 0) {
			Config.setProperty(DAO.DATABASE, "");
			DAO.setDatabase(null);
			dialog.dispose();
			return;
		}
		File banco = new File(local);

		// Verifica se o banco existe e está acessível
		if(!banco.isFile() || !banco.canRead() || !banco.canWrite()) {
			JOptionPane.showMessageDialog(dialog, "O banco de dados não existe ou está inacessível.", "Banco de dados inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			Config.setProperty(DAO.DATABASE, banco.getCanonicalPath());
			DAO.setDatabase(null);
			dialog.dispose();
		} catch (IOException e) {
			Log.e(TAG, "Erro ao carregar banco de dados", e);
		}
	}

	private void alterarSenhaAdmin() {
		// Verificar conexão com banco de dados
		if(DAO.getDatabase() == null) {
			JOptionPane.showMessageDialog(dialog, "O banco de dados não existe ou está inacessível.", "Banco de dados inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Alterar senha
		AlterarSenha alterar = new AlterarSenha();
		char[] senha = alterar.getSenha();
		if(senha == null)
			return;

		try {
			ContentValues values = new ContentValues();
			values.put(Usuario.SENHA, Hash.md5((new String(senha)+Usuario.ADMIN+Usuario.KEY).getBytes()));

			int result = DAO.getDatabase().update(Usuario.TABLE, values, Usuario.NOME+" = ?", Usuario.ADMIN);
			if(result <= 0) {
				values.put(Usuario.NOME, Usuario.ADMIN);
				DAO.getDatabase().insert(Usuario.TABLE, values);
			}
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao alterar senha de administrador", e);
		}
	}

	private void adicionarUsuario() {
		// Verificar conexão com banco de dados
		if(DAO.getDatabase() == null) {
			JOptionPane.showMessageDialog(dialog, "O banco de dados não existe ou está inacessível.", "Banco de dados inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String usuario = JOptionPane.showInputDialog(dialog,  "Usuário:", "Adicionar usuário",
				JOptionPane.PLAIN_MESSAGE);
		if(usuario == null)
			return;

		if(usuario.length() == 0) {
			JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Usuário'.", 
					"Adicionar usuário", JOptionPane.INFORMATION_MESSAGE);
			adicionarUsuario(); // Tenta novamente
			return;
		}
		
		for(Usuario u : modelUsuarios.getRows()) {
			if(u.getNome().equals(usuario)) {
				JOptionPane.showMessageDialog(dialog, usuario+" já existe.", 
						"Adicionar usuário", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		// Alterar senha
		AlterarSenha alterar = new AlterarSenha();
		char[] senha = alterar.getSenha();
		if(senha == null)
			return;

		ContentValues values = new ContentValues();
		values.put(Usuario.NOME, usuario);
		values.put(Usuario.SENHA, Hash.md5((new String(senha)+usuario+Usuario.KEY).getBytes()));
		try {
			DAO.getDatabase().insert(Usuario.TABLE, values);
		} catch (SQLException e) {
			Log.e(TAG, "Erro ao adicionar usuário", e);
		}
		
		// Recarregar usuários
		modelUsuarios.clear();
		carregarUsuarios();
	}

	private void alterarSenha() {
		// Verificar conexão com banco de dados
		if(DAO.getDatabase() == null) {
			JOptionPane.showMessageDialog(dialog, "O banco de dados não existe ou está inacessível.", "Banco de dados inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int row = dialog.getTable().getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(dialog, "Selecione um usuário.", "Alterar senha",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Usuario u = modelUsuarios.get(row);

		// Alterar senha
		AlterarSenha alterar = new AlterarSenha();
		char[] senha = alterar.getSenha();
		if(senha == null)
			return;

		try {
			ContentValues values = new ContentValues();
			values.put(Usuario.SENHA, Hash.md5((new String(senha)+u.getNome()+Usuario.KEY).getBytes()));

			DAO.getDatabase().update(Usuario.TABLE, values, Usuario.ID+" = ?", u.getId());

		} catch (SQLException e) {
			Log.e(TAG, "Erro ao alterar senha de usuário: "+u.getNome(), e);
		}
	}

	private void excluirUsuario() {
		// Verificar conexão com banco de dados
		if(DAO.getDatabase() == null) {
			JOptionPane.showMessageDialog(dialog, "O banco de dados não existe ou está inacessível.", "Banco de dados inválido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int row = dialog.getTable().getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(dialog, "Selecione um usuário.", "Excluir usuário",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Usuario u = modelUsuarios.get(row);

		int option = JOptionPane.showConfirmDialog(dialog, 
				String.format("Deseja realmente excluir usuário: %s?", u.getNome()), "Excluir usuário", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(option == JOptionPane.YES_OPTION) {
			try {
				DAO.getDatabase().delete(Usuario.TABLE, Usuario.ID+" = ?", u.getId());
			} catch (SQLException e) {
				Log.e(TAG, "Erro ao excluir usuário: ", e);
			}

			// Recarregar usuários
			modelUsuarios.clear();
			carregarUsuarios();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Alterar senha admin":
			alterarSenhaAdmin();
			break;
		case "Carregar banco":
			carregarBanco();
			break;
		case "Salvar banco":
			salvarBanco();
			break;
		case "Redefinir banco":
			dialog.getTxtBanco().setText("");
			salvarBanco();
			break;
		case "Adicionar":
			adicionarUsuario();
			break;
		case "Alterar senha":
			alterarSenha();
			break;
		case "Excluir":
			excluirUsuario();
			break;
		}
	}

	/**
	 * Alterar senha
	 * 
	 * @author Edison Jr
	 *
	 */
	private class AlterarSenha implements ActionListener {

		private DialogAlterarSenha dialog;

		private char[] senha = null;

		public AlterarSenha() {
			initialize();
		}

		private void initialize() {
			dialog = new DialogAlterarSenha(Configuracoes.this.dialog);

			addEvents();

			dialog.setVisible(true);
		}

		private void addEvents() {
			dialog.getBtnSalvar().addActionListener(this);
			dialog.getBtnCancelar().addActionListener(this);
		}

		private void salvar() {
			char[] senha = dialog.getTxtSenha().getPassword();
			if(senha.length == 0) {
				JOptionPane.showMessageDialog(dialog, "Favor preencher campo 'Senha'.", 
						"Acesso negado", JOptionPane.INFORMATION_MESSAGE);
				dialog.getTxtSenha().requestFocus();
				return;
			}
			char[] confirmarSenha = dialog.getTxtConfirmarSenha().getPassword();
			if(!new String(senha).equals(new String(confirmarSenha))) {
				JOptionPane.showMessageDialog(dialog, "As senhas não correspondem.", 
						"Acesso negado", JOptionPane.WARNING_MESSAGE);
				dialog.getTxtConfirmarSenha().requestFocus();
				return;
			}
			this.senha = senha;
			dialog.dispose();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Salvar":
				salvar();
				break;
			case "Cancelar":
				dialog.dispose();
				break;
			}
		}

		public char[] getSenha() {
			return senha;
		}

	}
}
