package configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;

public class settings {

	private Main app = null;

	public settings(Main app) {
		this.app = app;

		initiateSettings();
	}

	private void initiateSettings() {

		createSettingsDatabase();
		createUserDatabase();

		updateDatabase();

	}

	private void createSettingsDatabase() {

		ResultSet rs = this.app.sqliteDatabase.getMetaData();		
		Boolean contains = false;

		try {
			while (rs.next()) {
				if(rs.getString(3).contains("Settings")) {
					contains = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(contains == false) {

			String sql = "CREATE TABLE IF NOT EXISTS Settings " +
					"(ID 						INTEGER 	PRIMARY KEY	AUTOINCREMENT, " +
					" Credit_Author           	TEXT    NOT NULL, " +
					" Credit_Organization       TEXT    NOT NULL, " +
					" Credit_Version           	TEXT    NOT NULL, " +
					" Localization_Language     TEXT    NOT NULL, " +
					" Localization_Country      TEXT    NOT NULL, " +
					" Localization_Description  TEXT    NOT NULL);";

			this.app.sqliteDatabase.create(sql);
			
			sql = "INSERT INTO Settings " +
					"(Credit_Author, " +
					" Credit_Organization, " +
					" Credit_Version, " +
					" Localization_Language, " +
					" Localization_Country, " +
					" Localization_Description) " + 
					" VALUES " + 
					"('" + this.app.CREDIT_AUTHOR + "', " +
					"'" + this.app.CREDIT_ORGANIZATION + "', " +
					"'" + this.app.CREDIT_VERSION + "', " +
					"'de', " +
					"'DE', " +
					"'Deutsch');";

			this.app.sqliteDatabase.insert(sql);
		}



	}

	private void createUserDatabase() {

		String sql = "CREATE TABLE IF NOT EXISTS User " +
				"(ID 						INTEGER 	PRIMARY KEY	AUTOINCREMENT, " +
				" Username				    TEXT    			NOT NULL, " +
				" Password				    TEXT    			NOT NULL, " +
				" DatabaseType			    TEXT    			NOT NULL, " +
				" DatabaseName			    TEXT    			NOT NULL, " +
				" DatabaseURL			    TEXT    			NOT NULL, " +
				" DatabaseUser			    TEXT    			NOT NULL, " +
				" DatabasePassword         	TEXT    			NOT NULL);";

		this.app.sqliteDatabase.create(sql);

	}

	private void updateDatabase() {

		String sql = "UPDATE Settings SET " +
				"Credit_Author='" + this.app.CREDIT_AUTHOR + "', " +
				"Credit_Organization='" + this.app.CREDIT_ORGANIZATION + "', " +
				"Credit_Version='" + this.app.CREDIT_VERSION + "' WHERE ID=1;";

		this.app.sqliteDatabase.update(sql);

	}

	public String getLanguageDescription() {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings WHERE ID=1;");
		if(rs == null) {
			return "Deutsch";
		} else {
			String description = "";
			try {
				while ( rs.next() ) {
					description = rs.getString("Localization_Description");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.app.log.LogDebug("Description: " + description);
			return description;
		}
	}

	public String getLanguageLanguage() {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings WHERE ID=1;");
		if(rs == null) {
			return "de";
		} else {
			String language = "";
			try {
				while ( rs.next() ) {
					language = rs.getString("Localization_Language");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.app.log.LogDebug("Language: " + language);
			return language;
		}
	}

	public String getLanguageCountry() {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings WHERE ID=1;");
		if(rs == null) {
			return "DE";
		} else {
			String country = "";
			try {
				while ( rs.next() ) {
					country = rs.getString("Localization_Country");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.app.log.LogDebug("Country: " + country);
			return country;
		}
	}

	public void setLanguage(String language, String country, String description) {
		String sql = "UPDATE Settings SET " +
				"LOCALIZATION_LANGUAGE='" + language + "', " +
				"LOCALIZATION_COUNTRY='" + country + "', " +
				"LOCALIZATION_DESCRIPTION='" + description + "' WHERE ID=1;";

		this.app.sqliteDatabase.update(sql);
	}

}
