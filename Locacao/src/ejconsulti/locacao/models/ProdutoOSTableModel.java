package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela de produto da ordem de serviço
 * 
 * @author Érico Jr
 * @author Edison Jr
 *
 */
public class ProdutoOSTableModel extends TableModel<ProdutoOS> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME = new TableColumn("Nome", String.class, false);
    public static final TableColumn VALOR_DIARIO = new TableColumn("Valor diário", String.class, false);
    public static final TableColumn VALOR_MENSAL = new TableColumn("Valor mensal", String.class, false);
    public static final TableColumn QUANTIDADE = new TableColumn("Quantiade", Integer.class, true);
    public static final TableColumn DIAS = new TableColumn("Dias", Integer.class, true);
    public static final TableColumn TOTAL = new TableColumn("Total", String.class, false);
    
    public ProdutoOSTableModel() {
    	super(NOME, VALOR_DIARIO, VALOR_MENSAL, QUANTIDADE, DIAS, TOTAL); //Inicializar colunas
    }
    
    @Override
    public void add(ProdutoOS row) {
    	int index = getRows().indexOf(row);
    	if(index < 0)
    		super.add(row);
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProdutoOS o = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return o.toString();
        } if(columnIndex == VALOR_DIARIO.getIndex()) {
        	return String.format("%.2f", o.getValorDiario());
        } if(columnIndex == VALOR_MENSAL.getIndex()) {
        	return String.format("%.2f", o.getValorMensal());
        } if(columnIndex == QUANTIDADE.getIndex()) {
        	return o.getQuantidade();
        } if(columnIndex == DIAS.getIndex()) {
        	return o.getDias();
        } if(columnIndex == TOTAL.getIndex()) {
        	return String.format("%.2f", o.getTotal());
        }
		
        throw new IndexOutOfBoundsException();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ProdutoOS o = getRows().get(rowIndex);
		
		 if(columnIndex == QUANTIDADE.getIndex()) {
			 int qtd = (int) aValue;
			 if(qtd < 1) {
				 remove(rowIndex);
				 return;
			 }
			 if(qtd <= o.getQuantidadeTotal())
				 o.setQuantidade(qtd);
			 else
				 o.setQuantidade(o.getQuantidadeTotal());
		 } if(columnIndex == DIAS.getIndex()) {
			 int dias = (int) aValue;
			 if(dias < 1) {
				 remove(rowIndex);
				 return;
			 }
			 o.setDias(dias);
		 }
		 
		 fireTableRowsUpdated(rowIndex, rowIndex);
	}

}
