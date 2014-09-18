package application.logic.overview;

import database.sqlite.SQLite;
import util.codegeneration.CodeGenerator;
import application.Main;

public class createPlanLogic {

	// Reference to the main application
	private Main app;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public createPlanLogic(Main app) {
		this.app = app;
	}
	
	public void createUser(String username, String password, String dbtype, String dbname, String dburl, String dbuser, String dbpassword) {
		
		String securePassword = this.app.loginLogicC.generatePasswordHash(password);
		String secureDbPassword = this.app.loginLogicC.generatePasswordHash(dbpassword);
		if(dbpassword.equalsIgnoreCase(""))
			secureDbPassword="";
		
		String sql = "";
		
		if(dbtype.equalsIgnoreCase("SQLite")) {
			sql = "INSERT INTO User (Username, Password, DatabaseType, DatabaseFile, DatabaseName, DatabaseURL, DatabaseUser, DatabasePassword) VALUES('" + username + "','" + securePassword + "','" + dbtype + "','" + CodeGenerator.getNext() + "','" + dbname + "','" + dburl + "','" + dbuser + "','" + secureDbPassword + "')";
		} else {
			sql = "INSERT INTO User (Username, Password, DatabaseType, DatabaseFile, DatabaseName, DatabaseURL, DatabaseUser, DatabasePassword) VALUES('" + username + "','" + securePassword + "','" + dbtype + "','" + "" + "','" + dbname + "','" + dburl + "','" + dbuser + "','" + secureDbPassword + "')";
		}
		
		
		SQLite dbconn = new SQLite();
		dbconn.connect("settings", "settings.db");
		
		dbconn.insert(sql);		
		
		dbconn.disconnect();
		dbconn = null;
		
	}

}