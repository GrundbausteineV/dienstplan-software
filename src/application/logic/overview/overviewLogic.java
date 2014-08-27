package application.logic.overview;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import application.Main;

public class overviewLogic {

	private Main app = null;

	public overviewLogic(Main app) {
		this.app = app;
	}
	
	public void initPlanOverview() {
		
		ResourceBundle langBundle = ResourceBundle.getBundle("resources.localisation.local", new Locale(this.app.config.getString("Settings.Language.language", "de"), this.app.config.getString("Settings.Language.country", "DE")));
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
					this.app.planOverview.add(fileName);
					this.app.log.LogDebug(fileName + " added to List");
				}
			}
		}
		
	}
	
}
