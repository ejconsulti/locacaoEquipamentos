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
import ejconsulti.locacao.models.OrdemDeServico.Status;
import ejconsulti.locacao.models.StatusCellRenderer;
import ejconsulti.locacao.models.TipoRecebimento;
import eso.components.DoubleField;

public class DialogRecebimento extends JDialog {
private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JComboBox<OrdemDeServico> jcbOrdemServico;
	private JComboBox<TipoRecebimento> jcbTipo;
	private DoubleField txtQuantidadeReceber;
	private DoubleField txtQuantidadeTotal;
	private HistoricoRecebimentoTableModel historicoRecebimentoTableModel;
	private JTable tabela;

	private JButton btnSalvar;
	private JButton btnCancelar;

	public DialogRecebimento(Window owner, String title) {
		super(owner, title);
		intialize();
	}
	
	private void intialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setDefaultRenderer(Status.class, new StatusCellRenderer());
		scrollPane.setViewportView(table);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblOrdemServico = new JLabel("Número Ordem de Serviço");
		contentPanel.add(lblOrdemServico, "cell 0 0,alignx trailing");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0,alignx trailing");
		
		jcbOrdemServico = new JComboBox<OrdemDeServico>();
		contentPanel.add(jcbOrdemServico, "cell 2 0");
		
		JLabel lblTipoRecebimento = new JLabel("Tipo de Entrada");
		contentPanel.add(lblTipoRecebimento, "cell 0 1");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 1,alignx trailing");
		
		jcbTipo = new JComboBox<TipoRecebimento>(TipoRecebimento.values());
		contentPanel.add(jcbTipo, "cell 2 1");
		
		JLabel lblQuantidadeReceber = new JLabel("Quantidade a receber");
		contentPanel.add(lblQuantidadeReceber, "cell 0 2");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 2,alignx trailing");
		
		txtQuantidadeReceber = new DoubleField(0.0);
		txtQuantidadeReceber.setColumns(8);
		contentPanel.add(txtQuantidadeReceber, "cell 2 2");
		
		JLabel lblQuantidadeTotal = new JLabel("Quantidade total");
		contentPanel.add(lblQuantidadeTotal, "cell 0 3");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 3,alignx trailing");
		
		txtQuantidadeTotal = new DoubleField(0.0);
		txtQuantidadeTotal.setColumns(8);
		contentPanel.add(txtQuantidadeTotal, "cell 2 3");
		
		historicoRecebimentoTableModel = new HistoricoRecebimentoTableModel();

		tabela = new JTable(historicoRecebimentoTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(400, 100));
		contentPanel.add(new JScrollPane(tabela), "cell 0 4 3");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		buttonPanel.add(btnCancelar);
	}
	
	public JTable getTable() {
		return table;
	}

	public JComboBox<OrdemDeServico> getJcbOrdemServico() {
		return jcbOrdemServico;
	}

	public JComboBox<TipoRecebimento> getJcbTipo() {
		return jcbTipo;
	}
	
	public DoubleField getTxtQuantidadeReceber() {
		return txtQuantidadeReceber;
	}

	public DoubleField getTxtQuantidadeTotal() {
		return txtQuantidadeTotal;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public HistoricoRecebimentoTableModel getHistoricoRecebimentoTableModel() {
		return historicoRecebimentoTableModel;
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
