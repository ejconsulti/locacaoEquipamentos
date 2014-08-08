package ejconsulti.locacao.models;

import java.awt.Color;

public enum StatusRecebimento {

	Finalizado(0, "Finalizado", Color.RED),
	NaoFinalizado(1, "Não Finalizado", Color.black);
	
	private int id;
	private String nome;
	private Color cor;
	
	StatusRecebimento(int id, String nome, Color cor) {
		this.id = id;
		this.nome = nome;
		this.cor = cor;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return nome; 
	}
	
	public Color getCor() {
		return cor;
	}
	
	public static StatusRecebimento valueOf(int id) {
		for(StatusRecebimento o : values())
			if(o.getId() == id)
				return o;
		return null;
	}
}
