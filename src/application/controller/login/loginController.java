package application.controller.login;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class loginController {

	

	@FXML
	private Button login_button_login;
	@FXML
	private Button login_button_info;
	@FXML
	private Button login_button_quit;
	@FXML
	private TextField login_textfield_username;
	@FXML
	private PasswordField login_passwordfield_password;
	@FXML
	private MenuBar login_menubar_language;
	@FXML
	private Menu login_menu_language;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public loginController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		MenuItem german = new MenuItem("Deutsch");
		german.setGraphic(new ImageView(new Image(Main.getInstance().getClass().getResourceAsStream("/resources/icons/flags/DE.png"))));
		MenuItem english = new MenuItem("English");		
		english.setGraphic(new ImageView(new Image(Main.getInstance().getClass().getResourceAsStream("/resources/icons/flags/GB.png"))));
		
		this.login_menu_language.getItems().add(german);
		this.login_menu_language.getItems().add(english);

		//int index = languageList.indexOf(Main.getInstance().appConfig.getLanguageDescription());
		//this.login_choicebox_language.getSelectionModel().select(languageList.get(index));
		
		this.login_button_quit.setOnAction((event) -> {
			Platform.exit();
		});
		
		this.login_button_info.setOnAction((event) -> {
			Main.getInstance().loginLogicC.displayInfoLayer();
		});
		
		german.setOnAction((event) -> {
			Main.getInstance().loginLogicC.setLanguage("Deutsch");
		});
		english.setOnAction((event) -> {
			Main.getInstance().loginLogicC.setLanguage("English");
		});
		
	}
	
	public void setLabel(String text){
		this.login_textfield_username.setText(text);
	}

}