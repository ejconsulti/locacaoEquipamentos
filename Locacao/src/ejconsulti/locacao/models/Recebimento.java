package ejconsulti.locacao.models;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Recebimento {

	public static final String TABLE = "recebimentos";
	public static final String ID = "idRecebimento";
	public static final String ID_ORDEM_SERVICO = "idOrdemServico";
	public static final String TIPO = "tipoRecebimento";
	public static final String VALOR_RECEBIMENTO = "quantidadeRecebimento";
	public static final String VALOR_PARCIAL = "quantidadeParcialRecebimento";
	public static final String VALOR_TOTAL = "quantidadeTotalRecebimento";
	public static final String STATUS = "statusRecebimento";
	public static final String DATA_RECEBIMENTO = "dataRecebimento";
	
	private Integer id;
	private Integer idOrdemServico;
	private Tipo tipo;
	private Double valorRecebimento;
	private Double valorParcial;
	private Double valorTotal;
	private Status status;
	private Date dataRecebimento;
	
	public Recebimento() {}
	
	public Recebimento(Integer id, 
			           Integer idOrdemServico, 
			           Tipo tipo, 
			           Double valorRecebimento,
			           Double valorParcial,
			           Double valorTotal,
			           Status status,
			           Date dataRecebimento) {
		
		this.id = id;
		this.idOrdemServico = idOrdemServico;
		this.tipo = tipo;
		this.valorRecebimento = valorRecebimento;
		this.valorParcial = valorParcial;
		this.valorTotal = valorTotal;
		this.status = status;
		this.dataRecebimento = dataRecebimento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (id > 0)
			this.id = id;
	}

	public Integer getIdOrdemServico() {
		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico) {
		this.idOrdemServico = idOrdemServico;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Double getValorParcial() {
		return valorParcial;
	}

	public void setValorParcial(Double valorParcial) {
		this.valorParcial = valorParcial;
	}
	
	public Double getQuantidadeRecebimento() {
		return valorRecebimento;
	}

	public void setValorRecebimento(Double valorRecebimento) {
		this.valorRecebimento = valorRecebimento;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setQuantidadeTotal(Double quantidadeTotal) {
		this.valorTotal = quantidadeTotal;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	
	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Recebimento)
			return ((Recebimento) obj).id == id;
		return false;
	}
	
	public static Recebimento rsToObject(ResultSet rs) throws SQLException {
		return new Recebimento(rs.getInt(ID), 
				               rs.getInt(ID_ORDEM_SERVICO), 
				               Tipo.valueOf(rs.getInt(TIPO)),
				               rs.getDouble(VALOR_RECEBIMENTO),
				               rs.getDouble(VALOR_PARCIAL), 
				               rs.getDouble(VALOR_TOTAL),
				               Status.valueOf(rs.getInt(STATUS)),
				               ContentValues.getAsDate(rs.getString(DATA_RECEBIMENTO)));
	}
	
	public enum Status implements StatusColor {

		Finalizado(0, "Finalizado", Color.BLACK),
		NaoFinalizado(1, "Não Finalizado", Color.RED);
		
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
		
		@Override
		public String toString() {
			return nome; 
		}
		
		public Color getCor() {
			return cor;
		}
		
		public static Status valueOf(int id) {
			for(Status o : values())
				if(o.getId() == id)
					return o;
			return null;
		}
	}
	
	public enum Tipo {

		Credito(1, "Cartão de Crédito"),
		Debito(2, "Cartão de Débito"),
		Cheque(3, "Cheque"),
		Dinheiro(4, "Dinheiro");
		
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
