package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.StatusCellRenderer;
import eso.components.DoubleField;

/**
 * Painel gen�rico para consulta, adi��o, edi��o e exclus�o
 * 
 * @author Edison Jr
 *
 */
public class PanelConsultarCaixa extends JPanel {
	private static final long serialVersionUID = 1L;

	private DoubleField txtSaldoDiaAnterior;
	private DoubleField txtSaldoEntrada;
	private DoubleField txtSaldoSaida;
	private DoubleField txtSaldoTotal;
	
	private JTable table;

	private JButton btnPorDia;
	private JButton btnPorPeriodo;
	private JButton btnHoje;
	
	public PanelConsultarCaixa() {
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
		headerPanel.setLayout(new MigLayout("", "[][][][]", "[]"));
		
		btnPorDia = new JButton("Dia");
		btnPorDia.setIcon(new ImageIcon(PanelConsultarCaixa.class.getResource("/icones/dia.png")));
		headerPanel.add(btnPorDia, "cell 0 0");
		
		btnPorPeriodo = new JButton("Período");
		btnPorPeriodo.setIcon(new ImageIcon(PanelConsultarCaixa.class.getResource("/icones/periodo.png")));
		headerPanel.add(btnPorPeriodo, "cell 1 0");
		
		btnHoje = new JButton("Hoje");
		btnHoje.setIcon(new ImageIcon(PanelConsultarCaixa.class.getResource("/icones/hoje.png")));
		headerPanel.add(btnHoje, "cell 2 0");
		
		JLabel lblSaldoDiaAnterior = new JLabel("Saldo do Dia Anterior");
		headerPanel.add(lblSaldoDiaAnterior, "cell 3 0");
		
		txtSaldoDiaAnterior = new DoubleField();
		txtSaldoDiaAnterior.setColumns(8);
		txtSaldoDiaAnterior.setFocusable(false);
		headerPanel.add(txtSaldoDiaAnterior, "cell 4 0");
		
		JLabel lblSaldoEntrada = new JLabel("Entrada Total");
		headerPanel.add(lblSaldoEntrada, "cell 5 0");
		
		txtSaldoEntrada = new DoubleField();
		txtSaldoEntrada.setColumns(8);
		txtSaldoEntrada.setFocusable(false);
		headerPanel.add(txtSaldoEntrada, "cell 6 0");
		
		JLabel lblSaldoSaida = new JLabel("Saída Total");
		headerPanel.add(lblSaldoSaida, "cell 7 0");
		
		txtSaldoSaida = new DoubleField();
		txtSaldoSaida.setColumns(8);
		txtSaldoSaida.setFocusable(false);
		headerPanel.add(txtSaldoSaida, "cell 8 0");
		
		JLabel lblSaldoTotal = new JLabel("Saldo Total");
		headerPanel.add(lblSaldoTotal, "cell 9 0");
		
		txtSaldoTotal = new DoubleField();
		txtSaldoTotal.setColumns(8);
		txtSaldoTotal.setFocusable(false);
		headerPanel.add(txtSaldoTotal, "cell 10 0");
		
	}
	
	public JTable getTable() {
		return table;
	}

	public DoubleField getTxtSaldoDiaAnterior() {
		return txtSaldoDiaAnterior;
	}

	public DoubleField getTxtSaldoEntrada() {
		return txtSaldoEntrada;
	}

	public DoubleField getTxtSaldoSaida() {
		return txtSaldoSaida;
	}

	public DoubleField getTxtSaldoTotal() {
		return txtSaldoTotal;
	}

	public JButton getBtnPorDia() {
		return btnPorDia;
	}

	public JButton getBtnPorPeriodo() {
		return btnPorPeriodo;
	}

	public JButton getBtnHoje() {
		return btnHoje;
	}

}
