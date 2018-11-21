import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainClass {

	/**
	 * 
	 */
	static Connection con=null;
	public static void main(String args[]) throws Exception {
		
		//con = HiveConnection.getConnection();
		Checks c = new Checks();
		//c.tagFormatCheck(con);
		//c.count_installedSensors();
		//c.count_waterquality();
		//c.tagComparision();
		//c.nullValueTagsInWaterqulity();
		//c.TagsExcel.CompareTagsFromExcel(con);
	}
	
}
