package application.controller.overview;

import java.util.Locale;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class overviewController {	

	@FXML
	private ListView<String> overview_listview_plans;
	@FXML
	private Button overview_button_new;
	@FXML
	private ImageView overview_imageview_logo;
	@FXML
	private Label overview_label_name;

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
	private void initialize(){
		/*this.overview_imageview_logo.setImage(new Image("/resources/icons/application/blue-folder.png"));
		this.overview_imageview_logo.setLayoutX(19);
		this.overview_imageview_logo.setLayoutY(164);
		this.overview_imageview_logo.setOpacity(1.0);*/
		this.overview_listview_plans.setItems(Main.getInstance().planOverview);
		//this.overview_listview_plans.setOpacity(0.75);
		
		this.overview_listview_plans.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			ResourceBundle langBundle = null;
			try {
				langBundle = ResourceBundle.getBundle("resources.localisation.local", new Locale(Main.getInstance().settings.getLanguageLanguage(), Main.getInstance().settings.getLanguageCountry()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String newPlanString = langBundle.getString("key.overview_new_plan");
			if(newValue.equalsIgnoreCase(newPlanString)) {
				this.overview_label_name.setText(newValue + "Neuer Plan");
			} else {
				this.overview_label_name.setText(newValue);
			}
			
		});
	}
}