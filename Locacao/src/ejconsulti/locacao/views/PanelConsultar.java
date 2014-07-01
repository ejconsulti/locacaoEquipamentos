package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.StatusCellRenderer;

/**
 * Painel gen�rico para consulta, adi��o, edi��o e exclus�o
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
	private JPanel headerPanel;
	private JButton btnImprimir;
	
	public PanelConsultar() {
		super();
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setDefaultRenderer(Status.class, new StatusCellRenderer());
		scrollPane.setViewportView(table);
		
		headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow][][]", "[]"));
		
		btnAdicionar = new JButton("Adicionar");
		headerPanel.add(btnAdicionar, "cell 0 0");
		
		btnEditar = new JButton("Editar");
		headerPanel.add(btnEditar, "cell 1 0");
		
		btnExcluir = new JButton("Excluir");
		headerPanel.add(btnExcluir, "cell 2 0");
		
		btnImprimir = new JButton("Imprimir");
		headerPanel.add(btnImprimir, "cell 3 0");
		
		txtPesquisar = new JTextField(15);
		headerPanel.add(txtPesquisar, "cell 13 0");
		
		btnPesquisar = new JButton("Pesquisar");
		headerPanel.add(btnPesquisar, "cell 14 0");
		
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
	
	public JPanel getHeaderPanel() {
		return headerPanel;
	}
	
	public JButton getBtnImprimir() {
		return btnImprimir;
	}
	
}
