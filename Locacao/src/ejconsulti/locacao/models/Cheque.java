package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Cheque {
	
	public static final String ID = "idCheque";
	public static final String NOME = "nomeTitularCheque";
	public static final String DATA = "dataVencimentoCheque";
	public static final String CPF_CNPJ = "cpf_cnpj";
	public static final String NUMERO = "numeroCheque";
	public static final String BANCO = "bancoCheque";
	public static final String ID_RECEBIMENTO = "idRecebimento";
	
	private Integer id;
	private String cpfCnpj;
	private String nome;
	private Date dataVencimento;
	private String numero;
	private String banco;
	private Integer idRecebimento;
	
	public Cheque() {}
	
	public Cheque(Integer id,
			String cpfCnpj,
			String nome,
			Date dataVencimento,
			String numero,
			String banco,
			Integer idRecebimento) {
		
		this.id = id;
		this.cpfCnpj = cpfCnpj;
		this.nome = nome;
		this.dataVencimento = dataVencimento;
		this.numero = numero;
		this.banco = banco;
		this.idRecebimento = idRecebimento;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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

	public Integer getIdRecebimento() {
		return idRecebimento;
	}

	public void setIdRecebimento(Integer idRecebimento) {
		this.idRecebimento = idRecebimento;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
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
				               rs.getString(CPF_CNPJ), 
				               rs.getString(NOME),
				               ContentValues.getAsDate(rs.getString(DATA)),
				               rs.getString(NUMERO),
				               rs.getString(BANCO),
				               rs.getInt(ID_RECEBIMENTO));
	}

}
