
package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Caixa {
	
	public static final String TABLE = "caixa";
	public static final String ID_CAIXA = "idCaixa";
	public static final String ID_ENTRADA = "idEntrada";
	public static final String DATA = "data";
	public static final String TIPO_ENTRADA = "tipoEntrada";
	public static final String VALOR_ENTRADA = "valorEntrada";
	public static final String ID_SAIDA = "idSaida";
	public static final String TIPO_SAIDA = "tipoSaida";
	public static final String VALOR_SAIDA = "valorSaida";
	
	private Integer id;
	private Date data;
	private Integer tipoEntrada;
	private Double valorEntrada;
	private String tipoSaida;
	private Double valorSaida;
	private Integer idEntrada;
	private Integer idSaida;
	
	public Caixa() {}
	
	public Caixa(Integer id, 
			Date data, 
			Integer tipoEntrada, 
			Double valorEntrada,
			String tipoSaida,
			Double valorSaida,
			Integer idEntrada,
			Integer idSaida) {
		this.id = id;
		this.data = data;
		this.tipoEntrada = tipoEntrada;
		this.valorEntrada = valorEntrada;
		this.tipoSaida = tipoSaida;
		this.valorSaida = valorSaida;
		this.idEntrada = idEntrada;
		this.idSaida = idSaida;
	}

	public Integer getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(Integer tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public Double getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(Double valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public String getTipoSaida() {
		return tipoSaida;
	}

	public void setTipoSaida(String tipoSaida) {
		this.tipoSaida = tipoSaida;
	}

	public Double getValorSaida() {
		return valorSaida;
	}

	public void setValorSaida(Double valorSaida) {
		this.valorSaida = valorSaida;
	}
	
	public Integer getIdEntrada() {
		return idEntrada;
	}
	
	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}
	
	public Integer getIdSaida() {
		return idSaida;
	}
	
	public void setIdSaida(Integer idSaida) {
		this.idSaida = idSaida;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Caixa)
			return ((Caixa) obj).id == id;
		return false;
	}
	
	public static Caixa rsToObject(ResultSet rs) throws SQLException {
		return new Caixa(rs.getInt(ID_CAIXA), 
						 ContentValues.getAsDate(rs.getString(DATA)), 
						   rs.getInt(TIPO_ENTRADA), 
						   rs.getDouble(VALOR_ENTRADA), 
						   rs.getString(TIPO_SAIDA),
						   rs.getDouble(VALOR_SAIDA),
						   rs.getInt(ID_ENTRADA),
						   rs.getInt(ID_SAIDA));
	}
	
}
