package ejconsulti.locacao.models;


import java.sql.Date;

import eso.table.TableColumn;
import eso.table.TableModel;

public class HistoricoRecebimentoTableModel extends TableModel<HistoricoRecebimento> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn VALOR = new TableColumn("Valor", String.class, false);
	public static final TableColumn DATA = new TableColumn("Data de Entrada", Date.class, false);
	public static final TableColumn TIPO = new TableColumn("Tipo da Entrada", TipoRecebimento.class, false);
	
	public HistoricoRecebimentoTableModel() {
		super(VALOR, DATA, TIPO);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HistoricoRecebimento h = getRows().get(rowIndex);
		
		if(columnIndex == VALOR.getIndex()) {
        	return String.format("%.2f", (h.getValor()));
        } if(columnIndex == DATA.getIndex()) {
        	return h.getData();
        } if(columnIndex == TIPO.getIndex()) {
        	return TipoRecebimento.valueOf(h.getTipoRecebimento());
        }
        
        throw new IndexOutOfBoundsException();
	}

}
