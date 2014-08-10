package ejconsulti.locacao.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class PanelNotificacoes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JDesktopPane desktopPane;

	public PanelNotificacoes() {
		setLayout(new BorderLayout(0, 0));
		
		desktopPane = new JDesktopPane();
		add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	}
	
	public void add(BoxNotificacao box) {
		desktopPane.add(box);
		box.setVisible(true);
	}
	
	public void clear() {
		desktopPane.removeAll();
	}
}
