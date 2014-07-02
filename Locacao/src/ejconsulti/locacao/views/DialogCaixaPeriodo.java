package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.HistoricoRecebimentoTableModel;
import ejconsulti.locacao.models.OrdemDeServico;
import ejconsulti.locacao.models.StatusCellRenderer;
import ejconsulti.locacao.models.TipoRecebimento;
import ejconsulti.locacao.models.OrdemDeServico.Status;
import eso.components.DateField;
import eso.components.DoubleField;

public class DialogCaixaPeriodo extends JDialog {
private static final long serialVersionUID = 1L;

	private DateField txtData;
	private DateField txtDataFim;
	private DoubleField txtEntrada;
	private DoubleField txtSaida;
	private DoubleField txtSaldo;
	private JButton btnBuscar;

	private JButton btnSalvar;
	private JButton btnCancelar;

	public DialogCaixaPeriodo(Window owner, String title) {
		super(owner, title);
		intialize();
	}
	
	private void intialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblDataInicio = new JLabel("Data Inicial");
		contentPanel.add(lblDataInicio, "cell 0 0,alignx trailing");
		
		txtData = new DateField();
		txtData.setColumns(8);
		contentPanel.add(txtData, "cell 1 0");
		
		JLabel lblDataFim = new JLabel("Data Final");
		contentPanel.add(lblDataFim, "cell 2 0,alignx trailing");
		
		txtDataFim = new DateField();
		txtDataFim.setColumns(8);
		contentPanel.add(txtDataFim, "cell 3 0");
		
		btnBuscar = new JButton("buscar");
		contentPanel.add(btnBuscar, "cell 4 0");
		
		JLabel lblEntrada = new JLabel("Total Entrada");
		contentPanel.add(lblEntrada, "cell 0 1");
		
		txtEntrada = new DoubleField(0.0);
		txtEntrada.setColumns(8);
		contentPanel.add(txtEntrada, "cell 1 1");
		
		JLabel lblSaida = new JLabel("Total Saída");
		contentPanel.add(lblSaida, "cell 2 1");
		
		txtSaida = new DoubleField(0.0);
		txtSaida.setColumns(8);
		contentPanel.add(txtSaida, "cell 3 1");
		
		JLabel lblSaldo = new JLabel("Saldo");
		contentPanel.add(lblSaldo, "cell 0 2");
		
		txtSaldo = new DoubleField(0.0);
		txtSaldo.setColumns(8);
		contentPanel.add(txtSaldo, "cell 1 2");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		buttonPanel.add(btnCancelar);
	}

	public DateField getTxtData() {
		return txtData;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}
	
	public DoubleField getTxtEntrada() {
		return txtEntrada;
	}

	public DoubleField getTxtSaida() {
		return txtSaida;
	}

	public DoubleField getTxtSaldo() {
		return txtSaldo;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public DateField getTxtDataFim() {
		return txtDataFim;
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			pack();
			setLocationRelativeTo(null);
		}
		super.setVisible(b);
	}
}
