import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author SANDELS
 * Tells about comparision of Hive database with the excelfile
 * Uses ApachiPOI
 *
 */
public class TagsExcel {

    private static final String FILE_NAME = "src/DelranTags.xlsx";

	public static void CompareTagsFromExcel(Connection con) throws SQLException, IOException {
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();
		int c=0,i=0,exist=0,notexist=0;
		ArrayList<String> excelTags = new ArrayList<String>();
		
		while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            if(currentRow.getCell(0)!=null) {
            	excelTags.add(currentRow.getCell(0).getStringCellValue());
            	//System.out.println((++c)+". "+currentRow.getCell(0).getStringCellValue());
            }
        }
		
		
		ArrayList<String> waterqualityTags = getTagsFromwaterquality(con);
		
		while(i<waterqualityTags.size()) {
			if(excelTags.contains(waterqualityTags.get(i).toUpperCase())) {
				System.out.println(String.format("%-75s", waterqualityTags.get(i))+ " EXISTS         - in Excel");
				exist++;
			}else {
				System.out.println(String.format("%-75s", waterqualityTags.get(i))+ " DOESNOT EXISTS - in Excel");
				notexist++;
			}
			i++;
		}
		System.out.println("\nTag Exist Count : "+exist + "\nTag Doesnot Exists Count : "+notexist);
	}
	
	public static ArrayList<String> getTagsFromwaterquality(Connection con) throws SQLException{
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Enter column name");
		String c1 = scanner.next();
		System.out.println("Enter table name");
		String table = scanner.next();

		
		Statement st = con.createStatement();
		ArrayList<String> al = new ArrayList<>();
		Set<String> waterqualityTagsSet = new HashSet<String>();

		ResultSet res = st.executeQuery("select distinct "+c1+" from awinternal."+table+"");
		while (res.next()) {
			al.add(res.getString(1));
			waterqualityTagsSet.add(res.getString(1));
			//System.out.println(res.getString(1));
		}
		System.out.println("Count of Set"+waterqualityTagsSet.size()+" AL: "+ al.size());
		con.close();
		return al;
	}

}
