package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import eso.components.DateField;

public class DialogCaixa extends JDialog {
private static final long serialVersionUID = 1L;

	private DateField txtDataInicio;
	private JLabel lblDataInicio;
	private DateField txtDataFim;
	private JLabel lblDataFim;
	private JButton btnBuscar;
	private JPanel contentPanel;

	public DialogCaixa(Window owner, String title) {
		super(owner, title);
		intialize();
	}
	
	private void intialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[right]2[][]", "[][][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblDataInicio = new JLabel("Data Inicial");
		contentPanel.add(lblDataInicio, "cell 0 0,alignx trailing");
		
		txtDataInicio = new DateField();
		txtDataInicio.setColumns(8);
		contentPanel.add(txtDataInicio, "cell 1 0");
		
		lblDataFim = new JLabel("Data Final");
		contentPanel.add(lblDataFim, "cell 0 1,alignx trailing");
		
		txtDataFim = new DateField();
		txtDataFim.setColumns(8);
		contentPanel.add(txtDataFim, "cell 1 1");
		
		btnBuscar = new JButton("buscar");
		contentPanel.add(btnBuscar, "cell 2 1");

	}

	public DateField getTxtDataInicio() {
		return txtDataInicio;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}
	
	public JLabel getLblDataInicio() {
		return lblDataInicio;
	}
	
	public JPanel getContentPanel() {
		return contentPanel;
	}
	
	public JLabel getLblDataFim() {
		return lblDataFim;
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
