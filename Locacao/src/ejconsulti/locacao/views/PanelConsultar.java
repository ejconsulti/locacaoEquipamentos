package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

/**
 * Painel genérico para consulta, adição, edição e exclusão
 * 
 * @author Edison Jr
 *
 */
public class PanelConsultar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtPesquisar;
	private JButton btnPesquisar;
	
	public PanelConsultar() {
		super();
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new MigLayout("", "[][][][grow][][]", "[]"));
		
		btnAdicionar = new JButton("Adicionar");
		headerPanel.add(btnAdicionar, "cell 0 0");
		
		btnEditar = new JButton("Editar");
		headerPanel.add(btnEditar, "cell 1 0");
		
		btnExcluir = new JButton("Excluir");
		headerPanel.add(btnExcluir, "cell 2 0");
		
		txtPesquisar = new JTextField(15);
		headerPanel.add(txtPesquisar, "cell 4 0");
		
		btnPesquisar = new JButton("Pesquisar");
		headerPanel.add(btnPesquisar, "cell 5 0");
		
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JButton getBtnAdicionar() {
		return btnAdicionar;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}
	
	public JButton getBtnExcluir() {
		return btnExcluir;
	}
	
	public JTextField getTxtPesquisar() {
		return txtPesquisar;
	}
	
	public JButton getBtnPesquisar() {
		return btnPesquisar;
	}
	
}
