package ejconsulti.locacao.models;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ejconsulti.locacao.models.OrdemDeServico.Status;

public class StatusCellRenderer implements TableCellRenderer {

	private TableCellRenderer DEFAULT = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = DEFAULT.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(value instanceof Status) {
			Status s = (Status) value;
			c.setForeground(s.getCor());
		}
		return c;
	}
}
