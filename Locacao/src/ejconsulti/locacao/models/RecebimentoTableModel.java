package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

public class RecebimentoTableModel extends TableModel<Recebimento> {

	private static final long serialVersionUID = 1L;

	public static final TableColumn ID = new TableColumn("Ordem de Serviço", String.class, false);
	public static final TableColumn TIPO = new TableColumn("Tipo da Última Entrada", Recebimento.Tipo.class, false);
	public static final TableColumn VALOR_PARCIAL = new TableColumn("Vlr. Parcial", Double.class, false);
	public static final TableColumn VALOR_RESTANTE = new TableColumn("Vlr. Restante", Double.class, false);
    public static final TableColumn VALOR_TOTAL = new TableColumn("Vlr. Total", Double.class, false);
    public static final TableColumn STATUS = new TableColumn("Status", Recebimento.Status.class, false);
    
    public RecebimentoTableModel() {
    	super(ID, TIPO, VALOR_PARCIAL, VALOR_RESTANTE, VALOR_TOTAL, STATUS); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Recebimento r = getRows().get(rowIndex);
		if(columnIndex == ID.getIndex()) {
        	return r.getIdOrdemServico();
        } if (columnIndex == TIPO.getIndex()) {
        	return r.getTipo();
        } if(columnIndex == VALOR_PARCIAL.getIndex()) {
        	return r.getValorParcial();
        } if(columnIndex == VALOR_RESTANTE.getIndex()) {
        	return (r.getValorTotal() - r.getValorParcial());
        } if(columnIndex == VALOR_TOTAL.getIndex()) {
        	return r.getValorTotal();
        } if(columnIndex == STATUS.getIndex()) {
        	return r.getStatus();
        }
		
        throw new IndexOutOfBoundsException();
	}

	
}
