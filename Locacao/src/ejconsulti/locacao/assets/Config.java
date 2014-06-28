package ejconsulti.locacao.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import eso.utils.Log;

public class Config {
	public static final String TAG = Config.class.getSimpleName();
	
	private static Config instance;

	private File file = new File("config.properties");
	private Properties config;

	private Config() {}

	private Properties getConfig() {
		// Carrega o arquivo de configuração, se existir
		if(config == null && file.exists()) {
			try {
				config = new Properties();
				FileInputStream fis = new FileInputStream( file );
				config.load(fis);
				fis.close();
			} catch (IOException e) {
				Log.e(TAG, "Erro ao carregar configurações", e);
			}
		}
		return config;
	}

	private void storeProperty(String key, String value) throws IOException {
		if(value == null)
			return;
		if(config == null) {
			config = new Properties();
			if(!file.exists())
				file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream( file );
		config.setProperty(key, value);
		config.store(fos, null);
		fos.close();
	}

	private static Config getInstance() {
		if(instance == null)
			instance = new Config();
		return instance;
	}

	public static String getProperty(String key) {
		if(getInstance().getConfig() != null)
			return getInstance().getConfig().getProperty(key);
		return null;
	}

	public static void setProperty(String key, String value) {
		try {
			getInstance().storeProperty(key, value);
		} catch (IOException e) {
			Log.e(TAG, "Erro ao salvar configurações", e);
		}
	}
}
