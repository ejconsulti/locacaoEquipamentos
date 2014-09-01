package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Cheque {
	
	public static final String ID = "idCheque";
	public static final String CPF = "cpfCheque";
	public static final String NOME = "nomeCheque";
	public static final String DATA = "dataCheque";
	public static final String DONO = "donoCheque";
	public static final String NUMERO = "numeroCheque";
	public static final String VALOR = "valorCheque";
	public static final String ID_RECEBIMENTO = "idRecebimento";
	
	private Integer idCheque;
	private String cpfCheque;
	private String nomeCheque;
	private Date dataCheque;
	private boolean donoCheque;
	private String numeroCheque;
	private Double valorCheque;
	private Integer recebimento_idRecebimento;
	
	public Cheque() {}
	
	public Cheque(Integer idCheque,
				  String cpfCheque,
				  String nomeCheque,
				  Date dataCheque,
				  boolean donoCheque,
				  String numeroCheque,
				  Double valorCheque,
				  Integer recebimento_idRecebimento) {
		
		this.idCheque = idCheque;
		this.cpfCheque = cpfCheque;
		this.nomeCheque = nomeCheque;
		this.dataCheque = dataCheque;
		this.donoCheque = donoCheque;
		this.numeroCheque = numeroCheque;
		this.valorCheque = valorCheque;
		this.recebimento_idRecebimento = recebimento_idRecebimento;
		
	}

	public Integer getIdCheque() {
		return idCheque;
	}

	public void setIdCheque(Integer idCheque) {
		this.idCheque = idCheque;
	}

	public String getCpfCheque() {
		return cpfCheque;
	}

	public void setCpfCheque(String cpfCheque) {
		this.cpfCheque = cpfCheque;
	}

	public String getNomeCheque() {
		return nomeCheque;
	}

	public void setNomeCheque(String nomeCheque) {
		this.nomeCheque = nomeCheque;
	}

	public Date getDataCheque() {
		return dataCheque;
	}

	public void setDataCheque(Date dataCheque) {
		this.dataCheque = dataCheque;
	}

	public boolean isDonoCheque() {
		return donoCheque;
	}

	public void setDonoCheque(boolean donoCheque) {
		this.donoCheque = donoCheque;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public Integer getRecebimento_idRecebimento() {
		return recebimento_idRecebimento;
	}

	public void setRecebimento_idRecebimento(Integer recebimento_idRecebimento) {
		this.recebimento_idRecebimento = recebimento_idRecebimento;
	}
	
	public Double getValorCheque() {
		return valorCheque;
	}

	public void setValorCheque(Double valorCheque) {
		this.valorCheque = valorCheque;
	}

	@Override
	public int hashCode() {
		return idCheque != null ? idCheque : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Recebimento)
			return ((Cheque) obj).idCheque == idCheque;
		return false;
	}
	
	public static Cheque rsToObject(ResultSet rs) throws SQLException {
		return new Cheque(rs.getInt(ID), 
				               rs.getString(CPF), 
				               rs.getString(NOME),
				               ContentValues.getAsDate(rs.getString(DATA)),
				               rs.getBoolean(DONO),
				               rs.getString(NUMERO),
				               rs.getDouble(VALOR),
				               rs.getInt(ID_RECEBIMENTO));
	}

}
