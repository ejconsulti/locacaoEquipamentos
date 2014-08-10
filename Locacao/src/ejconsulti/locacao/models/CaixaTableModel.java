package ejconsulti.locacao.models;

import java.sql.Date;

import eso.table.TableColumn;
import eso.table.TableModel;

public class CaixaTableModel extends TableModel<Caixa> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn DATA = new TableColumn("Data", Date.class, false);
	public static final TableColumn TIPO_ENTRADA = new TableColumn("Entrada", Recebimento.Tipo.class, false);
	public static final TableColumn VALOR_ENTRADA = new TableColumn("Vlr. da Entrada", Double.class, false);
	public static final TableColumn TIPO_SAIDA = new TableColumn("Saída", String.class, false);
	public static final TableColumn VALOR_SAIDA = new TableColumn("Vlr. da Saída", Double.class, false);
	
	public CaixaTableModel() {
		super(DATA, TIPO_ENTRADA, VALOR_ENTRADA, TIPO_SAIDA, VALOR_SAIDA);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Caixa c = getRows().get(rowIndex);
		
		if(columnIndex == DATA.getIndex()) {
        	return c.getData();
        } if(columnIndex == TIPO_ENTRADA.getIndex()) {
        	return c.getTipoEntrada();
        } if(columnIndex == VALOR_ENTRADA.getIndex()) {
        	return c.getValorEntrada();
        } if(columnIndex == TIPO_SAIDA.getIndex()) {
        	return c.getTipoSaida();
        } if(columnIndex == VALOR_SAIDA.getIndex()) {
        	return c.getValorSaida();
        }
        throw new IndexOutOfBoundsException();
	}

}
