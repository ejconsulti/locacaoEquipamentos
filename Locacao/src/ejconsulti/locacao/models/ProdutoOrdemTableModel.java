package ejconsulti.locacao.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;



/**
 * Modelo de tabela de produtos na Ordem de serviço
 * 
 * @author Érico Jr
 *
 */
public class ProdutoOrdemTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	private List<Produto> dados;
	private List<Integer> quantidade;
	private List<Integer> dias;
	private String [] colunas = {"Nome", "Valor Diário", "Valor Mensal", "Quantidade", "Dias", "Total"};
	
	public ProdutoOrdemTableModel() {
    	dados = new ArrayList<Produto>();
    	quantidade = new ArrayList<Integer>();
    	dias = new ArrayList<Integer>();
    }
    
	
	public void addRow (Produto p, int quantidade, int dias){
		if (!dados.contains(p)){//Caso o produto ainda não tenha sido adicionado
			this.dados.add(p);
			this.quantidade.add(quantidade);
			this.dias.add(dias);
		}
		else{//se não, aumenta a quantidade deste na tabela
			if (this.quantidade.get(dados.indexOf(p)) < dados.get(dados.indexOf(p)).getQuantidade())//se a quantidade não ultrapassar o numero em estoque
				this.quantidade.set(this.dados.indexOf(p), this.quantidade.get(this.dados.indexOf(p)) + 1);
		}
		
		this.fireTableDataChanged();
		
	}
	
	public void removeRow(int linha){
	    this.dados.remove(linha);
	    this.quantidade.remove(linha);
	    this.dias.remove(linha);
	    this.fireTableRowsDeleted(linha, linha);
	}
	
	public Produto get(int linha){
	    return this.dados.get(linha);
	}
	
	public boolean isCellEditable(int linha, int coluna) {
		if (coluna == 3 || coluna == 4 )
			return true;
		
		return false;
	}
	
	public String getColumnName(int num){
		return this.colunas[num];
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		switch (coluna) {
		case 0:
			return dados.get(linha).getNome();
		case 1:
			return dados.get(linha).getValorDiario();
		case 2:
			return dados.get(linha).getValorMensal();
		case 3:
			if (quantidade.get(linha) >= dados.get(linha).getQuantidade())
				quantidade.set(linha, dados.get(linha).getQuantidade());
			return quantidade.get(linha);
		case 4:
			return dias.get(linha);
		case 5:
			if (dias.get(linha) < 30)
				return (quantidade.get(linha)*dados.get(linha).getValorDiario())*dias.get(linha);
			else{
				int d = dias.get(linha)%30;
				
				if (d == 0)
					return dados.get(linha).getValorMensal()*(dias.get(linha)/30)*quantidade.get(linha);
				else
					return (dados.get(linha).getValorMensal()*(dias.get(linha)/30) + (dados.get(linha).getValorDiario()*d))*quantidade.get(linha);
			}
		default:
			break;
		}
        throw new IndexOutOfBoundsException();
	}
	
	public void setValueAt(Object valor, int linha, int coluna){
        if( valor == null) return;
         
        switch(coluna){
            case 0:  dados.get(linha).setNome( (String) valor);break;
            case 1:  dados.get(linha).setValorDiario(Double.parseDouble(valor.toString()));break;
            case 2:  dados.get(linha).setValorMensal(Double.parseDouble(valor.toString()));break;
            case 3:
            	if (valor.toString().matches("^[0-9]*$")){ //se tiver apenas números
            		if (Integer.parseInt(valor.toString()) >= dados.get(linha).getQuantidade())//se a quantidade for maior que a disponível em estoque
            			quantidade.set(linha, dados.get(linha).getQuantidade());
            		else
            			quantidade.set(linha, Integer.parseInt(valor.toString()));
            	}
            	break;
            case 4:  
            	if (valor.toString().matches("^[0-9]*$")) //se tiver apenas números
            		dias.set(linha, Integer.parseInt(valor.toString()));
            	break;
        }
        this.fireTableRowsUpdated(linha, linha);
    }

}
