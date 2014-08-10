package ejconsulti.locacao.models;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ValorCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		((JLabel) c).setText(String.format("%.2f", (Double) value));
		((JLabel) c).setHorizontalAlignment(SwingConstants.TRAILING);
		if((Double) value < 0)
			c.setForeground(Color.RED);
		return c;
	}
}
