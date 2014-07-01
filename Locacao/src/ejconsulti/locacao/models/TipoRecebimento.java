package ejconsulti.locacao.models;

public enum TipoRecebimento {

	Nada(0, " - "),
	Credito(1, "Cartão de Crédito"),
	Debito(2, "Cartão de Débito"),
	Cheque(3, "Cheque"),
	Dinheiro(4, "Dinheiro");
	
	private int id;
	private String nome;
	
	TipoRecebimento(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return nome; 
	}
	
	public static TipoRecebimento valueOf(int id) {
		for(TipoRecebimento o : values())
			if(o.getId() == id)
				return o;
		return null;
	}
}
