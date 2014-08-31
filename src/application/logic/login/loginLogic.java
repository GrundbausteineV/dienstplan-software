package application.logic.login;

import util.properties.propertiesFile;
import application.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginLogic {

	// Reference to the main application
	private Main app;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public loginLogic(Main app) {
		this.app = app;
	}

	public void displayInfoLayer() {
		Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
		dialog.setHeight(400);
		dialog.setWidth(200);
		dialog.setScene(scene);
		dialog.show();
		
		return;
	}
	
	public void setLanguage(String language) {
		if(language.equalsIgnoreCase("Deutsch")) {
			String languageCode = "de";
			String countryCode = "DE";
			//this.app.appConfig.setLanguage(languageCode, countryCode, "Deutsch");
			this.app.settings.setLanguage(languageCode, countryCode, "Deutsch");
			this.app.languageIcon.set(0, countryCode);
			app.setAppLanguage(languageCode, countryCode);
		} else if(language.equalsIgnoreCase("English")){
			String languageCode = "en";
			String countryCode = "GB";
			//this.app.appConfig.setLanguage(languageCode, countryCode, "English");
			this.app.settings.setLanguage(languageCode, countryCode, "English");
			this.app.languageIcon.set(0, countryCode);
			app.setAppLanguage(languageCode, countryCode);
		} else if(language.equalsIgnoreCase("Français")){
			String languageCode = "fr";
			String countryCode = "FR";
			//this.app.appConfig.setLanguage(languageCode, countryCode, "Français");
			this.app.settings.setLanguage(languageCode, countryCode, "Français");
			this.app.languageIcon.set(0, countryCode);
			app.setAppLanguage(languageCode, countryCode);
		}  else {
			propertiesFile prop = new propertiesFile(language + ".properties");
			String languageCode = prop.getPropertyValue("key.init_languagecode");
			String countryCode = prop.getPropertyValue("key.init_country");
			this.app.appConfig.setLanguage(languageCode, countryCode, prop.getPropertyValue("key.init_description"));
			app.setAppLanguage(languageCode, countryCode);
		}
	}

}