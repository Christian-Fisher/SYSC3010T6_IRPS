

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {

// those are the columns used in our database 
// each user has pin , license plate 
// the Occupancy tells us if the spot is booked or not
// the array are used to return all the PINS , user names and Licenses plate in the get methods.
    int PIN1;
    int Occupancy;
    String usernameDB;
    String LicensePlate;
    String[] userNames; 
    String[] LicensePlates;
    int[] pins;
    Boolean[] array;


// This method connects this java program to the SQL database for validity, checking and editing purposes
    private Connection connect() {
        String url = "jdbc:sqlite:C:\\Users\\Akash\\Desktop\\Important\\Fall 2019\\SYSC 3010\\SYSC3010T6_IRPS\\SYSC3010T6_IRPS\\Database\\IRPSDatabase.db";
        Connection connect = null;
        //PIN1 = new Integer[10];
        userNames = new String[10];
        LicensePlates = new String[10];
        pins = new int[10];
        try {
            connect = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connect;
    }
// This method prints all the database columns from Users table where the Users table has columns Username, PIN, LicensePlate, and BookedSpot.
    public void printAll() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Username") + "\t"
                        + rs.getInt("PIN") + "\t"
                        + rs.getString("LicensePlate") + "\t"
                        + rs.getInt("BookedSpot"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
// This method gets all the PINS from the Users table of the database.
    public int[] getPINs() {
        int i = 0;
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pins[i] = rs.getInt("PIN");
                System.out.println(pins[i]);
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pins;
    }
// This method gets all the user names from the Users table of the database.

    public String[] getNames() {
        int i = 0;
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                userNames[i] = rs.getString("Username");
                System.out.println("The usernames present in the database are " + userNames[i]);
                i++;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userNames;
    }

// This method gets all the License Plate from the Users table of the database.
    public String[] getLicensePlate() {
        int i = 0;
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LicensePlates[i] = rs.getString("LicensePlate");
                System.out.println("The License Plates present in the database are " + LicensePlates[i]);
                i++;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return LicensePlates;
    }
/* This method checks in the given pin from the app if it exits in our database. 
* it also checks if the pin has wrong characters.
* it returns true if exits.
*@ param pin : int, Gets the pin from the app */

    public boolean PINexists(int pin) {

        if (pin <= 9999 && pin >= 1000) {
            if (pin != '@' || pin != '£' || pin != '$' || pin != '!' || pin != '%' || pin != '^') {
                String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
                try (Connection conn = this.connect();
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        PIN1 = rs.getInt("PIN");
                        if (pin == PIN1) {
                            System.out.println("Valid PIN");
                            return true;
                        } else {
                            System.out.println("Invalid PIN");
                            return false;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }
        }
        return false;

    }

/* This methods checks the length of the pin and returns true if its valid.
*  @param pin : int, Gets the pin from the app*/

    public boolean validPINlength(int pin) {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PIN1 = pin;
                if (PIN1 <= 9999 && PIN1 >= 1000) {
                    System.out.println("Valid Pin length");
                    return true;
                } else {
                    System.out.println("Invalid pin length");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

/* this method checks the format of the License PLate and return true if it has the right format else returns false.
* linecse plate should have 4 lecters first and 3 numbers after.
* @param plateNumber: String */

    public boolean validLicensePlate(String plateNumber) {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LicensePlate = plateNumber;
                for (int i = 0; i < LicensePlate.length(); i++) {
                    if (i <= 4) {
                        Character ch = LicensePlate.charAt(i);
                        if (!(Character.isAlphabetic(ch))) {
                            System.out.println("licence have a wrong digit or character");
                            return false;
                        }
                    }
                    if (i < 4) {
                        Character ch = LicensePlate.charAt(i);
                        if (!(Character.isDigit(ch))) {
                            System.out.println("licence have a wrong digit or character");
                            return false;
                        }
                    }
                }
                System.out.println("Good License plate");
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
// This method checks the format of the user name and return true if it has the right format else returns false.

    public boolean validUsernameFormat() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("Username");

                System.out.println("Good Username format");
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

// This method checks the length and format of the PIN and return true if it has the right format else returns false.

    public boolean checkPINcharacter() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            int pin = rs.getInt("PIN");
            while (rs.next()) {
                if (pin <= 9999 && pin >= 1000) {
                    if (pin != '@' || pin != '£' || pin != '$' || pin != '!' || pin != '%' || pin != '^') {
                        System.out.println("Valid PIN characters");
                        return true;
                    } else {
                        System.out.println("Invalid PIN characters");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
/* Checks if the given license from the app is registered or not in our database.
* if it exits it retruns true
* @param license: String */

    public boolean claimedLicensePlate(String license) {
        int i = 0;
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LicensePlates[i] = rs.getString("LicensePlate");
                if (license.equals(LicensePlates[i])) {
                    System.out.println("Correct claimed plate");
                    i++;
                    return true;
                } else {
                    System.out.println("Incorrect claimed plate");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
// Checks if the database is empty or not. return true if the data is not empty else returns false which means the database is empty.

    public boolean checkDatabaseEmpty() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (sql != null) {
                    System.out.println("Data Exists");
                    return true;
                } else {
                    System.out.println("Empty Table");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
//this methods checks all the spots if they are available or not 
// this returns an array with each spot avilability, true for avilable and false for not avilable. 

    public Boolean[] testAllSpots() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        array = new Boolean[10];
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String bookedSpot = rs.getString("BookedSpot");
                for (int i = 0; i < 9; i++) {
                    if (array[i] != null) {
                        if (bookedSpot.equals("A1")) {
                            System.out.println("A1 is not avalable");
                            array[0] = false;
                        } else {
                            array[0] = true;
                        }
                        if (bookedSpot.equals("A2")) {
                            System.out.println("A2 is not avalable");
                            array[1] = false;
                        } else {
                            array[1] = true;
                        }
                        if (bookedSpot.equals("A3")) {
                            System.out.println("A3 is not avalable");
                            array[2] = false;
                        } else {
                            array[2] = true;
                        }
                        if (bookedSpot.equals("B1")) {
                            System.out.println("B1 is not avalable");
                            array[3] = false;
                        } else {
                            array[3] = true;
                        }
                        if (bookedSpot.equals("B2")) {
                            System.out.println("B2 is not avalable");
                            array[4] = false;
                        } else {
                            array[4] = true;
                        }
                        if (bookedSpot.equals("B3")) {
                            System.out.println("B3 is not avalable");
                            array[5] = false;
                        } else {
                            array[5] = true;
                        }
                        if (bookedSpot.equals("C1")) {
                            System.out.println("C1 is not avalable");
                            array[6] = false;
                        } else {
                            array[6] = true;
                        }
                        if (bookedSpot.equals("C2")) {
                            System.out.println("C2 is not avalable");
                            array[7] = false;
                        } else {
                            array[7] = true;
                        }
                        if (bookedSpot.equals("C3")) {
                            System.out.println("C3 is not avalable");
                            array[8] = false;
                        } else {
                            array[8] = true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return array;
    }
/* Checks if the given user name exits in our database and retrun true if it does else returns false. 
*  @param userName: String */

    public boolean userNameExists(String userName) {
        for (int i = 0; i < userName.length(); i++) {
            Character ch = userName.charAt(i);
            if (!(ch == '%' || ch == '@' || ch == '+' || ch == '*' || ch == '/' || Character.isDigit(ch) || Character.isAlphabetic(ch))) {
                System.out.println("Wrong user name format");
                return false;
            }
        }
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usernameDB = rs.getString("Username");
                if (usernameDB.equals(userName)) {
                    System.out.println("Username Exists");
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }
 //Prints the Lot table of the parking database data such as the spot number and Occupancy and booktime.

    public void printAllLot() {
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("SpotNumber") + "\t"
                        + rs.getInt("Occupancy") + "\t"
                        + rs.getFloat("BookTime"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

// This method gets the lot Occupancy and returns it in an array form that has String that describes each spot ocupancy 
// whether it is occupied or not 


    public String[] getLotOccupancy() {
        String[] spotArray;
        spotArray = new String[10];
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String spotNumber = rs.getString("SpotNumber");
                Occupancy = rs.getInt("Occupancy");
                for (int i = 0; i > 9; i++) {
                    if (spotArray[i] != null) {
                        if (spotNumber.equals("A1") && Occupancy == 1) {
                            spotArray[0] = "A1 Spot Occupied";
                        } else {
                            spotArray[0] = "A1 Spot not Occupied";
                        }
                        if (spotNumber.equals("A2") && Occupancy == 1) {
                            spotArray[1] = "A2 Spot Occupied";
                        } else {
                            spotArray[1] = "A2 Spot  not Occupied";
                        }
                        if (spotNumber.equals("A3") && Occupancy == 1) {
                            spotArray[2] = "A3 Spot Occupied";
                        } else {
                            spotArray[2] = "A3 Spot  not Occupied";
                        }
                        if (spotNumber.equals("B1") && Occupancy == 1) {
                            spotArray[3] = "B1 Spot Occupied";
                        } else {
                            spotArray[3] = "B1 Spot  not Occupied";
                        }
                        if (spotNumber.equals("B2") && Occupancy == 1) {
                            spotArray[4] = "B2 Spot Occupied";
                        } else {
                            spotArray[4] = "B2 Spot  not Occupied";
                        }
                        if (spotNumber.equals("B3") && Occupancy == 1) {
                            spotArray[5] = "B3 Spot Occupied";
                        } else {
                            spotArray[5] = "B3 Spot  not Occupied";
                        }
                        if (spotNumber.equals("C1") && Occupancy == 1) {
                            spotArray[6] = "C1 Spot Occupied";
                        } else {
                            spotArray[6] = "C1 Spot  not Occupied";
                        }
                        if (spotNumber.equals("C2") && Occupancy == 1) {
                            spotArray[7] = "C2 Spot Occupied";
                        } else {
                            spotArray[7] = "C2 Spot  not Occupied";
                        }
                        if (spotNumber.equals("C3") && Occupancy == 1) {
                            spotArray[8] = "C3 Spot Occupied";
                        } else {
                            spotArray[8] = "C3 Spot  not Occupied";
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return spotArray;
    }

/* This method inserts the given Occupancy to a given spot number into the Lot table of the database. 
*  @param spotNumDB: String
*  @param occupancyDB: String */

    public void insertOccupancy(String spotNumDB, int occupancyDB) {
        String sql = "INSERT INTO Lot(spotNum,occupancy,data) VALUES(?,?)";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ((PreparedStatement) rs).setString(1, spotNumDB);
            ((PreparedStatement) rs).setInt(2, occupancyDB);
            //((PreparedStatement) rs).setFloat(3,time);
            ((PreparedStatement) rs).executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/* This method is used to update the occupancy where it updates the lot table of the database directly
* @param spot: String
* @param occupancy: boolean */

    public void changeOccupancy(String spot, boolean occupancy) {
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                //String	spotdb = rs.getString("SpotNumber");
                int occupant = (occupancy) ? 1 : 0;
                //occupant = rs.getInt("Occupancy");
                if (occupant == 1) {
                    if (spot.equals("A1")) {
                        //System.out.println("A1 spot is occupied");
                        insertOccupancy("A1", 1);
                    } else if (spot.equals("A2")) {
                        //System.out.println("A2 spot is occupied");
                        insertOccupancy("A2", 1);
                    } else if (spot.equals("A3")) {
                        //System.out.println("A3 spot is occupied");
                        insertOccupancy("A3", 1);
                    } else if (spot.equals("B1")) {
                        //System.out.println("B1 spot is occupied");
                        insertOccupancy("B1", 1);
                    } else if (spot.equals("B2")) {
                        //System.out.println("B2 spot is occupied");
                        insertOccupancy("B2", 1);
                    } else if (spot.equals("B3")) {
                        //System.out.println("B3 spot is occupied");
                        insertOccupancy("B3", 1);
                    } else if (spot.equals("C1")) {
                        //System.out.println("C1 spot is occupied");
                        insertOccupancy("C1", 1);
                    } else if (spot.equals("C2")) {
                        //System.out.println("C2 spot is occupied");
                        insertOccupancy("C2", 1);
                    } else if (spot.equals("C3")) {
                        //System.out.println("C3 spot is occupied");
                        insertOccupancy("C3", 1);
                    } else {
                        System.out.println("Invalid");
                    }
                } else {
                    if (spot.equals("A1")) {
                        //System.out.println("A1 spot is not occupied");
                        insertOccupancy("A1", 0);
                    } else if (spot.equals("A2")) {
                        //System.out.println("A2 spot is not occupied");
                        insertOccupancy("A2", 0);
                    } else if (spot.equals("A3")) {
                        //System.out.println("A3 spot is not occupied");
                        insertOccupancy("A3", 0);
                    } else if (spot.equals("B1")) {
                        //System.out.println("B1 spot is not occupied");
                        insertOccupancy("B1", 0);
                    } else if (spot.equals("B2")) {
                        //System.out.println("B2 spot is not occupied");
                        insertOccupancy("B2", 0);
                    } else if (spot.equals("B3")) {
                        //System.out.println("B3 spot is not occupied");
                        insertOccupancy("B3", 0);
                    } else if (spot.equals("C1")) {
                        //System.out.println("C1 spot is not occupied");
                        insertOccupancy("C1", 0);
                    } else if (spot.equals("C2")) {
                        //System.out.println("C2 spot is not occupied");
                        insertOccupancy("C2", 0);
                    } else if (spot.equals("C3")) {
                        //System.out.println("C3 spot is not occupied");
                        insertOccupancy("C3", 0);
                    } else {
                        System.out.println("Invalid");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/* This method inserts spot number , occupancy and the time booked into the lot table of the database
*  @param spotNum: String,
*  @param occupancy: int,
*  @param time: float */

    public void insertQuery(String spotNum, int occupancy, float time) {
        String sql = "INSERT INTO Lot(spotNum,occupancy,data) VALUES(?,?,?)";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ((PreparedStatement) rs).setString(1, spotNum);
            ((PreparedStatement) rs).setInt(2, occupancy);
            ((PreparedStatement) rs).setFloat(3, time);
            ((PreparedStatement) rs).executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

// this method books the given spot and assign it to the given user from the APP in the database.


    public boolean bookSpot(String spot, String User) {
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                //Float BookTime = rs.getFloat("BookTime");
                float BookTime = System.currentTimeMillis();
                float sec2 = BookTime / 1000F;
                float min2 = sec2 / 60F;
                insertQuery(spot, 0, min2);

                if (min2 < 10) {
                    changeOccupancy(User, true);
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

// this method checks if the booking time is more than 10 mins and the user didnt showe up so we unbook the spot. it retrun true if the time is still with in the 10 mins period

    public boolean bookingTimeOut(String spot) {
        String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
        // finding the time before the operation is executed
        long currentTime = System.currentTimeMillis();
        float timeBooked;
        // converting it into seconds
        float sec = currentTime / 1000F;
        // converting it into minutes
        float minutes = sec / 60F;
        System.out.println(minutes + " minutes");
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                spot = rs.getString("SpotNumber");
                timeBooked = rs.getFloat("BookTime");
                float sec1 = timeBooked / 1000F;
                float minute1 = sec1 / 60F;
                Occupancy = rs.getInt("Occupancy");
                if ((minutes - minute1) >= 10) {
                    Occupancy = 0;
                    System.out.println("Unbook the spot");
                    return false;
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    //Bookedspot is from 0 - 8
    //if spotnumber = booked occupied  =1 

  
	public boolean bookSpot(String spot, String User) {
		String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
		try(Connection conn = this.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()) {
				float BookTime = System.currentTimeMillis();
				float sec2 = BookTime/1000F;
				float min2 = sec2/60F;
				insertQuery(spot,0,min2);
			
				if(min2 < 10) {
				changeOccupancy(User,true);
				return true;
				}else {
					return false;
				}
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	public boolean bookingTimeOut(String spot, float timeBooked) {
		String sql = "SELECT SpotNumber, Occupancy, BookTime FROM Lot";
		// finding the time before the operation is executed
	      long currentTime = System.currentTimeMillis();
	      // converting it into seconds
	      float sec= currentTime/1000F;
	      // converting it into minutes
	      float minutes=sec/60F;
	      System.out.println(minutes + " minutes");
		try(Connection conn = this.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()) {
				spot = rs.getString("SpotNumber");
				timeBooked = rs.getFloat("BookTime");
				float sec1 = timeBooked/1000F;
				float minute1 = sec1/60F;
				Occupancy = rs.getInt("Occupancy");
				if((minutes - minute1) >= 10) {
					Occupancy = 0;
					System.out.println("Unbook the spot");
					return false;
				}
				return true;
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	//Bookedspot is from 0 - 8
	//if spotnumber = booked occupied  =1 

	public static void main1(String[] args) {

        Database db = new Database();
        db.printAll();
        db.getPINs();
        db.getNames();
        db.getLicensePlate();
        db.PINexists(1111);
        db.validPINlength(1111);
        db.validLicensePlate("QWER111");
        db.validUsernameFormat();
        db.checkPINcharacter();
        db.claimedLicensePlate("QWER111");
        db.checkDatabaseEmpty();
        db.userNameExists("User");
        db.testAllSpots();
        db.getLotOccupancy();
        db.bookingTimeOut("", 123124324);
        db.bookSpot("", "");
        db.changeOccupancy("", true);
        db.printAllLot();
    }

}
