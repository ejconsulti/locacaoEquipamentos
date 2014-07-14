package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela de produto
 * 
 * @author Edison Jr
 *
 */
public class ProdutoTableModel extends TableModel<Produto> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME = new TableColumn("Nome", String.class, false);
    public static final TableColumn VALOR_DIARIO = new TableColumn("Valor diário", String.class, false);
    public static final TableColumn VALOR_MENSAL = new TableColumn("Valor mensal", String.class, false);
    public static final TableColumn QUANTIDADE = new TableColumn("Quantiade", Integer.class, false);
    
    public ProdutoTableModel() {
    	super(NOME, VALOR_DIARIO, VALOR_MENSAL, QUANTIDADE); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Produto o = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return o.toString();
        } if(columnIndex == VALOR_DIARIO.getIndex()) {
        	return String.format("%.2f", o.getValorDiario());
        } if(columnIndex == VALOR_MENSAL.getIndex()) {
        	return String.format("%.2f", o.getValorMensal());
        } if(columnIndex == QUANTIDADE.getIndex()) {
        	return o.getQuantidade();
        }
		
        throw new IndexOutOfBoundsException();
	}

}
