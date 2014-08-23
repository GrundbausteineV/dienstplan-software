package application;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import util.logging.customHTMLLayout;
import yaml.file.*;
import application.controller.login.loginController;
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

	// Log-File
	private Logger logger = Logger.getLogger("(GBSeV) Dienstplan Software");

	// Configuration Files
	public FileConfiguration config = null;
	public File configFile = null;

	// Import aller Logic-Klassen
	public loginLogic loginLogicC = null;

	// Import aller Controller-Klassen
	public loginController controller = null;

	// Import aller Config-Dateien
	public appConfig appConfig = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		loginLogicC = new loginLogic(this);
		appConfig = new appConfig(this);

		initiateLogging();

		if(appConfig.initiateConfig()){
			logger.info("initialized: config.yml");
		} else {
			logger.error("error: config.yml couldn't be initiated.");
		}


		try{
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));

			loader.setResources(ResourceBundle.getBundle("resources.localisation.local", new Locale("en", "EN")));

			AnchorPane rootLayout = loader.load();
			Scene scene = new Scene(rootLayout);

			controller = loader.getController();
			controller.setMainApp(this);

			//remove window decoration
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Grundbaustein e.V. Dienstplan-Software 2014");
			//primaryStage.getIcons().add(new Image("/resources/icons/app-icon.png"));
			//primaryStage.getIcons().add(new Image("/resources/icons/favicon_32.png"));
			//primaryStage.getIcons().add(new Image("/resources/icons/favicon_64.png"));
			primaryStage.getIcons().add(new Image("/resources/icons/blue-folder.png"));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public File getDataFolder() {
		File currentDirectory = new File(new File("").getAbsolutePath());
		//this.controller.setLabel(currentDirectory.toString());
		return (new File(currentDirectory.toString()));
	}

	public void LogInfo(String message) {
		logger.info(message);
	}

	public void LogDebug(String message) {
		logger.debug(message);
	}

	public void LogWarning(String message) {
		logger.warn(message);
	}

	public void LogError(String message) {
		logger.error(message);
	}

	public void LogError(String message, Throwable t) {
		logger.error(message, t);
	}

	private void initiateLogging() {
		try {
			//SimpleLayout layout = new SimpleLayout();
			HTMLLayout layout = new customHTMLLayout();
			layout.setLocationInfo(true);
			ConsoleAppender consoleAppender = new ConsoleAppender( layout );
			logger.addAppender( consoleAppender );
			FileAppender fileAppender = new FileAppender( layout, "logs/log-" + DateFormat.getDateInstance().format(new Date(System.currentTimeMillis())).replace(".", "-") + "_" + DateFormat.getTimeInstance().format(new Date(System.currentTimeMillis())).replace(":", "-") + ".html", false );
			logger.addAppender( fileAppender );
			// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
			logger.setLevel( Level.ALL );
		} catch( Exception ex ) {
			System.out.println( ex );
			LogError("Error registering Logger", ex);
		}
		logger.debug( "Debug-Meldungen: aktiviert" );
		logger.info( "Info-Meldung: aktiviert" );
		logger.warn( "Warn-Meldung: aktiviert" );
		logger.error( "Error-Meldung: aktiviert" );
		logger.fatal( "Fatal-Meldung: aktiviert" );
	}

	/**
    The <b>LocationInfo</b> option takes a boolean value. By
    default, it is set to false which means there will be no location
    information output by this layout. If the the option is set to
    true, then the file name and line number of the statement
    at the origin of the log statement will be output.

    <p>If you are embedding this layout within an {@link
    org.apache.log4j.net.SMTPAppender} then make sure to set the
    <b>LocationInfo</b> option of that appender as well.
	 */
}
