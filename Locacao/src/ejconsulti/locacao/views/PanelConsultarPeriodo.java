package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
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
public class PanelConsultarPeriodo extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtPesquisar;
	private JButton btnPesquisar;
	private JButton btnImprimir;
	private JButton btnFiltrar;
	
	public PanelConsultarPeriodo() {
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
		
		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new MigLayout("", "[][][][][grow][][][]", "[]"));
		
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/adicionar.png")));
		headerPanel.add(btnAdicionar, "cell 0 0");
		
		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/editar.png")));
		headerPanel.add(btnEditar, "cell 1 0");
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/excluir.png")));
		headerPanel.add(btnExcluir, "cell 2 0");
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/imprimir.png")));
		headerPanel.add(btnImprimir, "cell 3 0");
		
		txtPesquisar = new JTextField(15);
		headerPanel.add(txtPesquisar, "cell 5 0");
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/pesquisar.png")));
		headerPanel.add(btnPesquisar, "cell 6 0");
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setIcon(new ImageIcon(PanelConsultarPeriodo.class.getResource("/icones/periodo.png")));
		headerPanel.add(btnFiltrar, "cell 7 0");
		
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
	
	public JButton getBtnImprimir() {
		return btnImprimir;
	}
	
	public JTextField getTxtPesquisar() {
		return txtPesquisar;
	}
	
	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}
	
	public JButton getBtnPesquisar() {
		return btnPesquisar;
	}
	
}
