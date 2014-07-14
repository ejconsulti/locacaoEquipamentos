package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

public class RecebimentoTableModel extends TableModel<Recebimento> {

	private static final long serialVersionUID = 1L;

	public static final TableColumn ID = new TableColumn("Ordem de Serviço", String.class, false);
	public static final TableColumn TIPO = new TableColumn("Tipo da Última Entrada", String.class, false);
	public static final TableColumn QUANTIDADE_PARCIAL = new TableColumn("Quantidade Parcial", String.class, false);
	public static final TableColumn QUANTIDADE_RESTANTE = new TableColumn("Quantidade Restante", String.class, false);
    public static final TableColumn QUANTIDADE_TOTAL = new TableColumn("Quantidade Total", String.class, false);
    public static final TableColumn STATUS = new TableColumn("Status", String.class, false);
    
    public RecebimentoTableModel() {
    	super(ID, TIPO, QUANTIDADE_PARCIAL, QUANTIDADE_RESTANTE, QUANTIDADE_TOTAL, STATUS); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Recebimento r = getRows().get(rowIndex);
		if(columnIndex == ID.getIndex()) {
        	return r.getIdOrdemServico();
        } if (columnIndex == TIPO.getIndex()) {
        	return TipoRecebimento.valueOf(r.getTipo());
        } if(columnIndex == QUANTIDADE_PARCIAL.getIndex()) {
        	return String.format("%.2f", r.getQuantidadeParcial());
        } if(columnIndex == QUANTIDADE_RESTANTE.getIndex()) {
        	return String.format("%.2f", (r.getQuantidadeTotal() - r.getQuantidadeParcial()));
        } if(columnIndex == QUANTIDADE_TOTAL.getIndex()) {
        	return String.format("%.2f", r.getQuantidadeTotal());
        } if(columnIndex == STATUS.getIndex()) {
        	return StatusRecebimento.valueOf(r.getStatus());
        }
		
        throw new IndexOutOfBoundsException();
	}

	
}
