package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Usuário
 * 
 * @author Edison Jr
 *
 */
public class Usuario {

	public static final String TABLE = "Usuarios";
	public static final String ID = "idUsuario";
	public static final String NOME = "nomeUsuario";
	public static final String SENHA = "senha";
	
	public static final String ADMIN = "Admin";
	public static final String KEY = "x11R6";
	
	private Integer id;
	private String nome;
	private String senha;
	
	public Usuario() {}

	public Usuario(Integer id, String nome, String senha) {
		this.id = id;
		this.nome = nome;
		this.senha = senha;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Usuario)
			return ((Usuario) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Usuario rsToObject(ResultSet rs) throws SQLException {
		return new Usuario(rs.getInt(ID), rs.getString(NOME), rs.getString(SENHA)); 
	}

}
