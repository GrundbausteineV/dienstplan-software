package application.controller.login;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class createUserController {	

	@FXML
	private TextField createuser_textfield_username;
	@FXML
	private PasswordField createuser_textfield_password;
	@FXML
	private PasswordField createuser_textfield_password_confirm;
	@FXML
	private ToggleButton createuser_togglebutton_sqlite;
	@FXML
	private ToggleButton createuser_togglebutton_mysql;
	@FXML
	private TextField createuser_textfield_databasename;
	@FXML
	private Label createuser_label_databasename;
	@FXML
	private TextField createuser_textfield_databaseurl;
	@FXML
	private Label createuser_label_databaseurl;
	@FXML
	private TextField createuser_textfield_databaseuser;
	@FXML
	private Label createuser_label_databaseuser;
	@FXML
	private PasswordField createuser_textfield_databasepassword;
	@FXML
	private Label createuser_label_databasepassword;
	@FXML
	private Button createuser_button_createuser;
	@FXML
	private Button createuser_button_resetuser;
	
	private String databaseType;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public createUserController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize(){
		
		this.databaseType = "SQLite";
		
		this.createuser_togglebutton_sqlite.setOnAction((event) -> {
			this.databaseType = "SQLite";
			this.createuser_label_databasename.setText(this.createuser_label_databasename.getText().replace("*", ""));
			this.createuser_label_databaseurl.setText(this.createuser_label_databaseurl.getText().replace("*", ""));
			this.createuser_label_databaseuser.setText(this.createuser_label_databaseuser.getText().replace("*", ""));
			this.createuser_label_databasepassword.setText(this.createuser_label_databasepassword.getText().replace("*", ""));
		});
		
		this.createuser_togglebutton_mysql.setOnAction((event) -> {
			this.databaseType = "MySQL";
			this.createuser_label_databasename.setText(this.createuser_label_databasename.getText().replace("*", "") + "*");
			this.createuser_label_databaseurl.setText(this.createuser_label_databaseurl.getText().replace("*", "") + "*");
			this.createuser_label_databaseuser.setText(this.createuser_label_databaseuser.getText().replace("*", "") + "*");
			this.createuser_label_databasepassword.setText(this.createuser_label_databasepassword.getText().replace("*", "") + "*");
		});
		
		this.createuser_button_createuser.setOnAction((event) -> {
			if(checkFields()) {
				Main.getInstance().createUserLogicC.createUser(this.createuser_textfield_username.getText(), this.createuser_textfield_password.getText(), this.databaseType, this.createuser_textfield_databasename.getText(), this.createuser_textfield_databaseurl.getText(), this.createuser_textfield_databaseuser.getText(), this.createuser_textfield_databasepassword.getText());
				Main.getInstance().registerUserStage.close();
			} else {
				return;
			}
		});
		
		this.createuser_button_resetuser.setOnAction((event) -> {
			this.resetForm();
		});
	
	}

	private void resetForm() {
	
		this.createuser_textfield_username.setText("");
		this.createuser_textfield_password.setText("");
		this.createuser_textfield_password_confirm.setText("");
		this.createuser_textfield_databasename.setText("");
		this.createuser_textfield_databaseurl.setText("");
		this.createuser_textfield_databaseuser.setText("");
		this.createuser_textfield_databasepassword.setText("");
		
	}
	
	private boolean checkFields() {
		if(this.createuser_textfield_username.getText().equalsIgnoreCase("")) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_username, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_username"), null);
			return false;
		} else if(this.createuser_textfield_password.getText().equalsIgnoreCase("")) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_password, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_password"), null);
			return false;
		} else if(!this.createuser_textfield_password.getText().equalsIgnoreCase(this.createuser_textfield_password_confirm.getText())) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_password, Main.getInstance().resourceBundle.getString("key.login_tooltip_error_passwordconfirm"), null);
			return false;
		} else if(this.createuser_textfield_databasename.getText().equalsIgnoreCase("")) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_databasename, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_databasename"), null);
			return false;
		}
		if(this.databaseType.equalsIgnoreCase("MySQL")) {
			if(this.createuser_textfield_databaseurl.getText().equalsIgnoreCase("")) {
				Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_databaseurl, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_databaseurl"), null);
				return false;
			} else if(this.createuser_textfield_databaseuser.getText().equalsIgnoreCase("")) {
				Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_databaseuser, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_databaseuser"), null);
				return false;
			} else if(this.createuser_textfield_databasepassword.getText().equalsIgnoreCase("")) {
				Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createuser_textfield_databasepassword, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_databasepassword"), null);
				return false;
			}
		}
		return true;
	}
}