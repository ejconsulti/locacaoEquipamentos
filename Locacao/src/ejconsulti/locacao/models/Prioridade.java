package ejconsulti.locacao.models;

import java.awt.Color;

public enum Prioridade {

	Baixa(0, "Baixa", Color.GREEN),
	Media(1, "Média", Color.YELLOW),
	Alta(2, "Não Pago", Color.RED);
	
	private int id;
	private String nome;
	private Color cor;
	
	Prioridade(int id, String nome, Color cor) {
		this.id = id;
		this.nome = nome;
		this.cor = cor;
	}
	
	public int getId() {
		return id;
	}
	
	public Color getCor() {
		return cor;
	}
	
	public String toString() {
		return nome; 
	}
	
	public static Prioridade valueOf(int id) {
		for(Prioridade o : values())
			if(o.getId() == id)
				return o;
		return null;
	}
}
