package GenericUtilites;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

/**
 * @author Vamsi
 * This method is used for DataBase
 */

public class DataBaseUtility {
	
	
	
	public Connection con;
	
	public Connection getDataBaseConnection() throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
		return con;
	}
	
	/**
	 * This method is used to fetch data from DataBase
	 * @param url
	 * @param un
	 * @param pwd
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	
	public ResultSet FetchDataFromDatabase(String url, String un, String pwd, String query) throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con= DriverManager.getConnection(url, un, pwd);
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;
		
	}
	
	/**
	 * this is a over loaded method to fetch data from data base
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	
	public ResultSet FetchDataFromDatabase(String query) throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con= DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;
		
	}
	/**
	 * This method is used to write back data to the database
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public int writeBactDataToDatabase(String query) throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con= DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
		Statement stmt = con.createStatement();
		int data = stmt.executeUpdate(query);
		return data;
		
		
	}
	/**
	 * this method is used to clsoe the database connection
	 * @throws SQLException
	 */
	
	public void closeDatabaseConnection() throws SQLException {
		con.close();
	}

}
