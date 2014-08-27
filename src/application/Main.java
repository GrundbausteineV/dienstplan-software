package application;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import util.localization.initLanguages;
import util.logging.Log;
import yaml.file.*;
import application.logic.login.loginLogic;
import application.logic.overview.overviewLogic;
import configuration.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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
	public overviewLogic overviewLogicC = null;

	// Import aller Config-Dateien
	public appConfig appConfig = null;
	
	public initLanguages initLanguage;

	FXMLLoader loader = null;
	AnchorPane rootLayout = null;

	private Stage hiddenStage = null;
	private Stage overviewStage = null;
	private Stage primaryStage = null;
	
	private static Main instance;
	
	public ObservableList<MenuItem> languageList = FXCollections.observableArrayList();
	public ObservableList<String> languageIcon = FXCollections.observableArrayList();	

	public ObservableList<String> planOverview = FXCollections.observableArrayList();
	
	private String applicationTitle = "Grundbaustein e.V. Dienstplan-Software 2014";
	private String applicationIcon = "/resources/icons/application/blue-folder.png";


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		Main.instance = this;
		
		this.hiddenStage = new Stage();
		this.hiddenStage.initStyle(StageStyle.TRANSPARENT);
		
		this.primaryStage = primaryStage;

		this.log = new Log();
		this.appConfig = new appConfig(this);
		this.initLanguage = new initLanguages(this);
		
		if(initLanguage.initiateLanguages()){
			log.LogInfo("Languages initialized");
		} else {
			log.LogError("error: Languages couldn't be initiated.");
		}

		if(appConfig.initiateConfig()){
			log.LogInfo("initialized: config.yml");
		} else {
			log.LogError("error: config.yml couldn't be initiated.");
		}

		this.loginLogicC = new loginLogic(this);


		loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));

		loader.setResources(ResourceBundle.getBundle("resources.localisation.local", new Locale(config.getString("Settings.Language.language"), config.getString("Settings.Language.country"))));
		languageIcon.add(0, config.getString("Settings.Language.country"));

		rootLayout = loader.load();

		Scene scene = new Scene(rootLayout);

		//remove window decoration
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle(applicationTitle);
		//primaryStage.getIcons().add(new Image("/resources/icons/app-icon.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_32.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_64.png"));
		this.primaryStage.getIcons().add(new Image(applicationIcon));
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
	
	public void loadOverview() {
		
		this.overviewLogicC = new overviewLogic(this);
		this.overviewLogicC.initPlanOverview();
		
		AnchorPane pane = null;
		FXMLLoader fxmlLoader = null;
		fxmlLoader = new FXMLLoader(this.getClass().getResource("/resources/fxml/overview/overview.fxml"));
		fxmlLoader.setResources(ResourceBundle.getBundle("resources.localisation.local", new Locale(config.getString("Settings.Language.language", "de"), config.getString("Settings.Language.country", "DE"))));
		try {
			pane = fxmlLoader.load();
		} catch (Exception ex) {
			log.LogError("can't load /resources/fxml/overview/overview.fxml", ex);
		}

		this.primaryStage.hide();
		Scene scene = new Scene(pane);
		this.primaryStage.hide();
		this.overviewStage = new Stage();
		this.overviewStage.initStyle(StageStyle.DECORATED);
		this.overviewStage.setTitle(applicationTitle);
		this.overviewStage.getIcons().add(new Image(applicationIcon));
		this.overviewStage.setScene(scene);
		this.overviewStage.show();
		
	}

	private void shutdownApplication() {
		log.LogInfo("Shutting down application...");
		log.LogInfo("Shutdown successfull!");
		this.hiddenStage.hide();
	}
}
