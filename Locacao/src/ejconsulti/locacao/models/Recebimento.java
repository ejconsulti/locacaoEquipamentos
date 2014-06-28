package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Recebimento {

	public static final String TABLE = "recebimentos";
	public static final String ID = "idRecebimento";
	public static final String ID_ORDEM_SERVICO = "idOrdemServico";
	public static final String TIPO = "tipoRecebimento";
	public static final String QUANTIDADE_RECEBIMENTO = "quantidadeRecebimento";
	public static final String QUANTIDADE_PARCIAL = "quantidadeParcialRecebimento";
	public static final String QUANTIDADE_TOTAL = "quantidadeTotalRecebimento";
	public static final String STATUS = "statusRecebimento";
	public static final String DATA_RECEBIMENTO = "dataRecebimento";
	
	private Integer id;
	private Integer idOrdemServico;
	private Integer tipo;
	private Double quantidadeRecebimento;
	private Double quantidadeParcial;
	private Double quantidadeTotal;
	private Integer status;
	private Date dataRecebimento;
	
	public Recebimento() {}
	
	public Recebimento(Integer id, 
			           Integer idOrdemServico, 
			           Integer tipo, 
			           Double quantidadeRecebimento,
			           Double quantidadeParcial,
			           Double quantidadeTotal,
			           Integer status,
			           Date dataRecebimento) {
		
		this.id = id;
		this.idOrdemServico = idOrdemServico;
		this.tipo = tipo;
		this.quantidadeRecebimento = quantidadeRecebimento;
		this.quantidadeParcial = quantidadeParcial;
		this.quantidadeTotal = quantidadeTotal;
		this.status = status;
		this.dataRecebimento = dataRecebimento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (id > 0) {
			this.id = id;
		}
	}

	public Integer getIdOrdemServico() {
		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico) {
		this.idOrdemServico = idOrdemServico;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getQuantidadeParcial() {
		return quantidadeParcial;
	}

	public void setQuantidadeParcial(Double quantidadeParcial) {
		this.quantidadeParcial = quantidadeParcial;
	}
	
	public Double getQuantidadeRecebimento() {
		return quantidadeRecebimento;
	}

	public void setQuantidadeRecebimento(Double quantidadeRecebimento) {
		this.quantidadeRecebimento = quantidadeRecebimento;
	}

	public Double getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Double quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
				               rs.getInt(TIPO),
				               rs.getDouble(QUANTIDADE_RECEBIMENTO),
				               rs.getDouble(QUANTIDADE_PARCIAL), 
				               rs.getDouble(QUANTIDADE_TOTAL),
				               rs.getInt(STATUS),
				               ContentValues.getAsDate(rs.getString(DATA_RECEBIMENTO)));
	}

}
