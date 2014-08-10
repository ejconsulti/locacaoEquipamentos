package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela de cliente
 * 
 * @author Edison Jr
 *
 */
public class NotificacaoTableModel extends TableModel<Notificacao> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOTIFICACAO = new TableColumn("Notificação", String.class, false);
	public static final TableColumn PRIORIDADE = new TableColumn("", Prioridade.class, false);
    
    public NotificacaoTableModel() {
    	super(NOTIFICACAO, PRIORIDADE); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Notificacao o = getRows().get(rowIndex);
		
		if(columnIndex == NOTIFICACAO.getIndex())
        	return o.toString();
		if(columnIndex == PRIORIDADE.getIndex())
			return o.getPrioridade();
		
        throw new IndexOutOfBoundsException();
	}

}
