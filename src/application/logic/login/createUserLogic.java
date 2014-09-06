package application.logic.login;

import application.Main;

public class createUserLogic {

	// Reference to the main application
	private Main app;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public createUserLogic(Main app) {
		this.app = app;
	}
	
	public void createUser(String username, String password, String dbtype, String dbname, String dburl, String dbuser, String dbpassword) {
		
		String securePassword = this.app.loginLogicC.generatePasswordHash(password);
		String secureDbPassword = this.app.loginLogicC.generatePasswordHash(dbpassword);
		if(dbpassword.equalsIgnoreCase(""))
			secureDbPassword="";
		
		String sql = "INSERT INTO User (Username, Password, DatabaseType, DatabaseName, DatabaseURL, DatabaseUser, DatabasePassword) VALUES('" + username + "','" + securePassword + "','" + dbtype + "','" + dbname + "','" + dburl + "','" + dbuser + "','" + secureDbPassword + "')";
		
		this.app.sqliteDatabase.insert(sql);
		
	}

}