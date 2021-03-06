package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Cheque {
	
	public static final String TABLE = "CHEQUE";
	public static final String ID = "idCheque";
	public static final String NOME = "nomeTitularCheque";
	public static final String DATA = "dataVencimentoCheque";
	public static final String NUMERO = "numeroCheque";
	public static final String BANCO = "bancoCheque";
	public static final String ID_EMITENTE = "idEmitente";
	
	private Integer id;
	private String nome;
	private Date dataVencimento;
	private String numero;
	private String banco;
	private Integer idEmitente;
	
	public Cheque() {}
	
	public Cheque(Integer id,
			String nome,
			Date dataVencimento,
			String numero,
			String banco,
			Integer idEmitente) {
		
		this.id = id;
		this.nome = nome;
		this.dataVencimento = dataVencimento;
		this.numero = numero;
		this.banco = banco;
		this.idEmitente = idEmitente;
		
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

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Integer getIdEmitente() {
		return idEmitente;
	}

	public void setIdEmitente(Integer idEmitente) {
		this.idEmitente = idEmitente;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}
	
	@Override
	public String toString() {
		return banco;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Cheque)
			return ((Cheque) obj).id == id;
		return false;
	}
	
	public static Cheque rsToObject(ResultSet rs) throws SQLException {
		return new Cheque(rs.getInt(ID), 
				               rs.getString(NOME),
				               ContentValues.getAsDate(rs.getString(DATA)),
				               rs.getString(NUMERO),
				               rs.getString(BANCO),
				               rs.getInt(ID_EMITENTE));
	}

}
