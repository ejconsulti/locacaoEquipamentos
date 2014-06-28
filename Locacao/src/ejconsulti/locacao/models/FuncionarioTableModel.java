package ejconsulti.locacao.models;

import java.sql.Date;

import eso.table.TableColumn;
import eso.table.TableModel;
import eso.utils.Text;

/**
 * Modelo de tabela de funcionário
 * 
 * @author Edison Jr
 *
 */
public class FuncionarioTableModel extends TableModel<Funcionario> {
	private static final long serialVersionUID = 1L;
	
	public static final TableColumn NOME = new TableColumn("Nome", String.class, false);
	public static final TableColumn RG = new TableColumn("RG", String.class, false);
	public static final TableColumn CPF = new TableColumn("CPF", String.class, false);
    public static final TableColumn TELEFONE = new TableColumn("Telefone", String.class, false);
    public static final TableColumn SALARIO = new TableColumn("Salário", String.class, false);
    public static final TableColumn DATA_ENTRADA = new TableColumn("Data de entrada", Date.class, false);
    public static final TableColumn DIA_PAGAMENTO = new TableColumn("Dia de pagamento", Integer.class, false);
    public static final TableColumn PREVISAO_FERIAS = new TableColumn("Previsão de férias", Date.class, false);
    
    public FuncionarioTableModel() {
    	super(NOME, RG, CPF, TELEFONE, SALARIO, DATA_ENTRADA, 
    			DIA_PAGAMENTO, PREVISAO_FERIAS); //Inicializar colunas
    }
    
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Funcionario o = getRows().get(rowIndex);
		
		if(columnIndex == NOME.getIndex()) {
        	return o.getNome();
        } if(columnIndex == RG.getIndex()) {
        	return o.getRg();
        } if(columnIndex == CPF.getIndex()) {
        	return Text.formatCpf(o.getCpf());
        } if(columnIndex == TELEFONE.getIndex()) {
        	return Text.formatTel(o.getTelefone());
        } if(columnIndex == SALARIO.getIndex()) {
        	return String.format("%.2f", o.getSalario());
        } if(columnIndex == DATA_ENTRADA.getIndex()) {
        	return o.getDataEntrada();
        } if(columnIndex == DIA_PAGAMENTO.getIndex()) {
        	return o.getDiaPagamento();
        } if(columnIndex == PREVISAO_FERIAS.getIndex()) {
        	return o.getPrevisaoFerias();
        }
		
        throw new IndexOutOfBoundsException();
	}

}
