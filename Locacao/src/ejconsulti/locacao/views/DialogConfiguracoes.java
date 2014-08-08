package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;

/**
 * Dialog de configurações
 * 
 * @author Edison Jr
 *
 */
public class DialogConfiguracoes extends JDialog {
	private static final long serialVersionUID = 1L;

	private JButton btnAlterarSenhaAdmin;

	private JTextField txtBanco;
	private JButton btnCarregarBanco;

	private JButton btnAdicionar;
	private JButton btnAlterarSenha;
	private JButton btnExcluir;

	private JTable table;
	private JButton btnSalvarBanco;
	private JButton btnRedefinirBanco;

	public DialogConfiguracoes(Window owner) {
		super(owner, "Configurações");
		initialize();
	}

	private void initialize() {
		setSize(600, 400);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[][][][][grow]", "[][][][grow]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblBancoDeDados = new JLabel("Banco de dados");
		contentPanel.add(lblBancoDeDados, "cell 0 1,alignx trailing");

		txtBanco = new JTextField(20);
		contentPanel.add(txtBanco, "flowx,cell 1 1");

		btnCarregarBanco = new JButton("");
		btnCarregarBanco.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/pesquisar.png")));
		btnCarregarBanco.setActionCommand("Carregar banco");
		contentPanel.add(btnCarregarBanco, "cell 2 1");

		btnSalvarBanco = new JButton("Salvar");
		btnSalvarBanco.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/confirmar.png")));
		btnSalvarBanco.setActionCommand("Salvar banco");
		contentPanel.add(btnSalvarBanco, "cell 3 1");

		btnRedefinirBanco = new JButton("Redefinir");
		btnRedefinirBanco.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/cancelar.png")));
		btnRedefinirBanco.setActionCommand("Redefinir banco");
		contentPanel.add(btnRedefinirBanco, "cell 4 1");

		JLabel lblAdministrador = new JLabel("Administrador");
		contentPanel.add(lblAdministrador, "cell 0 2,alignx trailing");

		btnAlterarSenhaAdmin = new JButton("Alterar senha");
		btnAlterarSenhaAdmin.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/editar.png")));
		btnAlterarSenhaAdmin.setActionCommand("Alterar senha admin");
		contentPanel.add(btnAlterarSenhaAdmin, "flowx,cell 1 2");

		JPanel panelUsuarios = new JPanel();
		panelUsuarios.setBorder(new TitledBorder(null, "Usu\u00E1rios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelUsuarios, "cell 0 3 5 1,grow");
		panelUsuarios.setLayout(new MigLayout("", "0[][][grow]0", "0[][grow]0"));

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/adicionar.png")));
		panelUsuarios.add(btnAdicionar, "cell 0 0");

		btnAlterarSenha = new JButton("Alterar senha");
		btnAlterarSenha.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/editar.png")));
		panelUsuarios.add(btnAlterarSenha, "cell 1 0");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(DialogConfiguracoes.class.getResource("/icones/excluir.png")));
		panelUsuarios.add(btnExcluir, "cell 2 0");

		JScrollPane scrollPane = new JScrollPane();
		panelUsuarios.add(scrollPane, "cell 0 1 3 1,grow");

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public JTextField getTxtBanco() {
		return txtBanco;
	}

	public JButton getBtnCarregarBanco() {
		return btnCarregarBanco;
	}

	public JButton getBtnSalvarBanco() {
		return btnSalvarBanco;
	}

	public JButton getBtnRedefinirBanco() {
		return btnRedefinirBanco;
	}

	public JButton getBtnAlterarSenhaAdmin() {
		return btnAlterarSenhaAdmin;
	}

	public JButton getBtnAdicionar() {
		return btnAdicionar;
	}

	public JButton getBtnAlterarSenha() {
		return btnAlterarSenha;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public JTable getTable() {
		return table;
	}

	@Override
	public void setVisible(boolean b) {
		if(b) {
			setLocationRelativeTo(null);
			setModal(true);
		}
		super.setVisible(b);
	}

}
