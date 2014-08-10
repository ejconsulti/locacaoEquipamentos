package ejconsulti.locacao.views;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ejconsulti.locacao.models.Notificacao;
import ejconsulti.locacao.models.NotificacaoTableModel;
import ejconsulti.locacao.models.Prioridade;
import ejconsulti.locacao.models.PrioridadeCellRenderer;
import javax.swing.ImageIcon;

public class BoxNotificacao extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private NotificacaoTableModel model;
	private JTable table;

	public BoxNotificacao(String titulo, String iconPath) {
		setMaximumSize(new Dimension(400, 200));
		setFrameIcon(new ImageIcon(BoxNotificacao.class.getResource(iconPath)));
		setClosable(true);
		setTitle(titulo);
		setSize(new Dimension(400, 200));
		setIconifiable(true);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setDefaultRenderer(Prioridade.class, new PrioridadeCellRenderer());
		scrollPane.setViewportView(table);
		
		model = new NotificacaoTableModel();
		table.setModel(model);
	}
	
	public void addNotificacao(Notificacao notificacao) {
		model.add(notificacao);
	}
	
	public JTable getTable() {
		return table;
	}
	
	public NotificacaoTableModel getModel() {
		return model;
	}
}
