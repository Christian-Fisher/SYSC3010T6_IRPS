import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	int PIN1;
	String LicensePlate;
	String[] userNames;
	String[] LicensePlates;
	
	private Connection connect() {
		String url = "jdbc:sqlite:C:\\Users\\Akash\\Desktop\\Important\\Fall 2019\\SYSC 3010\\SYSC3010T6_IRPS\\SYSC3010T6_IRPS\\Database\\IRPSDatabase.db";
		Connection connect = null;
		//PIN1 = new Integer[10];
		userNames = new String[10];
		LicensePlates = new String[10];
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
	
	public String[] getLicensePlate() {
		int i=0;
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
	        Statement stmt  = conn.createStatement();
	        ResultSet rs    = stmt.executeQuery(sql)) {
			while(rs.next()) {
				LicensePlates[i] = rs.getString("LicensePlate");
				System.out.println("The License Plates present in the database are "+LicensePlates[i]);
				i++;
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return LicensePlates;
	}
	
	public boolean checkPIN(int pin) {
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
		    Statement stmt  = conn.createStatement();
		    ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()) {
				PIN1 = pin;
				if(PIN1 == rs.getInt("PIN")) {
					System.out.println("Valid PIN");
					return true;
				}else {
					System.out.println("Invalid PIN");
					return false;
				}
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean validPINlength(int pin) {
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()) {
				PIN1 = pin;
				if(PIN1 <= 9999 && PIN1 >= 1000) {
					System.out.println("Valid Pin length");
					return true;
				}else {
					System.out.println("Invalid pin length");
					return false;
				}
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean validLicensePlate(String plateNumber) {
		String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
		try(Connection conn = this.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()) {
				LicensePlate = plateNumber;
				for (int i=0; i < LicensePlate.length(  ); i++) {
					if(i < 4) {
				    Character ch =  LicensePlate.charAt(i);
				    if (!(  Character.isAlphabetic(ch) ) )  {
					System.out.println("licence have a wrong digit or character");
					return false;
				    }
					}if(i >= 4){
					    Character ch =  LicensePlate.charAt(i);    
					if (! ( Character.isDigit(ch))    )  {
						System.out.println("licence plate have a wrong digit ");
						return false;
					}		
					}
				}
				System.out.println("Good License plate");
				return true;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	public static void main(String[] args) {
        Database db = new Database();
        db.printAll();
        db.getPINs();
        db.getNames();
        db.getLicensePlate();
        db.checkPIN(1234);
        db.validPINlength(1234);
        db.validLicensePlate("ABCD123");
    }

}
