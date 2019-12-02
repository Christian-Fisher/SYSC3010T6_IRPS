import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	int PIN1;
	String[] userNames;
	
	private Connection connect() {
		String url = "jdbc:sqlite:C:\\Users\\Akash\\Desktop\\Important\\Fall 2019\\SYSC 3010\\SYSC3010T6_IRPS\\SYSC3010T6_IRPS\\Database\\IRPSDatabase.db";
		Connection connect = null;
		//PIN1 = new Integer[10];
		userNames = new String[10];
		try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connect;
	}
	
	public void printAll(){
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Username") +  "\t" + 
                                   rs.getInt("PIN") + "\t" +
                                   rs.getString("LicensePlate") + "\t" +
                                   rs.getInt("BookedSpot"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public Integer getPINs() {
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
	        Statement stmt  = conn.createStatement();
	        ResultSet rs    = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				PIN1 = rs.getInt("PIN");
				System.out.println(PIN1);
			}
		}catch (SQLException e) {
            System.out.println(e.getMessage());
	   }
		return PIN1;
   }
	
	public String[] getNames() {
		int i=0;
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
	        Statement stmt  = conn.createStatement();
	        ResultSet rs    = stmt.executeQuery(sql)) {
			while(rs.next()) {
				userNames[i] = rs.getString("Username");
				System.out.println("The usernames present in the database are "+userNames[i]);
				i++;
			}
			
		}catch (SQLException e) {
            System.out.println(e.getMessage());
		}
		return userNames;
	}

	public static void main(String[] args) {
        Database db = new Database();
        db.printAll();
        db.getPINs();
        db.getNames();
    }

}
