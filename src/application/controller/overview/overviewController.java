package application.controller.overview;

import application.Main;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class overviewController {	

	@FXML
	private FlowPane overview_flowpane_plans;
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
	private void initialize(){
		
		ImageView iconImage = new ImageView(new Image("/resources/icons/application/blue-folder.png"));
		iconImage.setFitHeight(64);
		iconImage.setFitWidth(64);
		this.overview_button_new.setGraphic(iconImage);
		
		this.overview_flowpane_plans.getChildren().addAll(Main.getInstance().planOverview);
		
		this.overview_button_new.setOnAction((event) -> {
			Main.getInstance().planOverview.add(new Button("Hallo"));
		});
		
		Main.getInstance().planOverview.addListener(new ListChangeListener<Button>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Button> c) {
				
				while (c.next()) {
	                 if (c.wasPermutated()) {
	                     for (int i = c.getFrom(); i < c.getTo(); ++i) {
	                          //permutate
	                     }
	                 } else if (c.wasUpdated()) {
	                          //update item
	                 } else {
	                     for (Button remitem : c.getRemoved()) {
	                    	 getOverviewFlowPanePlanes().getChildren().remove(remitem);
	                     }
	                     for (Button additem : c.getAddedSubList()) {
	                    	 getOverviewFlowPanePlanes().getChildren().add(additem);
	                     }
	                 }
	             }

			}
			
		});
		
		/*this.overview_listview_plans.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
			
		});*/
	}
	
	public FlowPane getOverviewFlowPanePlanes() {
		return this.overview_flowpane_plans;
	}
}