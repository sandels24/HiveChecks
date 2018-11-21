
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class HiveConnection {


    static Connection con;
    static Properties prop = new Properties();
    	
    // static method to create single instance 
    public static Connection getConnection() throws SQLException 
    { 
    	try {
			prop.load(new FileInputStream("src/db.properties"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        if (con == null) {
        	try {
				Class.forName(prop.getProperty("driverName"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        	StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
    	    decryptor.setPassword("hello");  
        	con = DriverManager.getConnection(prop.getProperty("hiveDriverUrl"), prop.getProperty("user"), decryptor.decrypt(prop.getProperty("encPass")));
        	System.out.println("Connection Created");
        }
        return con; 
    } 
}
