package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cidade
 * 
 * @author Edison Jr
 *
 */
public class Cidade {
	public static final String TABLE = "cidades";
	public static final String ID = "idCidade";
	public static final String ID_UF = "idUf";
	public static final String NOME = "nomeCidade";
	
	private Integer id;
	private int idUf;
	private String nome;
	
	public Cidade() {}
	
	public Cidade(Integer id, int idUf, String nome) {
		this.id = id;
		this.idUf = idUf;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id > 0)
			this.id = id;
	}
	
	public int getIdUf() {
		return idUf;
	}
	
	public void setIdUf(int idUf) {
		this.idUf = idUf;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Cidade)
			return ((Cidade) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Cidade rsToObject(ResultSet rs) throws SQLException {
		return new Cidade(rs.getInt(ID), rs.getInt(ID_UF),
				rs.getString(NOME));
	}
	
}
