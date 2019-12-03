import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LotDB {
	
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
	
	public void printAll(){
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Users";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("SpotNumber") +  "\t" + 
                                   rs.getInt("Occupancy") + "\t" +
                                   rs.getInt("BookTime"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        /*
        public String[] getLotOccupancy()
        
        RETURNS: an array of strings. Each string contains a single spot's occupancy
        */
        
        /*
        public void carArrived(String spot, boolean occupancy)
        This method takes a spot number in, and changes it's occupancy to true. This is different from bookSpot because this is for a user parking without booking.
        */
        
	/*
        public boolean bookSpot(String spot, String User)
        Parameters: spot contains the spot that User is booking. 
        Sets BookTime of the spot to the current time.
        Set the User's BookedSpot to the spot number, set the spot's occupancy to true (call your carArrived method)
        Returns: boolean that is true if the operation worked, false if there was a problem (spot not available ect)
        */
        /*
        public boolean bookingTimeOut(String spot, float timeBooked)
        Parameters: timeBooked is when the user booked their spot, spot is the spot number (this spot COULD not have a time booked (=null)
        Checks if currentTime - timeBooked >=10 minutes
        Returns boolean if the spot should be unbooked (set occupancy of spot to 0)
        */
	public void main(String args[]) {
		LotDB db = new LotDB();
		db.printAll();
	}

}
