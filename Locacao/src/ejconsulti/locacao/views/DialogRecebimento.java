package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.HistoricoRecebimentoTableModel;
import ejconsulti.locacao.models.OrdemServico;
import ejconsulti.locacao.models.OrdemServico.Status;
import ejconsulti.locacao.models.Recebimento;
import ejconsulti.locacao.models.StatusCellRenderer;
import eso.components.DoubleField;

public class DialogRecebimento extends JDialog {
private static final long serialVersionUID = 1L;
	
	private JTable table;

	private JComboBox<OrdemServico> cboxOrdemServico;
	private JComboBox<Recebimento.Tipo> cboxTipo;
	private DoubleField txtValorReceber;
	private DoubleField txtValorTotal;
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
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
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
		
		cboxOrdemServico = new JComboBox<OrdemServico>();
		contentPanel.add(cboxOrdemServico, "cell 2 0");
		
		JLabel lblTipoRecebimento = new JLabel("Tipo de Entrada");
		contentPanel.add(lblTipoRecebimento, "cell 0 1");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 1,alignx trailing");
		
		cboxTipo = new JComboBox<Recebimento.Tipo>(Recebimento.Tipo.values());
		contentPanel.add(cboxTipo, "cell 2 1");
		
		JLabel lblQuantidadeReceber = new JLabel("Valor à receber");
		contentPanel.add(lblQuantidadeReceber, "cell 0 2");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 2,alignx trailing");
		
		txtValorReceber = new DoubleField(0.0);
		txtValorReceber.setColumns(8);
		contentPanel.add(txtValorReceber, "cell 2 2");
		
		JLabel lblQuantidadeTotal = new JLabel("Valor total");
		contentPanel.add(lblQuantidadeTotal, "cell 0 3");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 3,alignx trailing");
		
		txtValorTotal = new DoubleField(0.0);
		txtValorTotal.setColumns(8);
		contentPanel.add(txtValorTotal, "cell 2 3");
		
		historicoRecebimentoTableModel = new HistoricoRecebimentoTableModel();

		tabela = new JTable(historicoRecebimentoTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(400, 100));
		contentPanel.add(new JScrollPane(tabela), "cell 0 4 3");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);
	}
	
	public JTable getTable() {
		return table;
	}

	public JComboBox<OrdemServico> getCboxOrdemServico() {
		return cboxOrdemServico;
	}

	public JComboBox<Recebimento.Tipo> getCboxTipo() {
		return cboxTipo;
	}
	
	public DoubleField getTxtValorReceber() {
		return txtValorReceber;
	}

	public DoubleField getTxtValorTotal() {
		return txtValorTotal;
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
