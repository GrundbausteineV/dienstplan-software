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
		
		if(this.app.sqliteDatabase.select("SELECT * FROM settings") == null) {
			
			this.app.log.LogInfo("Die Settings-Datenbank ist nicht vorhanden.");
			this.app.log.LogInfo("Initiiere Settings-Datenbank ...");
			
			String sql = "CREATE TABLE Settings " +
			"(Credit_Author           	TEXT    NOT NULL, " +
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
			"( " + this.app.CREDIT_AUTHOR + ", " +
			" " + this.app.CREDIT_ORGANIZATION + ", " +
			" " + this.app.CREDIT_VERSION + ", " +
			"'de', " +
			"'DE', " +
			"'Deutsch');";
			
			this.app.sqliteDatabase.insert(sql);

			this.app.log.LogInfo("Initiierung der Settings-Datenbank abgeschlossen.");
			
			return;
			
		} else {
			
			String sql = "UPDATE Settings SET " +
					"Credit_Author='" + this.app.CREDIT_AUTHOR + "', " +
					"Credit_Organization='" + this.app.CREDIT_ORGANIZATION + "', " +
					"Credit_Version='" + this.app.CREDIT_VERSION + "';";
					
			this.app.sqliteDatabase.update(sql);
			
			this.app.log.LogInfo("" + this.app.sqliteDatabase.select("SELECT * FROM Settings;"));
			this.app.log.LogInfo("Die Settings-Datenbank ist bereits vorhanden.");
			return;
			
		}
		
	}
	
	public String getLanguageDescription() throws SQLException {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings;");
		if(rs == null) {
			return "Deutsch";
		} else {
			String description = "";
			while ( rs.next() ) {
				description = rs.getString("Localization_Description");
			}
			this.app.log.LogDebug("Description: " + description);
			return description;
		}
	}
	
	public String getLanguageLanguage() throws SQLException {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings;");
		if(rs == null) {
			return "de";
		} else {
			String language = "";
			while ( rs.next() ) {
				language = rs.getString("Localization_Language");
			}
			this.app.log.LogDebug("Language: " + language);
			return language;
		}
	}
	
	public String getLanguageCountry() throws SQLException {
		ResultSet rs = this.app.sqliteDatabase.select("SELECT * FROM Settings;");
		if(rs == null) {
			return "DE";
		} else {
			String country = "";
			while ( rs.next() ) {
				country = rs.getString("Localization_Country");
			}
			this.app.log.LogDebug("Country: " + country);
			return country;
		}
	}
	
	public void setLanguage(String language, String country, String description) {
		String sql = "UPDATE Settings SET " +
				"LOCALIZATION_LANGUAGE='" + language + "', " +
				"LOCALIZATION_COUNTRY='" + country + "', " +
				"LOCALIZATION_DESCRIPTION='" + description + "';";
		
		this.app.sqliteDatabase.update(sql);
	}

}
