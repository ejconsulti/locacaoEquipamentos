package ejconsulti.locacao.assets;

import java.io.File;

import eso.database.SQLiteDatabase;
import eso.utils.Text;

/**
 * Data Access Object (DAO)
 * 
 * gerencia as conexões com o(s) banco(s) de dados
 * 
 * @author Edison Jr
 *
 */
public class DAO {
	public static final String DATABASE = "database";
	
	public static final String Default = "data/database.db";

	private static SQLiteDatabase database;
	
	// Abre o banco de dados sqlite localizado em: 'data/database.db'
	public static SQLiteDatabase getDatabase() {
		if(database == null) {
			String databaseName = Config.getProperty(DATABASE);
			if(Text.isEmpty(databaseName))
				databaseName = Default;
			File banco = new File(databaseName);
			if(banco.isFile() || banco.canRead() || banco.canWrite())
				database = new SQLiteDatabase(databaseName);
		}
		return database;
	}
	
	public static void setDatabase(SQLiteDatabase database) {
		DAO.database = database;
	}
	
}
