package application.controller.overview;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class createPlanController {	

	@FXML
	private TextField createplan_textfield_name;
	@FXML
	private TextArea createplan_textarea_description;
	@FXML
	private DatePicker createplan_datepicker_startdate;
	@FXML
	private DatePicker createplan_datepicker_enddate;
	@FXML
	private TextField createplan_textfield_prefix;
	@FXML
	private Button createplan_button_createplan;
	@FXML
	private Button createplan_button_resetplan;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public createPlanController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize(){
		
		this.createplan_button_createplan.setOnAction((event) -> {
			if(checkFields()) {
				//Main.getInstance().createplanLogicC.createplan(this.createplan_textfield_username.getText(), this.createplan_textfield_password.getText(), this.databaseType, this.createplan_textfield_databasename.getText(), this.createplan_textfield_databaseurl.getText(), this.createplan_textfield_databaseuser.getText(), this.createplan_textfield_databasepassword.getText());
				Main.getInstance().registerUserStage.close();
			} else {
				return;
			}
		});
		
		this.createplan_button_resetplan.setOnAction((event) -> {
			this.resetForm();
		});
	
	}

	private void resetForm() {
	
		this.createplan_textfield_name.setText("");
		this.createplan_textarea_description.setText("");
		
	}
	
	private boolean checkFields() {
		if(this.createplan_textfield_name.getText().equalsIgnoreCase("")) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createplan_textfield_name, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_username"), null);
			return false;
		} else if(this.createplan_textarea_description.getText().equalsIgnoreCase("")) {
			Main.getInstance().showTooltip(Main.getInstance().registerUserStage, this.createplan_textarea_description, Main.getInstance().resourceBundle.getString("key.login_tooltip_fillin_password"), null);
			return false;
		}
		return true;
	}
}