package ejconsulti.locacao.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import eso.database.ContentValues;

/**
 * Funcionario
 * 
 * @author Edison Jr
 *
 */
public class Funcionario {

	public static final String TABLE = "funcionarios";
	public static final String ID = "idFuncionario";
	public static final String NOME = "nomeFuncionario";
	public static final String RG = "rgFuncionario";
	public static final String CPF = "cpfFuncionario";
	public static final String TELEFONE = "telefoneFuncionario";
	public static final String ID_ENDERECO = "idEnderecoFuncionario";
	public static final String SALARIO = "SALARIO";
	public static final String DATA_ENTRADA = "DATAENTRADA";
	public static final String DIA_PAGAMENTO = "DIAPAGAMENTO";
	public static final String PREVISAO_FERIAS = "PREVISAOFERIAS";
	
	private Integer id;
	private String nome;
	private String rg;
	private String cpf;
	private String telefone;
	private Integer idEndereco;
	private Double salario;
	private Date dataEntrada;
	private int diaPagamento;
	private Date previsaoFerias;
	
	public Funcionario() {}

	public Funcionario(Integer id, String nome, String rg, String cpf,
			String telefone, Integer idEndereco, Double salario,
			Date dataEntrada, int diaPagamento, Date previsaoFerias) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.telefone = telefone;
		this.idEndereco = idEndereco;
		this.salario = salario;
		this.dataEntrada = dataEntrada;
		this.previsaoFerias = previsaoFerias;
		this.diaPagamento = diaPagamento;
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

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public int getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(int diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	
	public Date getPrevisaoFerias() {
		return previsaoFerias;
	}
	
	public void setPrevisaoFerias(Date vencimentoFerias) {
		this.previsaoFerias = vencimentoFerias;
	}
	
	@Override
	public int hashCode() {
		return id != null ? id : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof Funcionario)
			return ((Funcionario) obj).id == id;
		return false;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	public static Funcionario rsToObject(ResultSet rs) throws SQLException {
		return new Funcionario(rs.getInt(ID), rs.getString(NOME), rs.getString(RG),
				rs.getString(CPF), rs.getString(TELEFONE), rs.getInt(ID_ENDERECO), 
				rs.getDouble(SALARIO), ContentValues.getAsDate(rs.getString(DATA_ENTRADA)), 
				rs.getInt(DIA_PAGAMENTO), ContentValues.getAsDate(rs.getString(PREVISAO_FERIAS)));
	}

}
