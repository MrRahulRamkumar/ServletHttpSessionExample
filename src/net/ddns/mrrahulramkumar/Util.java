package net.ddns.mrrahulramkumar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Util {

	public static final String DB_PATH = "jdbc:mysql://localhost:3306/exampledb?user=rahul&password=<password>";
		
//	public static final String DB_PATH = "jdbc:sqlite:/var/lib/tomcat8/webapps/users.db";

	public static String select(String username) {
		String password = "";
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			c = DriverManager.getConnection(DB_PATH);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PASSWORD FROM USERS WHERE USERNAME='" + username + "';");

			while (rs.next()) {
				password = rs.getString("password");
				System.out.println("Password = " + password);
				break;
			}
			rs.close();
			stmt.close();
			c.close();
			System.out.println("Operation done successfully");
			return password;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return password;
	}

	public static boolean insert(String username, String password) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(DB_PATH);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "INSERT INTO USERS (USERNAME, PASSWORD) " + "VALUES ('" + username + "', '" + password + "');";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();

			System.out.println("Records created successfully");
			return true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("Username already exists");
			return false;
		}
	}
	public static void main(String [] agrs) {
		select("rahul");
	}
}

