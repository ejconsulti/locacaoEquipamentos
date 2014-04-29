package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Rua (Logadouro)
 * 
 * @author Edison Jr
 *
 */
public class Rua {

	public static final String TABLE = "logadouros";
	public static final String ID = "idLogadouro";
	public static final String ID_BAIRRO = "idBairro";
	public static final String NOME = "nomeLogadouro";
	
	private Integer id;
	private int idBairro;
	private String nome;
	
	public Rua() {}
	
	public Rua(Integer id, int idBairro, String nome) {
		this.id = id;
		this.idBairro = idBairro;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id < 1)
			return;
		this.id = id;
	}
	
	public int getIdBairro() {
		return idBairro;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Rua)
			return ((Rua) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Rua rsToObject(ResultSet rs) throws SQLException {
		return new Rua(rs.getInt(ID), rs.getInt(ID_BAIRRO),
				rs.getString(NOME));
	}
	
}
