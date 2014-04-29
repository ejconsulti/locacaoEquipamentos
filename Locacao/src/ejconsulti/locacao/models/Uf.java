package ejconsulti.locacao.models;

/**
 * Uf (Estado)
 * 
 * @author Edison Jr
 *
 */
public enum Uf {
	
	Acre (0, "AC", "Acre"),
	Alagoas (1, "AL", "Alagoas"),
	Amapa (2, "AP", "Amap�"),
	Amazonas (3, "AM", "Amazonas"),
	Bahia (4, "BA", "Bahia"),
	Ceara (5, "CE", "Cear�"),
	Distrito_Federal (6, "DF", "Distrito Federal"),
	Espirito_Santo (7, "ES", "Esp�rito Santo"),
	Goias (8, "GO", "Goi�s"),
	Maranhao (9, "MA", "Maranh�o"),
	Mato_Grosso (10, "MT", "Mato Grosso"),
	Mato_Grosso_do_Sul (11, "MS", "Mato Grosso do Sul"),
	Minas_Gerais (12, "MG", "Minas Gerais"),
	Para (13, "PA", "Par�"), 
	Paraiba (14, "PB", "Para�ba"),
	Parana (15, "PR", "Paran�"),
	Pernambuco (16, "PE", "Pernambuco"),
	Piaui (17, "PI", "Piau�"),
	Rio_de_Janeiro (18, "RJ", "Rio de Janeiro"),
	Rio_Grande_do_Norte (19, "RN", "Rio Grande do Norte"),
	Rio_Grande_do_Sul (20, "RS", "Rio Grande do Sul"),
	Rondonia (21, "RO", "Rond�nia"),
	Roraima (22, "RR", "Roraima"),
	Santa_Catarina (23, "SC", "Santa Catarina"),
	Sao_Paulo (24, "SP", "S�o Paulo"),
	Sergipe (25, "SE", "Sergipe"),
	Tocantins (26, "TO", "Tocantins");
	
	public static final String ID = "idUf";
	
	private int id;
	private String uf;
	private String nome;
	
	Uf(int id, String uf, String estado) {
		this.id = id;
		this.uf = uf;
		this.nome = estado;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return uf;
	}
	
	public static Uf valueOf(int id) {
		for(Uf uf : values())
			if(uf.id == id)
				return uf;
		return null;
	}
	
}
