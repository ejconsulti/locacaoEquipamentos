package ejconsulti.locacao.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ordem de Serviço
 * 
 * @author Érico Jr
 *
 */

public class OrdemDeServico {
	public static final String TABLE = "ordemServico";
	public static final String ID = "idOrdemServico";
	public static final String ID_CLIENTE = "idCliente";
	public static final String ID_ENDERECO_ENTREGA = "idEnderecoEntrega";
	public static final String DATA = "data";//data da realizacao da ordem de servico
	public static final String TOTAL = "total";
	public static final String STATUS = "status";
	
	private Integer id;
	private Integer idCliente;
	private Integer idEnderecoEntrega;
	private String data;
	private Double valor;
	private String status;
	
	public OrdemDeServico() {}

	public OrdemDeServico(Integer id, Integer idCliente,
			Integer idEnderecoEntrega, String data,
			Double valor, String status) {
		this.id = id;
		this.idCliente = idCliente;
		this.idEnderecoEntrega = idEnderecoEntrega;
		this.data = data;
		this.valor = valor;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (id > 0)
			this.id = id;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdEnderecoEntrega() {
		return idEnderecoEntrega;
	}

	public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
		this.idEnderecoEntrega = idEnderecoEntrega;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public int hashCode() {
		return id != null ? id : -1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof OrdemDeServico)
			return ((OrdemDeServico) obj).id == id;
		return false;
	}
	
	public static OrdemDeServico rsToObject(ResultSet rs) throws SQLException {
		return new OrdemDeServico(rs.getInt(ID), rs.getInt(ID_CLIENTE), rs.getInt(ID_ENDERECO_ENTREGA)
				, rs.getString(DATA), rs.getDouble(TOTAL), rs.getString(STATUS));
	}
}
