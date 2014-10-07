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
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import ejconsulti.locacao.models.Emitente;
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
	
	private PanelEmitente panelEmitente;
	private PanelCartaoCheque panelCartaoCheque;

	private JComboBox<Emitente> cboxEmitente;
	private JComboBox<?> cboxCartaoCheque;

	private JButton btnAdicionarCartaoCheque;
	private JButton btnAdicionarEmitente;
	
	private JLabel lblCartaoCheque;
	private JLabel lblEmitente;
	private JLabel l6;
	private JLabel l7;
	
	private JPanel contentPanel;
	
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
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][][]", "[][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblOrdemServico = new JLabel("Número Ordem de Serviço");
		contentPanel.add(lblOrdemServico, "cell 0 0,alignx trailing");
		
		JLabel l1 = new JLabel("*");
		l1.setForeground(Color.RED);
		contentPanel.add(l1, "cell 1 0,alignx trailing");
		
		cboxOrdemServico = new JComboBox<OrdemServico>();
		contentPanel.add(cboxOrdemServico, "cell 2 0");
		
		JLabel lblTipoRecebimento = new JLabel("Tipo de Entrada");
		contentPanel.add(lblTipoRecebimento, "cell 0 3");
		
		JLabel l2 = new JLabel("*");
		l2.setForeground(Color.RED);
		contentPanel.add(l2, "cell 1 3,alignx trailing");
		
		cboxTipo = new JComboBox<Recebimento.Tipo>(Recebimento.Tipo.values());
		contentPanel.add(cboxTipo, "cell 2 3");
		
		JLabel lblQuantidadeReceber = new JLabel("Valor à receber");
		contentPanel.add(lblQuantidadeReceber, "cell 0 1");
		
		JLabel l3 = new JLabel("*");
		l3.setForeground(Color.RED);
		contentPanel.add(l3, "cell 1 1,alignx trailing");
		
		txtValorReceber = new DoubleField(0.0);
		txtValorReceber.setColumns(8);
		contentPanel.add(txtValorReceber, "cell 2 1");
		
		JLabel lblQuantidadeTotal = new JLabel("Valor total");
		contentPanel.add(lblQuantidadeTotal, "cell 0 2");
		
		JLabel l5 = new JLabel("*");
		l5.setForeground(Color.RED);
		contentPanel.add(l5, "cell 1 2,alignx trailing");
		
		txtValorTotal = new DoubleField(0.0);
		txtValorTotal.setColumns(8);
		contentPanel.add(txtValorTotal, "cell 2 2");
		
		historicoRecebimentoTableModel = new HistoricoRecebimentoTableModel();

		tabela = new JTable(historicoRecebimentoTableModel);
		tabela.setPreferredScrollableViewportSize(new Dimension(475, 100));
		contentPanel.add(new JScrollPane(tabela), "cell 0 8 3");
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/confirmar.png")));
		buttonPanel.add(btnSalvar);
		getRootPane().setDefaultButton(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(DialogRecebimento.class.getResource("/icones/cancelar.png")));
		buttonPanel.add(btnCancelar);
		
		panelEmitente = new PanelEmitente();
		panelEmitente.setBorder(new TitledBorder(null, "Dados do Emitente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEmitente.setEditable(false);
		contentPanel.add(panelEmitente, "cell 0 6 3 1,alignx left");
		
		panelCartaoCheque = new PanelCartaoCheque();
		panelCartaoCheque.setBorder(new TitledBorder(null, "Dados do Documento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCartaoCheque.setEditable(false);
		contentPanel.add(panelCartaoCheque, "cell 0 7 3 1,alignx left");
		
		lblEmitente = new JLabel("Nome do Emitente");
		contentPanel.add(lblEmitente, "cell 0 4,alignx trailing");
		
		l6 = new JLabel("*");
		l6.setForeground(Color.RED);
		contentPanel.add(l6, "cell 1 4,alignx trailing");
		
		cboxEmitente = new JComboBox<Emitente>();
		contentPanel.add(cboxEmitente, "cell 2 4");
		
		btnAdicionarEmitente = new JButton();
		btnAdicionarEmitente.setIcon(new ImageIcon(PanelConsultar.class.getResource("/icones/adicionar.png")));
		contentPanel.add(btnAdicionarEmitente, "cell 2 4 3");
		
		lblCartaoCheque = new JLabel();
		contentPanel.add(lblCartaoCheque, "cell 0 5");
		
		l7 = new JLabel("*");
		l7.setForeground(Color.RED);
		contentPanel.add(l7, "cell 1 5,alignx trailing");
		
		cboxCartaoCheque = new JComboBox<>();
		contentPanel.add(cboxCartaoCheque, "cell 2 5");
		
		btnAdicionarCartaoCheque = new JButton();
		btnAdicionarCartaoCheque.setIcon(new ImageIcon(PanelConsultar.class.getResource("/icones/adicionar.png")));
		contentPanel.add(btnAdicionarCartaoCheque, "cell 2 5 3");
		
	}
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JComboBox<OrdemServico> getCboxOrdemServico() {
		return cboxOrdemServico;
	}

	public void setCboxOrdemServico(JComboBox<OrdemServico> cboxOrdemServico) {
		this.cboxOrdemServico = cboxOrdemServico;
	}

	public JComboBox<Recebimento.Tipo> getCboxTipo() {
		return cboxTipo;
	}

	public void setCboxTipo(JComboBox<Recebimento.Tipo> cboxTipo) {
		this.cboxTipo = cboxTipo;
	}

	public DoubleField getTxtValorReceber() {
		return txtValorReceber;
	}

	public void setTxtValorReceber(DoubleField txtValorReceber) {
		this.txtValorReceber = txtValorReceber;
	}

	public DoubleField getTxtValorTotal() {
		return txtValorTotal;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public void setTxtValorTotal(DoubleField txtValorTotal) {
		this.txtValorTotal = txtValorTotal;
	}

	public HistoricoRecebimentoTableModel getHistoricoRecebimentoTableModel() {
		return historicoRecebimentoTableModel;
	}

	public void setHistoricoRecebimentoTableModel(
			HistoricoRecebimentoTableModel historicoRecebimentoTableModel) {
		this.historicoRecebimentoTableModel = historicoRecebimentoTableModel;
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public void setBtnSalvar(JButton btnSalvar) {
		this.btnSalvar = btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public PanelCartaoCheque getPanelCartaoCheque() {
		return panelCartaoCheque;
	}

	public void setPanelCartaoCheque(PanelCartaoCheque panelCartaoCheque) {
		this.panelCartaoCheque = panelCartaoCheque;
	}

	public PanelEmitente getPanelEmitente() {
		return panelEmitente;
	}

	public void setPanelEmitente(PanelEmitente panelEmitente) {
		this.panelEmitente = panelEmitente;
	}

	public JComboBox<Emitente> getCboxEmitente() {
		return cboxEmitente;
	}

	public void setCboxEmitente(JComboBox<Emitente> cboxEmitente) {
		this.cboxEmitente = cboxEmitente;
	}

	public JComboBox<?> getCboxCartaoCheque() {
		return cboxCartaoCheque;
	}

	public void setCboxCartaoCheque(JComboBox<?> cboxCartaoCheque) {
		this.cboxCartaoCheque = cboxCartaoCheque;
	}
	
	public JButton getBtnAdicionarCartaoCheque() {
		return btnAdicionarCartaoCheque;
	}

	public void setBtnAdicionarCartaoCheque(JButton btnAdicionarCartaoCheque) {
		this.btnAdicionarCartaoCheque = btnAdicionarCartaoCheque;
	}

	public JButton getBtnAdicionarEmitente() {
		return btnAdicionarEmitente;
	}

	public void setBtnAdicionarEmitente(JButton btnAdicionarEmitente) {
		this.btnAdicionarEmitente = btnAdicionarEmitente;
	}

	public JLabel getLblCartaoCheque() {
		return lblCartaoCheque;
	}

	public void setLblCartaoCheque(JLabel lblCartaoCheque) {
		this.lblCartaoCheque = lblCartaoCheque;
	}

	public JLabel getLblEmitente() {
		return lblEmitente;
	}

	public void setLblEmitente(JLabel lblEmitente) {
		this.lblEmitente = lblEmitente;
	}

	public JLabel getL6() {
		return l6;
	}

	public void setL6(JLabel l6) {
		this.l6 = l6;
	}

	public JLabel getL7() {
		return l7;
	}

	public void setL7(JLabel l7) {
		this.l7 = l7;
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
