/*from:- https://sangbui.com/database-testing-with-java/
also refer to https://www.seleniumeasy.com/selenium-tutorials/database-testing-example-with-selenium-using-java*/
import java.sql.Connection;
import java.sql.Statement;
import org.testng.annotations.*;
import java.sql.ResultSet;
import java.sql.DriverManager;
 
public class database {
 
	// Config information
	static Connection con = null;
	private static Statement stmt;
	public static String DB_URL = "jdbc:mysql://localhost:3306/testdb";
	public static String DB_USER = "root";
	public static String DB_PASSWORD = "";
 
	// SQL queries
	String queryShowCustomers = "SELECT * FROM CUSTOMERS";
	String queryInsertCustomer = "INSERT INTO CUSTOMERS (ID,NAME,LICENSE_PLATE,PIN_CODE) VALUES (1, 'Raj', 'NVB 217', 7256)";
	String queryUpdateCustomer = "UPDATE Customers SET LICENSE_PLATE = 'BNC 552', PIN_CODE= '4389' WHERE ID = 3";
	String queryVerifySalaryWithID = "SELECT SALARY FROM CUSTOMERS WHERE ID = 3";
	
	@BeforeTest
	public void setUp() throws Exception {
		try {
			// Make the database connection
			String dbClass = "com.mysql.jdbc.Driver";
			Class.forName(dbClass).newInstance();
 
			// Get connection to DB
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			// Statement object to send the SQL statement to the Database
			stmt = con.createStatement();
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test(enabled = false)                    //enable this statement to 'true' to see the result
	public void testqueryShowCustomers() {
		try {
			// Get the contents of table from DB
			ResultSet res = stmt.executeQuery(queryShowCustomers);
 
			// Print the all result
			while (res.next()) {
				String cusID = res.getString(1);
				String cusName = res.getString(2);
				String cusLicensePlate = res.getString(3);
				String cusPINCode = res.getString(4);
 
				System.out.println(cusID + "\t" + cusName + "\t" + cusLicensePlate + "\t" + cusPINCode );
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	@Test(enabled = false)              //enable this statement to 'true' to see the result
	public void queryUpdateCustomer() {
		try {
			
			// Update data value
			stmt.executeUpdate(queryUpdateCustomer);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test(enabled = false)
	public void queryInsertCustomers() {
		try {			
			// Insert data value
			stmt.executeUpdate(queryInsertCustomer);
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	@Test(enabled = false)                        //enable this statement to 'true' to see the result
	public void testqueryShowCustomersWithID() {
		try {
			// Get the contents of table from DB
			ResultSet res = stmt.executeQuery(queryVerifySalaryWithID);
 
			// Print the all result
			while (res.next()) {	
				String cusPINCode = res.getString(1);
				
				if (cusPINCode.equals("3256"))
				{
					System.out.println("Pass");
				}		
				System.out.println("Fail, the actual value is " + cusPINCode);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
 
	@AfterTest
	public void tearDown() throws Exception {
		// Close DB connection
		if (con != null) {
			con.close();
		}
	}
 
}