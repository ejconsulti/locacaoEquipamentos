package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cliente
 * 
 * @author Edison Jr
 *
 */
public class Cliente {
	
	public static final String TABLE = "clientes";
	public static final String ID = "idCliente";
	public static final String NOME = "nomeCliente";
	public static final String RG = "rgCliente";
	public static final String CPF = "cpfCliente";
	public static final String TELEFONE = "telefoneCliente";
	public static final String EMAIL = "emailCliente";
	public static final String ID_ENDERECO = "idEnderecoCliente";
	public static final String ID_ENDERECO_ENTREGA = "idEnderecoEntrega";

	private Integer id;
	private String nome;
	private String rg;
	private String cpf;
	private String telefone;
	private String email;
	private Integer idEndereco;
	private Integer idEnderecoEntrega;
	
	public Cliente() {}
	
	public Cliente(Integer id, String nome, String rg, String cpf, 
			String telefone, String email, Integer idEndereco, Integer idEnderecoEntrega) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.idEndereco = idEndereco;
		this.idEnderecoEntrega = idEnderecoEntrega;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		if(id > 0)
			this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}
	
	public Integer getIdEnderecoEntrega() {
		return idEnderecoEntrega;
	}
	
	public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
		this.idEnderecoEntrega = idEnderecoEntrega;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Cliente)
			return ((Cliente) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Cliente rsToObject(ResultSet rs) throws SQLException {
		return new Cliente(rs.getInt(ID), rs.getString(NOME), rs.getString(RG),
				rs.getString(CPF), rs.getString(TELEFONE), rs.getString(EMAIL),
				rs.getInt(ID_ENDERECO), rs.getInt(ID_ENDERECO_ENTREGA));
	}
	
}
