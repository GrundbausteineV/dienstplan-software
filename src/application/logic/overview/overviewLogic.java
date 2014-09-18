package application.logic.overview;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.sqlite.SQLite;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.Main;

public class overviewLogic {

	private Main app = null;

	public overviewLogic(Main app) {
		this.app = app;
	}
	
	public void initPlanOverview() {
		
		SQLite dbconn = new SQLite();
		dbconn.connect(this.app.SAVE_DIRECTORY, this.app.user.getDatabaseFile());
		
		String sql = "SELECT * FROM Plans;";
		
		ResultSet rs = dbconn.select(sql);
		
		try {
			while(rs.next()) {
				Button button = new Button();
				button.setMinWidth(125);
				button.setMaxWidth(125);
				button.setMinHeight(80);
				button.setText(rs.getString("Name"));
				button.setContentDisplay(ContentDisplay.TOP);
				ImageView iconImage = new ImageView(new Image("/resources/icons/application/blue-folder.png"));
				iconImage.setFitHeight(64);
				iconImage.setFitWidth(64);
				button.setGraphic(iconImage);
				Tooltip tip = new Tooltip();
				tip.setText(rs.getString("Description") + "\r\n" + "Start: " + rs.getString("Start_Date") + "\r\n" + "Ende: " + rs.getString("End_Date"));
				button.setTooltip(tip);
				
				this.app.planOverview.add(button);
			}
		} catch (SQLException e) {
			this.app.log.LogError("Can't receive data from database: " + this.app.user.getDatabaseFile(), e);
		}
		
		dbconn.disconnect();
		dbconn = null;
		
	}

	public void displayRegisterPlanLayer() {
		Stage dialog = new Stage();
		this.app.registerPlanStage = dialog;
		dialog.initStyle(StageStyle.UTILITY);
		
		AnchorPane pane = null;
		FXMLLoader fxmlLoader = null;
		fxmlLoader = new FXMLLoader(this.getClass().getResource("/resources/fxml/overview/createPlan.fxml"));
		fxmlLoader.setResources(this.app.resourceBundle);
		try {
			pane = fxmlLoader.load();
		} catch (Exception ex) {
			this.app.log.LogError("can't load /resources/fxml/overview/createPlan.fxml", ex);
		}
		
		Scene scene = new Scene(pane);
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.setTitle(this.app.APPLICATION_TITLE);
		dialog.getIcons().add(new Image(this.app.APPLICATION_ICON));
		dialog.show();
		
		return;
	}
	
}
