package application.logic.overview;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.sqlite.SQLite;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
				
				this.app.planOverview.add(button);
			}
		} catch (SQLException e) {
			this.app.log.LogError("Can't receive data from database: " + this.app.user.getDatabaseFile(), e);
		}
		
		dbconn.disconnect();
		dbconn = null;
		
	}
	
}
