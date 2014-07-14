package ejconsulti.locacao.models;

public enum StatusDespesa {

	Nada(0, ""),
	Pago(1, "Pago"),
	NaoPago(2, "Não Pago");
	
	private int id;
	private String nome;
	
	StatusDespesa(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return nome; 
	}
	
	public static StatusDespesa valueOf(int id) {
		for(StatusDespesa o : values())
			if(o.getId() == id)
				return o;
		return null;
	}
}
