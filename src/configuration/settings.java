package configuration;

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
			" Localization_Description  TEXT    NOT NULL)";
			
			this.app.sqliteDatabase.create(sql);
			
			sql = "INSERT INTO Settings " +
			"(Credit_Author, " +
			" Credit_Organization, " +
			" Credit_Version, " +
			" Localization_Language, " +
			" Localization_Country, " +
			" Localization_Description) " + 
			" VALUES " + 
			"('Jan-Eric Dreßler', " +
			"'Gemeinschaft für Medienkompetenz Grundbaustein e.V.', " +
			"'0.0.1', " +
			"'de', " +
			"'DE', " +
			"'Deutsch')";
			
			this.app.sqliteDatabase.insert(sql);

			this.app.log.LogInfo("Initiierung der Settings-Datenbank abgeschlossen.");
			
			return;
			
		} else {
			
			this.app.log.LogInfo("" + this.app.sqliteDatabase.select("SELECT * FROM settings"));
			this.app.log.LogInfo("Die Settings-Datenbank ist bereits vorhanden.");
			return;
			
		}
		
	}

}
