package ejconsulti.locacao.assets;

import eso.database.SQLiteDatabase;

/**
 * Data Access Object (DAO)
 * 
 * gerencia as conexões com o(s) banco(s) de dados
 * 
 * @author Edison Jr
 *
 */
public class DAO {

	private static SQLiteDatabase database;
	
	// Abre o banco de dados sqlite localizado em: 'data/database.db'
	public static SQLiteDatabase getDatabase() {
		if(database == null)
			database = new SQLiteDatabase("data/database.db");
		return database;
	}
	
}
