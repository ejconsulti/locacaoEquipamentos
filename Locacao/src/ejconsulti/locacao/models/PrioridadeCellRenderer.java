package ejconsulti.locacao.models;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PrioridadeCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(value instanceof Prioridade) {
			Prioridade p = (Prioridade) value;
			c.setBackground(p.getCor());
			((JLabel) c).setText("");
			((JLabel) c).setToolTipText(p.toString());
		}
		return c;
	}
}
