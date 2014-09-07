package application.logic.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.properties.propertiesFile;
import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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

	public void displayRegisterUserLayer() {
		Stage dialog = new Stage();
		this.app.registerUserStage = dialog;
		dialog.initStyle(StageStyle.UTILITY);
		
		AnchorPane pane = null;
		FXMLLoader fxmlLoader = null;
		fxmlLoader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/createUser.fxml"));
		fxmlLoader.setResources(this.app.resourceBundle);
		try {
			pane = fxmlLoader.load();
		} catch (Exception ex) {
			this.app.log.LogError("can't load /resources/fxml/login/createUser.fxml", ex);
		}
		
		Scene scene = new Scene(pane);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.setTitle(this.app.APPLICATION_TITLE);
		dialog.getIcons().add(new Image(this.app.APPLICATION_ICON));
		dialog.show();
		
		return;
	}
	
	public String generatePasswordHash(String password) {
		String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(this.app.PASSWORD_SALT.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
	}
	
	public boolean checkUsername(TextField textfield, String username, String password) {
		
		this.app.sqliteDatabase.connect("settings", "settings.db");
		
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM User");
		
		try {
			if (!rs.isBeforeFirst() ) {    
				Main.getInstance().showTooltip(Main.getInstance().primaryStage, textfield, Main.getInstance().resourceBundle.getString("key.login_tooltip_error_nouser"), null);
				this.app.sqliteDatabase.disconnect();
				return true;
			} else {
				while(rs.next()) {
					if((rs.getString("Username").equalsIgnoreCase(username)) && (rs.getString("Password").equalsIgnoreCase(generatePasswordHash(password)))) {
						this.app.loadOverview();
						this.app.sqliteDatabase.disconnect();
						return true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.app.sqliteDatabase.disconnect();
		return false;
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
			this.app.settings.setLanguage(languageCode, countryCode, prop.getPropertyValue("key.init_description"));
			app.setAppLanguage(languageCode, countryCode);
		}
	}

}