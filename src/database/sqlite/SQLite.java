package database.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import application.Main;

public class SQLite {

	private Main app = null;
	private Connection conn = null;
	private Statement stmt = null;

	public SQLite(Main app){
		this.app = app;
	}

	public int create(String statement) {

		connect();
		
		try {
			stmt = conn.createStatement();
			int rs = stmt.executeUpdate(statement);
			disconnect();
			return rs;
		} catch (Exception e) {
			this.app.log.LogError("", e);
			disconnect();
			return 0;
		}

	}

	public int update(String statement) {

		connect();
		
		try {
			stmt = conn.createStatement();
			int rs = stmt.executeUpdate(statement);
			disconnect();
			return rs;
		} catch (Exception e) {
			this.app.log.LogError("", e);
			disconnect();
			return 0;
		}

	}

	public int insert(String statement) {

		connect();
		
		try {
			stmt = conn.createStatement();
			int rs = stmt.executeUpdate(statement);
			disconnect();
			return rs;
		} catch (Exception e) {
			this.app.log.LogError("", e);
			disconnect();
			return 0;
		}

	}

	public ResultSet select(String statement) {

		connect();
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(statement);
			disconnect();
			return rs;
		} catch (Exception e) {
			this.app.log.LogError("", e);
			disconnect();
			return null;
		}

	}

	private void connect() {

		try {
			Class.forName("org.sqlite.JDBC");
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.app.getDataFolder() + File.separator + "configs" + File.separator + "settings.db");
			this.app.log.LogDebug("Successfully connected to Database.");
		} catch (Exception e) {
			this.app.log.LogError("", e);
		}

	}

	private void disconnect() {

		try {
			stmt.close();
			conn.close();
			this.app.log.LogDebug("Successfully disconnected from Database.");
		} catch (Exception e) {
			this.app.log.LogError("", e);
		}

	}

}
