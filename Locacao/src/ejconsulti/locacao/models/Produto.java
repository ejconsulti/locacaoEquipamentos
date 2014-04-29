package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Produto
 * 
 * @author Edison Jr
 *
 */
public class Produto {

	public static final String TABLE = "produtos";
	public static final String ID = "idProduto";
	public static final String NOME = "nomeProduto";
	public static final String VALOR_DIARIO = "valorDiario";
	public static final String VALOR_MENSAL = "valorMensal";
	public static final String QUANTIDADE = "quantidade";
	
	private Integer id;
	private String nome;
	private double valorDiario;
	private double valorMensal;
	private int quantidade;
	
	public Produto() {}

	public Produto(Integer id, String nome, double valorDiario,
			double valorMensal, int quantidade) {
		this.id = id;
		this.nome = nome;
		this.valorDiario = valorDiario;
		this.valorMensal = valorMensal;
		this.quantidade = quantidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValorDiario() {
		return valorDiario;
	}

	public void setValorDiario(double valorDiario) {
		this.valorDiario = valorDiario;
	}

	public double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Produto)
			return ((Produto) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Produto rsToObject(ResultSet rs) throws SQLException {
		return new Produto(rs.getInt(ID), rs.getString(NOME), rs.getDouble(VALOR_DIARIO),
				rs.getDouble(VALOR_MENSAL),rs.getInt(QUANTIDADE));
	}

}
