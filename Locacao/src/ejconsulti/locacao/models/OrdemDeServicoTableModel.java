package ejconsulti.locacao.models;

import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TableView.TableRow;

import ejconsulti.locacao.assets.DAO;
import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela da ordem de serviço
 * 
 * @author Érico Jr
 *
 */
public class OrdemDeServicoTableModel extends TableModel<OrdemDeServico> implements TableCellRenderer{
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME_CLIENTE = new TableColumn("Cliente", String.class, false);
    public static final TableColumn DATA = new TableColumn("Data", String.class, false);
    public static final TableColumn VALOR_TOTAL = new TableColumn("Valor Total", String.class, false);
    public static final TableColumn STATUS = new TableColumn("Status", String.class, false);
    
    private TableCellRenderer tableCellRenderer;
    
    public OrdemDeServicoTableModel() {
    	
    	super(NOME_CLIENTE, DATA, VALOR_TOTAL, STATUS); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrdemDeServico o = getRows().get(rowIndex);
		
		if(columnIndex == NOME_CLIENTE.getIndex()) {
			ResultSet rs = null;
			try {
				rs = DAO.getDatabase().select(null, Cliente.TABLE, Cliente.ID + "=?", new Integer[]{o.getIdCliente()}, null, null);
				if (rs.next())
					return rs.getString(Cliente.NOME);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
			}	
        	
        } if(columnIndex == DATA.getIndex()) {
        	return o.getData();
        } if(columnIndex == VALOR_TOTAL.getIndex()) {
        	return ("R$" + o.getValor().toString());
        } if(columnIndex == STATUS.getIndex()) {
        	return (o.getStatus());
        }
		
        throw new IndexOutOfBoundsException();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		Component c = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (c!=null){
			c.setBackground(Color.red);
		}
		else
			System.out.println("null");
		return null;
	}
	
	public TableCellRenderer getTableRederer(){
		return tableCellRenderer;
	}

}
