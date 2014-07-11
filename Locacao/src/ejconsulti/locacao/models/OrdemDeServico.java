package ejconsulti.locacao.models;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

/**
 * Ordem de Servi���o
 * 
 * @author ���rico Jr
 *
 */
public class OrdemDeServico {
	public static final String TABLE = "ordemServico";
	public static final String ID = "idOrdemServico";
	public static final String ID_CLIENTE = "idCliente";
	public static final String ID_ENDERECO_ENTREGA = "idEnderecoEntrega";
	public static final String DATA_ENTREGA = "dataEntrega";
	public static final String TOTAL = "total";
	public static final String STATUS = "status";
	public static final String RECEBIMENTO = "recebimento";
	
	private Integer id;
	private Integer idCliente;
	private Integer idEnderecoEntrega;
	private Date data;
	private Double valor;
	private Status status;
	private Integer recebimento;
	
	private String nomeCliente;
	
	public OrdemDeServico() {}

	public OrdemDeServico(Integer id, Integer idCliente,
			Integer idEnderecoEntrega, Date data,
			Double valor, Status status, Integer recebimento) {
		this.id = id;
		this.idCliente = idCliente;
		this.idEnderecoEntrega = idEnderecoEntrega;
		this.data = data;
		this.valor = valor;
		this.status = status;
		this.recebimento = recebimento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void setStatus(Status status){
		this.status = status;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	public Integer getRecebimento() {
		return recebimento;
	}
	
	public void setRecebimento(Integer recebimento) {
		this.recebimento = recebimento; 
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
	
	@Override
	public String toString() {
		return "" + id;
	}
	
	public static OrdemDeServico rsToObject(ResultSet rs) throws SQLException {
		return new OrdemDeServico(rs.getInt(ID), rs.getInt(ID_CLIENTE), rs.getInt(ID_ENDERECO_ENTREGA)
				, ContentValues.getAsDate(rs.getString(DATA_ENTREGA)), rs.getDouble(TOTAL), Status.valueOf(rs.getInt(STATUS)), rs.getInt(RECEBIMENTO));
	}
	
	/**
	 * Status da Ordem de Servi���o
	 * 
	 * @author Edison Jr
	 *
	 */
	public static enum Status {

		Cancelada (-1, "Cancelada", Color.RED),
		Concluida (0, "Concluída", Color.GREEN),
		EmAndamento (1, "Em Andamento", Color.BLACK),
		PagamentoPendente (2, "Pagamento Pendente", Color.ORANGE);
		
		
		private int id;
		private String descricao;
		private Color cor;
		
		Status(int id, String descricao, Color cor) {
			this.id = id;
			this.descricao = descricao;
			this.cor = cor;
		}
		
		public int getId() {
			return id;
		}
		
		public String getDescricao() {
			return descricao;
		}
		
		public Color getCor() {
			return cor;
		}
		
		@Override
		public String toString() {
			return descricao;
		}
		
		public static Status valueOf(int id) {
			for(Status o : values())
				if(o.id == id)
					return o;
			return null;
		}
	}
}
