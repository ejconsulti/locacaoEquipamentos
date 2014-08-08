package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Produto da Ordem de Serviço
 * 
 * @author Edison Jr
 *
 */
public class ProdutoOS extends Produto {
	
	public static final String TABLE = "PRODUTOSLOCADOS";
	public static final String VIEW = "V_PRODUTOSLOCADOS";
	public static final String ID_ORDEMSERVICO = "idOrdemServico";
	public static final String DATA_DEVOLUCAO = "DATADEVOLUCAO";
	public static final String QUANTIDADE_LOCADA = "QUANTIDADELOCADA";
	public static final String DIAS = "DIAS";
	public static final String VALOR = "VALOR";
	public static final String LOCADO = "LOCADO"; // 1 = Sim / 0 = Não
	
	private int dias;
	private int locado;
	
	public ProdutoOS() {}

	public ProdutoOS(Integer id, String nome, double valorDiario,
			double valorMensal, int quantidadeTotal, int quantidade, 
			int dias, int locado) {
		super(id, nome, valorDiario, valorMensal, quantidadeTotal, quantidade);
		this.dias = dias;
		this.locado = locado;
	}
	
	public ProdutoOS(Produto p, int quantidade, int dias, int locado) {
		this(p.getId(), p.getNome(), p.getValorDiario(), p.getValorMensal(), 
				p.getQuantidade(), quantidade, dias, locado);
	}
	
	public int getDias() {
		return dias;
	}
	
	public void setDias(int dias) {
		this.dias = dias;
	}
	
	public int getLocado() {
		return locado;
	}
	
	public void setLocado(int locado) {
		this.locado = locado;
	}
	
	public boolean isLocado() {
		return locado == 1;
	}
	
	public double getTotal() {
		int meses = (dias / 30);
		int diasEx = dias - (meses * 30); 
		return getQuantidade() * ((getValorMensal() * meses) + (getValorDiario() * diasEx));
	}
	
	public static ProdutoOS rsToObject(ResultSet rs) throws SQLException {
		return new ProdutoOS(rs.getInt(ID), rs.getString(NOME), rs.getDouble(VALOR_DIARIO),
				rs.getDouble(VALOR_MENSAL), rs.getInt(QUANTIDADE), rs.getInt(QUANTIDADE_LOCADA), 
				rs.getInt(DIAS), rs.getInt(LOCADO));
	}

}
