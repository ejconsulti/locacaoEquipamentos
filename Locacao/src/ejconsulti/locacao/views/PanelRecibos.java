package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SingleSelectionModel;

import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.StatusCellRenderer;

import net.miginfocom.swing.MigLayout;

public class PanelRecibos extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JButton btnReciboEntrega;
	private JButton btnReciboDevolucao;
	private JTextField txtPesquisar;
	private JButton btnPesquisar;
	
	public PanelRecibos() {
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
		table.setSelectionMode(0);
		
		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new MigLayout("", "[][][][grow][][]", "[]"));
		
		btnReciboEntrega = new JButton("Recibo de Entrega");
		headerPanel.add(btnReciboEntrega, "cell 0 0");
		
		btnReciboDevolucao = new JButton("Recibo de Devolução");
		headerPanel.add(btnReciboDevolucao, "cell 1 0");
		
		txtPesquisar = new JTextField(15);
		headerPanel.add(txtPesquisar, "cell 4 0");
		
		btnPesquisar = new JButton("Pesquisar");
		headerPanel.add(btnPesquisar, "cell 5 0");
		
	}

	public JTable getTable() {
		return table;
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

	public JButton getBtnPesquisar() {
		return btnPesquisar;
	}
	
	
}
