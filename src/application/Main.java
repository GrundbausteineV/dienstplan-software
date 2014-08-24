package application;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import util.logging.Log;
import yaml.file.*;
import application.logic.login.loginLogic;
import configuration.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Hello world!
 *
 */

public class Main extends Application {

	// Log initialisieren
	public Log log = null;

	// Configuration Files
	public FileConfiguration config = null;
	public File configFile = null;

	// Import aller Logic-Klassen
	public loginLogic loginLogicC = null;

	// Import aller Config-Dateien
	public appConfig appConfig = null;

	FXMLLoader loader = null;
	AnchorPane rootLayout = null;

	private Stage primaryStage = null;
	
	private static Main instance;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		Main.instance = this;
		this.primaryStage = primaryStage;

		this.log = new Log();

		this.loginLogicC = new loginLogic(this);
		this.appConfig = new appConfig(this);

		if(appConfig.initiateConfig()){
			log.LogInfo("initialized: config.yml");
		} else {
			log.LogError("error: config.yml couldn't be initiated.");
		}


		loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));

		loader.setResources(ResourceBundle.getBundle("resources.localisation.local", new Locale("en", "EN")));

		rootLayout = loader.load();

		Scene scene = new Scene(rootLayout);

		//remove window decoration
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("Grundbaustein e.V. Dienstplan-Software 2014");
		//primaryStage.getIcons().add(new Image("/resources/icons/app-icon.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_32.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_64.png"));
		this.primaryStage.getIcons().add(new Image("/resources/icons/application/blue-folder.png"));
		this.primaryStage.show();

	}

	@Override
	public void stop() {
		shutdownApplication();
	}

	// static method to get instance of view
	public static Main getInstance() {
	        return instance;
	}

	public File getDataFolder() {
		File currentDirectory = new File(new File("").getAbsolutePath());
		//this.controller.setLabel(currentDirectory.toString());
		return (new File(currentDirectory.toString()));
	}

	public void setAppLanguage(String language, String country) {
		reloadLogin(new Locale(language, country));
		log.LogInfo("Changed language to: " + language);
	}

	private void reloadLogin(Locale locale) {
		AnchorPane pane = null;
		FXMLLoader fxmlLoader = null;
		fxmlLoader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));
		fxmlLoader.setResources(ResourceBundle.getBundle("resources.localisation.local", locale));
		try {
			pane = fxmlLoader.load();
		} catch (Exception ex) {
			log.LogError("can't load /resources/fxml/login/login.fxml", ex);
		}

		Scene scene = new Scene(pane);
		this.primaryStage.setScene(scene);

	}

	private void shutdownApplication() {
		log.LogInfo("Shutting down application...");
		log.LogInfo("Shutdown successfull!");
	}
}
