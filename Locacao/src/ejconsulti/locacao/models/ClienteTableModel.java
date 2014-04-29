package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;
import eso.utils.Text;

/**
 * Modelo de tabela de cliente
 * 
 * @author Edison Jr
 *
 */
public class ClienteTableModel extends TableModel<Cliente> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME = new TableColumn("Nome", String.class, false);
	public static final TableColumn RG = new TableColumn("RG", String.class, false);
	public static final TableColumn CPF = new TableColumn("CPF", String.class, false);
    public static final TableColumn TELEFONE = new TableColumn("Telefone", String.class, false);
    public static final TableColumn EMAIL = new TableColumn("Email", String.class, false);
    
    public ClienteTableModel() {
    	super(NOME, RG, CPF, TELEFONE, EMAIL); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente o = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return o.getNome();
        } if(columnIndex == RG.getIndex()) {
        	return o.getRg();
        } if(columnIndex == CPF.getIndex()) {
        	return Text.formatCpf(o.getCpf());
        } if(columnIndex == TELEFONE.getIndex()) {
        	return Text.formatTel(o.getTelefone());
        } if(columnIndex == EMAIL.getIndex()) {
        	return o.getEmail();
        }
		
        throw new IndexOutOfBoundsException();
	}

}
