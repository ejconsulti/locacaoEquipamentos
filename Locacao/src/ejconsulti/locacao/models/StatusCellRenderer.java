package ejconsulti.locacao.models;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ejconsulti.locacao.models.OrdemDeServico.Status;

public class StatusCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(value instanceof Status) {
			Status s = (Status) value;
			c.setForeground(s.getCor());
		}
		return c;
	}
}
