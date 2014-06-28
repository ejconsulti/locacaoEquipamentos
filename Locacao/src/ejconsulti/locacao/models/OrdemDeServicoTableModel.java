package ejconsulti.locacao.models;

import java.sql.Date;

import ejconsulti.locacao.models.OrdemDeServico.Status;
import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela da ordem de servi�o
 * 
 * @author �rico Jr
 *
 */
public class OrdemDeServicoTableModel extends TableModel<OrdemDeServico> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME_CLIENTE = new TableColumn("Cliente", String.class, false);
    public static final TableColumn DATA = new TableColumn("Data", Date.class, false);
    public static final TableColumn VALOR_TOTAL = new TableColumn("Valor Total", String.class, false);
    public static final TableColumn STATUS = new TableColumn("Status", Status.class, true);
    
    public OrdemDeServicoTableModel() {
    	super(NOME_CLIENTE, DATA, VALOR_TOTAL, STATUS); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrdemDeServico o = getRows().get(rowIndex);
		
		if(columnIndex == NOME_CLIENTE.getIndex()) {
			return o.getNomeCliente();
        } if(columnIndex == DATA.getIndex()) {
        	return o.getData();
        } if(columnIndex == VALOR_TOTAL.getIndex()) {
        	return String.format("%.2f", o.getValor());
        } if(columnIndex == STATUS.getIndex()) {
        	return o.getStatus();
        }
		
        throw new IndexOutOfBoundsException();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		OrdemDeServico o = getRows().get(rowIndex);
		
		 if(columnIndex == STATUS.getIndex()) {
			 o.setStatus((Status) aValue);
		 }
	}

}
