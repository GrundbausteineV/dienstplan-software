package configuration;

import java.io.File;
import java.io.IOException;

import yaml.file.*;
import application.Main;

public class appConfig {

	public Main app = null;

	public appConfig(Main app) {
		this.app = app;
	}

	public void reloadConfig() {
		if (app.configFile == null) {	    	
			File dir = new File("configs");
			if(dir.exists() == false) {
				dir.mkdir();
			}
			app.configFile = new File("configs/config.yml");
			if(app.configFile.exists() == false) {
				try {
					app.configFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					app.LogError("Couldn't create new config in 'configs/config.yml'", e);
				}
			}			
		}
		app.config = YamlConfiguration.loadConfiguration(app.configFile);
	
		
	}

	public FileConfiguration getConfig() {
		if (app.config == null) {
			reloadConfig();
		}
		return app.config;
	}

	public void saveConfig() {
		if (app.config == null || app.configFile == null) {
			return;
		}
		try {
			app.config.save(app.configFile);
		} catch (IOException ex) {
			app.LogError("Could not save config to " + app.configFile, ex);
		}
	}

	public boolean initiateConfig() {

		reloadConfig();
		getConfig();

		if(app.config.getString("Credit.Version") == null)
        	app.config.set("Credit.Version", "v0.0.1");

		saveConfig();

		return true;

	}

}