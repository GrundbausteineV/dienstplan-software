package application.logic.overview;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import util.encryption.CryptoException;
import util.encryption.FileEncryptor;
import yaml.file.FileConfiguration;
import yaml.file.YamlConfiguration;
import application.Main;

public class overviewLogic {

	private Main app = null;
	private FileConfiguration config = null;
	private File configFile = null;

	public overviewLogic(Main app) {
		this.app = app;
	}
	
	public void initPlanOverview() {
		
		ResourceBundle langBundle = ResourceBundle.getBundle("resources.localisation.local", new Locale(this.app.settings.getLanguageLanguage(), this.app.settings.getLanguageCountry()));
		this.app.planOverview.add(langBundle.getString("key.overview_new_plan"));
		
		File dir = new File(this.app.getDataFolder(), "saves" + File.separator);
		String fileName;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
				}
				else {
					fileName = files[i].getName();
					
					getConfig(fileName);
					this.app.log.LogDebug("FileName:" + fileName);
					
					if(this.config.getBoolean("EncryptionData")) {
						this.app.planOverview.add(fileName);
						this.app.log.LogDebug(fileName + " added to List");
					} else {
						File inputFile = new File(this.app.getDataFolder(), "saves" + File.separator + fileName);
						//File encryptedFile = new File(fileName + ".enc");
						String[] newFile = fileName.split(".yml.enc");
						//this.app.log.LogDebug("Logging:" + fileName);
						File decryptedFile = new File(this.app.getDataFolder(), "saves" + File.separator + newFile[0] + ".yml");
						//this.app.log.LogDebug("Decrypt:" + decryptedFile.toString());

						try {
							//FileEncryptor.encrypt("JEDressler", "Juhu", inputFile, encryptedFile);
							FileEncryptor.decrypt("JEDressler", "Juhu", inputFile, decryptedFile);
						} catch (CryptoException ex) {
							System.out.println(ex.getMessage());
							ex.printStackTrace();
						}
						try{
				    		if(inputFile.delete()){
				    			System.out.println(inputFile.getName() + " is deleted!");
				    		}else{
				    			System.out.println("Delete operation is failed.");
				    		}				 
				    	}catch(Exception e){				 
				    		e.printStackTrace();				 
				    	}

						this.app.log.LogDebug("EncFileName:" + newFile[0] + ".yml");
						getConfig(newFile[0] + ".yml");
						
						if(this.config.getBoolean("EncryptionData")) {
							this.app.planOverview.add(newFile[0] + ".yml");
							this.app.log.LogDebug(newFile[0] + ".yml" + " added to List");
						} else {
							this.app.log.LogDebug("Could not add" + newFile[0] + ".yml" + " to List");
						}
					}
					
					
			       
				}
			}
		}
		
	}

	private void reloadConfig(String fileName) {
		if (this.configFile == null) {	    	
			File dir = new File("saves");
			if(dir.exists() == false) {
				dir.mkdir();
			}
			this.configFile = new File(this.app.getDataFolder(), "saves" + File.separator + fileName);
		}
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	
		
	}

	private FileConfiguration getConfig(String fileName) {
		if (this.config == null) {
			reloadConfig(fileName);
		}
		return this.config;
	}
	
}
