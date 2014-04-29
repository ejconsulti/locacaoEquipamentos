package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Bairro
 * 
 * @author Edison Jr
 *
 */
public class Bairro {
	public static final String TABLE = "bairros";
	public static final String ID = "idBairro";
	public static final String ID_CIDADE = "idCidade";
	public static final String NOME = "nomeBairro";
	
	private Integer id;
	private int idCidade;
	private String nome;
	
	public Bairro(Integer id, int idCidade, String nome) {
		this.id = id;
		this.nome = nome;
		this.idCidade = idCidade;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id > 0)
			this.id = id;
	}
	
	public int getIdCidade() {
		return idCidade;
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
		if(obj instanceof Bairro)
			return ((Bairro) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Bairro rsToObject(ResultSet rs) throws SQLException {
		return new Bairro(rs.getInt(ID), rs.getInt(ID_CIDADE),
				rs.getString(NOME));
	}
	
}
