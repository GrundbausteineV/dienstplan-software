package application;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import application.controller.login.loginController;
import application.logic.login.loginLogic;
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
	
	public loginLogic loginLogicC = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {    	

		loginLogicC = new loginLogic(this);
    	
    	try{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/login/login.fxml"));
            
            loader.setResources(ResourceBundle.getBundle("resources.localisation.local", new Locale("en", "EN")));
            
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            
            loginController controller = loader.getController();
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
}
