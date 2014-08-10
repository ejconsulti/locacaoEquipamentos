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
    public static final TableColumn VALOR_DIARIO = new TableColumn("Valor diário", Double.class, false);
    public static final TableColumn VALOR_MENSAL = new TableColumn("Valor mensal", Double.class, false);
    public static final TableColumn QTD_LOCADO = new TableColumn("Qtd. Locado", Double.class, false);
    public static final TableColumn QTD_DISPONIVEL = new TableColumn("Qtd. Disponível", Double.class, false);
    public static final TableColumn QTD_TOTAL = new TableColumn("Qtd. Total", Double.class, false);
    
    public ProdutoTableModel() {
    	super(NOME, VALOR_DIARIO, VALOR_MENSAL, QTD_LOCADO, QTD_DISPONIVEL, QTD_TOTAL); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Produto o = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return o.toString();
        } if(columnIndex == VALOR_DIARIO.getIndex()) {
        	return  o.getValorDiario();
        } if(columnIndex == VALOR_MENSAL.getIndex()) {
        	return o.getValorMensal();
        } if(columnIndex == QTD_LOCADO.getIndex()) {
        	return o.getQuantidadeTotal() - o.getQuantidade();
        } if(columnIndex == QTD_DISPONIVEL.getIndex()) {
        	return o.getQuantidade();
        } if(columnIndex == QTD_TOTAL.getIndex()) {
        	return o.getQuantidadeTotal();
        }
		
        throw new IndexOutOfBoundsException();
	}

}
