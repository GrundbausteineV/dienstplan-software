package application.controller.login;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
	@FXML
	private ImageView login_icon_language;

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
		
		this.login_menu_language.getItems().addAll(Main.getInstance().languageList);

		//int index = languageList.indexOf(Main.getInstance().appConfig.getLanguageDescription());
		//this.login_choicebox_language.getSelectionModel().select(languageList.get(index));
		
		this.login_button_quit.setOnAction((event) -> {
			Platform.exit();
		});
		
		this.login_button_info.setOnAction((event) -> {
			Main.getInstance().loginLogicC.displayInfoLayer();
		});
		
		Main.getInstance().languageList.forEach(item -> {
			item.setOnAction((event) -> {
				Main.getInstance().loginLogicC.setLanguage(item.getText());
			});
		});
		
		this.login_icon_language.setImage(new Image("/resources/icons/flags/" + Main.getInstance().languageIcon.get(0) + ".png"));
		
	}
	
	public void setLabel(String text){
		this.login_textfield_username.setText(text);
	}

}