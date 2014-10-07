package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

public class Cartao {

	public static final String TABLE = "CARTAO";
	public static final String ID_CARTAO = "idCartao";
	public static final String NOME_CARTAO = "nomeTitularCartao";
	public static final String NUMERO_CARTAO = "numeroCartao";
	public static final String BANDEIRA_CARTAO = "bandeiraCartao";
	public static final String DATA_VENCIMENTO_CARTAO = "dataVencimentoCartao";
	public static final String ID_EMITENTE = "idEmitente";
	
	private Integer id;
	private String nome;
	private String numero;
	private String bandeira;
	private Date dataVencimento;
	private Integer idEmitente;
	
	public Cartao() {}
	
	public Cartao(Integer id, String nome, String numero, String bandeira, Date dataVencimento, Integer idEmitente) {
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.bandeira = bandeira;
		this.dataVencimento = dataVencimento;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Integer getIdEmitente() {
		return idEmitente;
	}

	public void setIdEmitente(Integer idEmitente) {
		this.idEmitente = idEmitente;
	}
	
	@Override
	public String toString() {
		return bandeira;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Cartao)
			return ((Cartao) obj).id == id;
		return false;
	}
	
	public static Cartao rsToObject(ResultSet rs) throws SQLException {
		return new Cartao(rs.getInt(ID_CARTAO), 
						 rs.getString(NOME_CARTAO),
						 rs.getString(NUMERO_CARTAO),
						 rs.getString(BANDEIRA_CARTAO),
						 ContentValues.getAsDate(rs.getString(DATA_VENCIMENTO_CARTAO)), 
						 rs.getInt(ID_EMITENTE));
	}
	
}
