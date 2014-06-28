
package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class CaixaDia {
	
	public static final String TABLE = "caixadia";
	public static final String ID_CAIXADIA = "idCaixaDia";
	public static final String ENTRADA_SAIDA = "entradaOuSaida";
	public static final String TIPO_ENTRADA_SAIDA = "tipoEntradaOuSaida";
	public static final String VALOR = "valor";
	public static final String DATA = "data";
	
	private Integer id;
	private String entradaOuSaida;
	private String tipoEntradaOuSaida;
	private Double valor;
	private Date data;
	
	public CaixaDia() {}
	
	public CaixaDia(Integer id, 
					String entradaOuSaida, 
					String tipoEntradaOuSaida,
					Double valor,
					Date data) {
		this.id = id;
		this.entradaOuSaida = entradaOuSaida;
		this.tipoEntradaOuSaida = tipoEntradaOuSaida;
		this.valor = valor;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public String getEntradaOuSaida() {
		return entradaOuSaida;
	}

	public void setEntradaOuSaida(String entradaOuSaida) {
		this.entradaOuSaida = entradaOuSaida;
	}

	public String getTipoEntradaOuSaida() {
		return tipoEntradaOuSaida;
	}

	public void setTipoEntradaOuSaida(String tipoEntradaOuSaida) {
		this.tipoEntradaOuSaida = tipoEntradaOuSaida;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof CaixaDia)
			return ((CaixaDia) obj).id == id;
		return false;
	}
	
	public static CaixaDia rsToObject(ResultSet rs) throws SQLException {
		return new CaixaDia(rs.getInt(ID_CAIXADIA), 
						   rs.getString(ENTRADA_SAIDA), 
						   rs.getString(TIPO_ENTRADA_SAIDA),
						   rs.getDouble(VALOR),
						   ContentValues.getAsDate(rs.getString(DATA)));
	}
	
}
