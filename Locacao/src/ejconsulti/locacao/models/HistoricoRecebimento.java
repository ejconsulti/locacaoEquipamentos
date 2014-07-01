package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class HistoricoRecebimento {
	
	public static final String TABLE = "HISTORICORECEBIMENTO";
	public static final String ID_HISTORICO_RECEBIMENTO = "idHistoricoRecebimento";
	public static final String ID_RECEBIMENTO = "idRecebimento";
	public static final String VALOR = "valorRecebimento";
	public static final String DATA = "dataRecebimento";
	public static final String TIPO_RECEBIMENTO = "tipoRecebimento";
	
	private Integer idHistorico;
	private Integer idRecebimento;
	private Double valor;
	private Date data;
	private Integer tipoRecebimento;
	
	public HistoricoRecebimento() {}
	
	public HistoricoRecebimento(Integer idHistorico, 
								Integer idRecebimento, 
								Double valor, 
								Date data,
								Integer tipoRecebimento) {
		this.idHistorico = idHistorico;
		this.idRecebimento = idRecebimento;
		this.valor = valor;
		this.data = data;
		this.tipoRecebimento = tipoRecebimento;
	}

	public Integer getIdHistorico() {
		return idHistorico;
	}

	public Integer getIdRecebimento() {
		return idRecebimento;
	}

	public void setIdRecebimento(Integer idRecebimento) {
		this.idRecebimento = idRecebimento;
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
	
	public Integer getTipoRecebimento() {
		return tipoRecebimento;
	}
	
	public void setTipoRecebimento(Integer tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}

	@Override
	public int hashCode() {
		return idHistorico != null ? idHistorico : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof HistoricoRecebimento)
			return ((HistoricoRecebimento) obj).idHistorico == idHistorico;
		return false;
	}
	
	public static HistoricoRecebimento rsToObject(ResultSet rs) throws SQLException {
		return new HistoricoRecebimento(rs.getInt(ID_HISTORICO_RECEBIMENTO), 
						   				rs.getInt(ID_RECEBIMENTO), 
						   				rs.getDouble(VALOR), 
						   				ContentValues.getAsDate(rs.getString(DATA)),
						   				rs.getInt(TIPO_RECEBIMENTO));
	}
	
}
