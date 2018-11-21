import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Checks {

	public void count_installedSensors(Connection con) throws SQLException {
		Statement st = con.createStatement();
		
		ResultSet res = st.executeQuery("select count(distinct tagname) from awinternal.wq_installedsensors");
		System.out.print("Count of defined tags in wq_installedsensors = ");
		while (res.next()) {
			System.out.println(res.getString(1));
		}
		
		res = st.executeQuery("select count(*) from awinternal.wq_installedsensors where tagname is null");
		System.out.print("Count of records with no tags defined in wq_installedsensors = ");
		while (res.next()) {
			System.out.println(res.getString(1));
		}
		
		res = st.executeQuery("select tagname from awinternal.wq_installedsensors group by tagname having count(tagname)>1");
		System.out.println("Duplicate tags defined in wq_installedsensors = ");
		while (res.next()) {
			System.out.println("\t"+res.getString(1));
		}

		//con.close();

	}


	public void count_waterquality(Connection con) throws SQLException {
		Statement st = con.createStatement();
		
		ResultSet res = st.executeQuery("select count(distinct tagname) from awinternal.waterquality");
		System.out.print("Count of distinct tags in waterquality = ");
		while (res.next()) {
			System.out.println(res.getString(1));
		}
		
		res = st.executeQuery("select count(*) from awinternal.waterquality where tagname is null");
		System.out.print("Count of records with no tags  in waterquality = ");
		while (res.next()) {
			System.out.println(res.getString(1));
		}
		
		res = st.executeQuery("select count(*), tagname from awinternal.wq_installedsensors group by tagname");
		System.out.println("Duplicate tags defined in wq_installedsensors = ");
		while (res.next()) {
			System.out.println("\t"+res.getString(1)+"\t"+res.getString(2));
		}

		//con.close();

	}
	
	public void tagFormatCheck(Connection con) throws SQLException {
		Statement st = con.createStatement();
 		ResultSet res = st.executeQuery("select distinct tagname from awinternal.waterquality");
		System.out.println(" distinct tags in waterquality = ");
		while (res.next()) {
			if(res.getString(1).matches("[A-Z]{2}:[A-Z]{2}:[A-Z]{2}:[A-Z]{4}:[A-Z]{4}:[a-zA-Z_0-9]*"))
				System.out.println(res.getString(1)+ " \t\t: Match");
			else
				System.out.println(res.getString(1)+ " \t\t: Doesnot Match");
		}
		
		con.close();
	}
	
	public void tagComparision(Connection con) throws SQLException {
		Statement st = con.createStatement();
		ArrayList<String> al1 = new ArrayList<>();
		ArrayList<String> al2 = new ArrayList<>();
		
		int i = 0, notexist =0, exist=0;
 		ResultSet res = st.executeQuery("select distinct tagname from awinternal.waterquality");
		while (res.next()) {
			al1.add(res.getString(1));
		}
		
		ResultSet res2 = st.executeQuery("select distinct tagname from awinternal.wq_installedsensors");
		while (res2.next()) {
			if(res2.getString(1) != null) {
				al2.add(res2.getString(1));
			}
		}
		
		while(i<al1.size()) {
			if(al2.contains(al1.get(i))) {
				System.out.println(String.format("%-75s", al1.get(i))+ " EXISTS         - in wq_installedSensors");
				exist++;
			}else {
				System.out.println(String.format("%-75s", al1.get(i))+ " DOESNOT EXISTS - in wq_installedSensors");
				notexist++;
			}
			i++;
		}
		
		System.out.println("\nTag Exist Count : "+exist + "\nTag Doesnot Exists Count : "+notexist);
		con.close();
	}

	public void nullValueTagsInWaterqulity(Connection con) throws SQLException{
		Statement st = con.createStatement();
 		//ResultSet res = st.executeQuery("select tagname, systemtime from awinternal.waterquality where value is null");
 		ResultSet res = st.executeQuery("select count(distinct tagname) from awinternal.waterquality where value is null");

		System.out.println(" tags in waterquality with null values = ");
		while (res.next()) {
			System.out.println(res.getString(1));
				//System.out.println(String.format("%-75s", res.getString(1))+"\t"+ res.getString(2));
		}
		con.close();
	}
}
