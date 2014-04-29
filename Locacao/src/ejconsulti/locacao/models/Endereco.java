package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Endereço
 * 
 * @author Edison Jr
 *
 */
public class Endereco {

	public static final String TABLE = "enderecos";
	public static final String VIEW = "v_enderecos";
	public static final String ID = "idEndereco";
	public static final String ID_RUA = "idLogadouro";
	public static final String NUMERO = "numero";
	public static final String COMPLEMENTO = "complemento";
	public static final String REFERENCIA = "referencia";
	
	private Integer id;
	private int idRua;
	private String numero;
	private String complemento;
	private String referencia;
	
	// Endereço detalhado
	private Rua rua;
	private Bairro bairro;
	private Cidade cidade;
	private Uf uf;
	
	public Endereco() {}
	
	public Endereco(Integer id, int idRua, String numero, String complemento,
			String referencia) {
		this.id = id;
		this.idRua = idRua;
		this.numero = numero;
		this.complemento = complemento;
		this.referencia = referencia;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id > 0)
			this.id = id;
	}
	
	public int getIdRua() {
		return idRua;
	}
	
	public void setIdRua(int idLogadouro) {
		this.idRua = idLogadouro;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	// Endereço detalhado
	
	public Rua getRua() {
		return rua;
	}

	public void setRua(Rua rua) {
		this.rua = rua;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Endereco)
			return ((Endereco) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return numero;
	}
	
	public static Endereco rsToObject(ResultSet rs) throws SQLException {
		return new Endereco(rs.getInt(ID), rs.getInt(ID_RUA), rs.getString(NUMERO),
				rs.getString(COMPLEMENTO), rs.getString(REFERENCIA));
	}
	
}
