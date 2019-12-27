package DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connectToDB {
	public static Statement DBStmt() {
		Connection 	conn = null;
		Statement 	stmt = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://59.27.140.107:3306/super_junior?characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
            conn = DriverManager.getConnection(url, "super", "junior");

            stmt = conn.createStatement();
        }
        catch( SQLException e){
            System.out.println("Error " + e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stmt;
	}
	
	public static void main(String[] args) {
	}
}
