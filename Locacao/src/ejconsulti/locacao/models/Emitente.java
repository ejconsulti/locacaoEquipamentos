package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Emitente {

	public static final String TABLE = "EMITENTES";
	public static final String ID_EMITENTES = "idEmitentes";
	public static final String NOME_EMITENTES = "nomeEmitentes";
	public static final String TELEFONE_EMITENTES = "telefoneEmitentes";
	public static final String CPF_CNPJ_EMITENTES = "cpf_cnpj";
	
	private Integer id;
	private String nome;
	private String telefone;
	private String cpfCnpj;
	
	public Emitente() {}
	
	public Emitente(Integer id, String nome, String telefone, String cpfCnpj) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.cpfCnpj = cpfCnpj;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Emitente)
			return ((Emitente) obj).id == id;
		return false;
	}
	
	public static Emitente rsToObject(ResultSet rs) throws SQLException {
		return new Emitente(rs.getInt(ID_EMITENTES), 
						 rs.getString(NOME_EMITENTES),
						 rs.getString(TELEFONE_EMITENTES),
						 rs.getString(CPF_CNPJ_EMITENTES));
	}
	
}
