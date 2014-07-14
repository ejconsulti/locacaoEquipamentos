package ejconsulti.locacao.models;

import eso.table.TableColumn;
import eso.table.TableModel;

/**
 * Modelo de tabela de cliente
 * 
 * @author Edison Jr
 *
 */
public class UsuarioTableModel extends TableModel<Usuario> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn USUARIO = new TableColumn("Usuário", String.class, false);
    
    public UsuarioTableModel() {
    	super(USUARIO); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Usuario o = getRows().get(rowIndex);
		
		if(columnIndex == USUARIO.getIndex())
        	return o.getNome();
		
        throw new IndexOutOfBoundsException();
	}

}
