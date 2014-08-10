
package ejconsulti.locacao.models;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Despesa {
	
	public static final String TABLE = "despesas";
	public static final String ID_DESPESA = "idDespesa";
	public static final String NOME = "nomeDespesa";
	public static final String DESCRICAO = "descricaoDespesa";
	public static final String DATA_PAGAMENTO = "dataPagamentoDespesa";
	public static final String VALOR = "valorDespesa";
	public static final String STATUS = "statusDespesa";
	public static final String TIPO = "tipoDespesa";
	
	private Integer id;
	private String nome;
	private String descricao;
	private Date dataPagamento;
	private Double valor;
	private Status status;
	private Tipo tipo;
	
	public Despesa() {}
	
	public Despesa(Integer id, 
					String nome, 
					String descricao, 
					Date dataPagamento,
					Double valor,
					Status status,
					Tipo tipo) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.status = status;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Despesa)
			return ((Despesa) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Despesa rsToObject(ResultSet rs) throws SQLException {
		return new Despesa(rs.getInt(ID_DESPESA), 
						   rs.getString(NOME), 
						   rs.getString(DESCRICAO), 
						   ContentValues.getAsDate(rs.getString(DATA_PAGAMENTO)), 
						   rs.getDouble(VALOR), 
						   Status.valueOf(rs.getInt(STATUS)),
						   Tipo.valueOf(rs.getInt(TIPO)));
	}
	
	public static enum Status implements StatusColor {

		Pago(1, "Pago", Color.BLACK),
		NaoPago(2, "Não Pago", Color.RED);
		
		private int id;
		private String nome;
		private Color cor;
		
		Status(int id, String nome, Color cor) {
			this.id = id;
			this.nome = nome;
			this.cor = cor;
		}
		
		public int getId() {
			return id;
		}
		
		public Color getCor() {
			return cor;
		}
		
		public String toString() {
			return nome; 
		}
		
		public static Status valueOf(int id) {
			for(Status o : values())
				if(o.getId() == id)
					return o;
			return null;
		}
	}
	
	public enum Tipo {

		Fixa(1, "Fixa"),
		Variavel(2, "Variável");
		
		private int id;
		private String nome;
		
		Tipo(int id, String nome) {
			this.id = id;
			this.nome = nome;
		}
		
		public int getId() {
			return id;
		}
		
		public String toString() {
			return nome; 
		}
		
		public static Tipo valueOf(int id) {
			for(Tipo o : values())
				if(o.getId() == id)
					return o;
			return null;
		}
		
	}
}
