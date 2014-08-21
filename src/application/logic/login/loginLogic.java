package application.logic.login;

import application.Main;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginLogic {

	// Reference to the main application
	@SuppressWarnings("unused")
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

}