import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection connect() {
		String url = "jdbc:sqlite:C:\\Users\\Akash\\Desktop\\Important\\Fall 2019\\SYSC 3010\\SYSC3010T6_IRPS\\SYSC3010T6_IRPS\\Database\\IRPSDatabase.db";
		Connection connect = null;
		try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connect;
	}
	
	public void selectAll(){
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
	
	public static void main(String[] args) {
        Database db = new Database();
        db.selectAll();
    }

}
