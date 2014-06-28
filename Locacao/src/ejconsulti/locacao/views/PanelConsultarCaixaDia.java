package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.StatusCellRenderer;

/**
 * Painel gen�rico para consulta, adi��o, edi��o e exclus�o
 * 
 * @author Edison Jr
 *
 */
public class PanelConsultarCaixaDia extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JButton btnEntrada;
	private JButton btnSaida;
	
	public PanelConsultarCaixaDia() {
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
		headerPanel.setLayout(new MigLayout("", "[][][][grow][][]", "[]"));
		
		btnEntrada = new JButton("Adicionar");
		headerPanel.add(btnEntrada, "cell 0 0");
		
		btnSaida = new JButton("Editar");
		headerPanel.add(btnSaida, "cell 1 0");
		
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JButton getBtnEntrada() {
		return btnEntrada;
	}
	
	public JButton getBtnSaida() {
		return btnSaida;
	}

}
