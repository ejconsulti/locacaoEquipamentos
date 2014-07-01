package ejconsulti.locacao.models;

public enum TipoDespesa {

	Nada(0, " - "),
	Fixa(1, "Fixa"),
	Variavel(2, "Variável");
	
	private int id;
	private String nome;
	
	TipoDespesa(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return nome; 
	}
	
	public static TipoDespesa valueOf(int id) {
		for(TipoDespesa o : values())
			if(o.getId() == id)
				return o;
		return null;
	}
	
}
