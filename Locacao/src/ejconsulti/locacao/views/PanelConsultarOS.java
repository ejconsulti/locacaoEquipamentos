package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Despesa;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.StatusCellRenderer;

/**
 * Painel gen�rico para consulta, adi��o, edi��o e exclus�o
 * 
 * @author Edison Jr
 *
 */
public class PanelConsultarOS extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtPesquisar;
	private JButton btnPesquisar;
	private JButton btnImprimir;
	private JButton btnFiltrar;
	private JButton btnReciboEntrega;
	private JButton btnReciboDevolucao;
	
	public PanelConsultarOS() {
		super();
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setDefaultRenderer(Despesa.Status.class, new StatusCellRenderer());
		table.setDefaultRenderer(OrdemServico.Status.class, new StatusCellRenderer());
		table.setDefaultRenderer(Recebimento.Status.class, new StatusCellRenderer());
		scrollPane.setViewportView(table);
		
		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new MigLayout("", "[][][][][][][grow][][][]", "[]"));
		
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/adicionar.png")));
		headerPanel.add(btnAdicionar, "cell 0 0");
		
		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/editar.png")));
		headerPanel.add(btnEditar, "cell 1 0");
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/excluir.png")));
		headerPanel.add(btnExcluir, "cell 2 0");
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/imprimir.png")));
		headerPanel.add(btnImprimir, "cell 3 0");
		
		btnReciboEntrega = new JButton("Recibo de Entrega");
		btnReciboEntrega.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/recibo_entrega.png")));
		headerPanel.add(btnReciboEntrega, "cell 4 0");
		
		btnReciboDevolucao = new JButton("Recibo de Devolu\u00E7\u00E3o");
		btnReciboDevolucao.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/recibo_devolucao.png")));
		headerPanel.add(btnReciboDevolucao, "cell 5 0");
		
		txtPesquisar = new JTextField(15);
		headerPanel.add(txtPesquisar, "cell 7 0");
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/pesquisar.png")));
		headerPanel.add(btnPesquisar, "cell 8 0");
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setIcon(new ImageIcon(PanelConsultarOS.class.getResource("/icones/periodo.png")));
		headerPanel.add(btnFiltrar, "cell 9 0");
		
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
	
	public JButton getBtnReciboEntrega() {
		return btnReciboEntrega;
	}
	
	public JButton getBtnReciboDevolucao() {
		return btnReciboDevolucao;
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
