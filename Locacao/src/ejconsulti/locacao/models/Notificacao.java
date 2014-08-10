package ejconsulti.locacao.models;

public class Notificacao {
	
	private String notificacao;
	private Prioridade prioridade;
	private Object objeto;
	
	public Notificacao(String notificacao, Object objeto, Prioridade prioridade) {
		this.notificacao = notificacao;
		this.objeto = objeto;
		this.prioridade = prioridade;
	}
	
	@Override
	public String toString() {
		return notificacao;
	}
	
	public Prioridade getPrioridade() {
		return prioridade;
	}
	
	public Object getObjeto() {
		return objeto;
	}

}
