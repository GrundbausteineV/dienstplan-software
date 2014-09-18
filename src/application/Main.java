package application;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import util.localization.initLanguages;
import util.logging.Log;
import yaml.file.*;
import application.logic.login.createUserLogic;
import application.logic.login.loginLogic;
import application.logic.overview.overviewLogic;
import application.user.User;
import configuration.*;
import database.mysql.MySQL;
import database.sqlite.SQLite;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
* <h1>Dienstplan Software</h1>
* Die Dienstplan Software soll es pädagogischen Einrichtungen
* und kleinen Firmen erleichtern, die regelmäßige Pflege
* von Dienstplänen durch eine geeignete Software zu erleichtern.
* <p>
* <b>Hinweis:</b> Wir sind stets darauf aus, unseren Code zu kommentieren
* und so dritten zu ermöglichen, diesen nachzuvollziehen.
*
* @author  Jan-Eric Dreßler
* @version 0.0.2
* @since   2014-08-14
*/

public class Main extends Application {
	
	/**
	 * Initialisierung der Programm-Konstanten
	 */
	public final String CREDIT_AUTHOR 			= "Jan-Eric Dreßler";
	public final String CREDIT_ORGANIZATION 	= "Gemeinschaft für Medienkompetenz Grundbaustein e.V.";
	public final String CREDIT_VERSION 			= "0.0.2";
	public final String DATABASE_DIRECTORY		= "settings";
	public final String DATABASE_FILE			= "settings.db";
	public final String SAVE_DIRECTORY			= "saves";
	public final String PASSWORD_SALT			= "g867Rzu7657F6tdf7d758F687RfHfr4fgFDhD56ffdZtZr67R7RFGFGUGzuTRDTGUt2TGT";
	
	public final String APPLICATION_TITLE = "Grundbaustein e.V. Dienstplan-Software 2014";
	public final String APPLICATION_ICON = "/resources/icons/application/blue-folder.png";
	
	/**
	 * Spracheinstellungen werden über die gesamte Laufzeit allen Fenstern zugänglich gemacht
	 */
	// TODO Getter und Setter einbauen
	public ResourceBundle resourceBundle = null;

	/**
	 * Die Log-Klasse wird zum Start initialisiert und steht allen Klassen zur Verfügung
	 */
	// TODO Getter und Setter einbauen
	public Log log = null;

	// Configuration Files
	public FileConfiguration config = null;
	public File configFile = null;
	
	// H2 Database class
	public SQLite sqliteDatabase = null;
	// MySQL Database class
	public MySQL mysqlDatabase = null;
	
	// User class
	public User user = null;

	// Import aller Logic-Klassen
	public loginLogic loginLogicC = null;
	public overviewLogic overviewLogicC = null;
	public createUserLogic createUserLogicC = null;

	// Import aller Config-Dateien
	//public appConfig appConfig = null;
	public settings settings = null;
	
	public initLanguages initLanguage;

	FXMLLoader loader = null;
	AnchorPane rootLayout = null;

	private Stage hiddenStage = null;
	private Stage overviewStage = null;
	public Stage primaryStage = null;
	public Stage registerUserStage = null;
	
	private static Main instance;
	
	// TODO Observables in eigene Pseudo-Klasse unterbringen (auslagern)
	public ObservableList<MenuItem> languageList = FXCollections.observableArrayList();
	public ObservableList<String> languageIcon = FXCollections.observableArrayList();	
	public ObservableList<Button> planOverview = FXCollections.observableArrayList();


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Main.instance = this;
		
		this.hiddenStage = new Stage();
		this.hiddenStage.initStyle(StageStyle.TRANSPARENT);
		
		this.primaryStage = primaryStage;

		this.log = new Log();
		this.sqliteDatabase = new SQLite(this);
		//this.mysqlDatabase = new MySQL(this);
		//this.appConfig = new appConfig(this);
		this.initLanguage = new initLanguages(this);
		
		//this.mysqlDatabase.testConnection();
		
		if(initLanguage.initiateLanguages()){
			log.LogInfo("Languages initialized");
		} else {
			log.LogError("error: Languages couldn't be initiated.");
		}
		
		this.settings = new settings(this);

		/*if(appConfig.initiateConfig()){
			log.LogInfo("initialized: config.yml");
		} else {
			log.LogError("error: config.yml couldn't be initiated.");
		}*/

		this.loginLogicC = new loginLogic(this);
		this.createUserLogicC = new createUserLogic(this);


		loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));

		this.resourceBundle = ResourceBundle.getBundle("resources.localisation.local", new Locale(settings.getLanguageLanguage(), settings.getLanguageCountry()));
		loader.setResources(this.resourceBundle);
		languageIcon.add(0, settings.getLanguageCountry());

		try {
			rootLayout = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(rootLayout);

		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle(this.APPLICATION_TITLE);
		//primaryStage.getIcons().add(new Image("/resources/icons/app-icon.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_32.png"));
		//primaryStage.getIcons().add(new Image("/resources/icons/favicon_64.png"));
		this.primaryStage.getIcons().add(new Image(this.APPLICATION_ICON));
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
		return (new File(currentDirectory.toString()));
	}

	public void setAppLanguage(String language, String country) {
		reloadLogin(new Locale(language, country));
		log.LogInfo("Changed language to: " + language);
	}
	
	public void showTooltip(Stage owner, Control control, String tooltipText, ImageView tooltipGraphic)
		{
		    Point2D p = control.localToScene(0.0, 0.0);

		    final Tooltip customTooltip = new Tooltip();
		    customTooltip.setText(tooltipText);

		    control.setTooltip(customTooltip);
		    customTooltip.setAutoHide(true);

		    customTooltip.show(owner, p.getX() + control.getScene().getX() + control.getScene().getWindow().getX(), p.getY() + control.getScene().getY() + control.getScene().getWindow().getY());

		}

	private void reloadLogin(Locale locale) {
		AnchorPane pane = null;
		FXMLLoader fxmlLoader = null;
		fxmlLoader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));
		this.resourceBundle = ResourceBundle.getBundle("resources.localisation.local", locale);
		fxmlLoader.setResources(this.resourceBundle);
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
		this.resourceBundle = ResourceBundle.getBundle("resources.localisation.local", new Locale(settings.getLanguageLanguage(), settings.getLanguageCountry()));
		fxmlLoader.setResources(this.resourceBundle);
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
		this.overviewStage.setTitle(this.APPLICATION_TITLE);
		this.overviewStage.getIcons().add(new Image(this.APPLICATION_ICON));
		this.overviewStage.setScene(scene);
		this.overviewStage.show();
		
	}

	private void shutdownApplication() {
		
		log.LogInfo("Shutting down application...");
		this.hiddenStage.hide();
		log.LogInfo("Shutdown successfull!");
		/*
		this.planOverview.forEach(item -> {
			File inputFile = new File(this.getDataFolder(), "saves" + File.separator + item);
			File encryptedFile = new File(this.getDataFolder(), "saves" + File.separator + item + ".enc");

			if(item.equalsIgnoreCase("Neuer Dienstplan")) {
			} else {
				try {
					FileEncryptor.encrypt("JEDressler", "Juhu", inputFile, encryptedFile);
					try{
			    		if(inputFile.delete()){
			    			System.out.println(inputFile.getName() + " is deleted!");
			    		}else{
			    			System.out.println("Delete operation is failed.");
			    		}				 
			    	}catch(Exception e){				 
			    		e.printStackTrace();				 
			    	}
				} catch (CryptoException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
			
		});
		*/
	}
}
