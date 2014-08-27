package application.controller.overview;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class overviewController {	

	@FXML
	private Button overview_button_new;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public overviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		this.overview_button_new.setGraphic(new ImageView(new Image(Main.getInstance().getClass().getResourceAsStream("/resources/icons/application/window_app_list_add_32.png"))));
		
	}
}