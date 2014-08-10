package ejconsulti.locacao.models;

import java.sql.Date;

import eso.table.TableColumn;
import eso.table.TableModel;

public class DespesaTableModel extends TableModel<Despesa> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME = new TableColumn("Nome", String.class, false);
	public static final TableColumn DESCRICAO = new TableColumn("Descrição", String.class, false);
	public static final TableColumn DATA_PAGAMENTO = new TableColumn("Data Pagamento", Date.class, false);
	public static final TableColumn VALOR = new TableColumn("Valor", Double.class, false);
	public static final TableColumn STATUS = new TableColumn("Status", Despesa.Status.class, false);
	public static final TableColumn TIPO = new TableColumn("Tipo", Despesa.Tipo.class, false);
	
	public DespesaTableModel() {
		super(NOME, DESCRICAO, DATA_PAGAMENTO, VALOR, STATUS, TIPO);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Despesa d = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return d.getNome();
        } if(columnIndex == DESCRICAO.getIndex()) {
        	return d.getDescricao();
        } if(columnIndex == DATA_PAGAMENTO.getIndex()) {
        	return d.getDataPagamento();
        } if(columnIndex == VALOR.getIndex()) {
        	return  d.getValor();
        } if(columnIndex == STATUS.getIndex()) {
        	return d.getStatus();
        } if (columnIndex == TIPO.getIndex()) {
        	return d.getTipo();
        }
		
        throw new IndexOutOfBoundsException();
	}

}
