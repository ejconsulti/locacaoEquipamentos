package ejconsulti.locacao.views;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelNotificacoes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable table;

	/**
	 * Create the panel.
	 */
	public PanelNotificacoes() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JLabel lblMensagens = new JLabel("Mensagens");
		panel.add(lblMensagens);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);

	}
}
