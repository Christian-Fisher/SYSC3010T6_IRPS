package server;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
// those are the coloms used in our database 
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

// here we connect the class database to our Sql with a link to the location were it is saved.
    private Connection connect() {
        String url = "jdbc:sqlite:IRPSDatabase.db";
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
// this method prints all the database coloms Username, PIN, LicensePlate, BookedSpot.
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
            System.out.println("print failed");
        }
    }

// this method checks in the given pin from the app if it exits in our database. 
// it returns true if exits.

    public boolean PINexists(int pin) {
        String sql = "SELECT PIN FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PIN1 = rs.getInt("PIN");
                if (pin == PIN1) {
                    System.out.println("Valid PIN");
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

// this methods checks the length of the pin and returns true if its valid.

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

// this method checks the format of the License PLate and return true if it has the right format.
// linecse plate should have 4 lecters first and 3 numbers after.

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
// this method checks the format of the user name and return true if it has the right format.

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
//this method return true if is doesnt contain invalid characters
    public boolean checkPINcharacter() {
        String sql = "SELECT Username, PIN, LicensePlate, BookedSpot FROM Users";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            int pin = rs.getInt("PIN");
            while (rs.next()) {
                if (pin <= 9999 && pin >= 1000) {
                    if (pin != '@' || pin != '�' || pin != '$' || pin != '!' || pin != '%' || pin != '^') {
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

// checks if the given license from the app is registered or not in our database.
// if it exits it retruns true

    public boolean claimedLicensePlate(String license) {
        String sql = "SELECT LicensePlate FROM Users WHERE LicensePlate = '" + license + "';";
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
// checks if the database is empty or not. return true is the data is not empty


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

// checks if the given user name exits in our database and retrun true if it does.
// checks if the user name has the right format.

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
//print the parking database data such as the spot number and Occupancy and booktime.

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
            System.out.println("Print lot failed");
        }
    }
// this method get the lot Occupancy and retruns it in an array that has String that discribe each spot ocupancy 

    public String[] getLotOccupancy() {
        String[] spotArray;
        spotArray = new String[9];
        String sql = "SELECT Occupancy FROM Lot";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                spotArray[i] = rs.getString("Occupancy");
                i++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("getLotOccupancy failed");
        }
        return spotArray;
    }

// this method inserts the given Occupancy to a given spot number into the database. 

    public void changeOccupancy(String spot, boolean occupancy) {
        String sql;
        if (occupancy) {
            sql = "UPDATE Lot SET Occupancy = 1 WHERE SpotNumber = '" + spot + "';";
        } else {
            sql = "UPDATE Lot SET Occupancy = 0 WHERE SpotNumber = '" + spot + "';";

        }
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("changeOccupancy failed");
        }
    }

// this method set the booking time for the spot 

    public void setTime(String spot, boolean changeToNull) {
        String sql;
        if (changeToNull) {
            sql = "UPDATE Lot SET BookTime = '" + null + "' WHERE SpotNumber = '" + spot + "';";

        } else {
            sql = "UPDATE Lot SET BookTime = '" + System.currentTimeMillis() / 60000 + "' WHERE SpotNumber = '" + spot + "';";

        }
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
// this method update database by adding the new booked spot and the user who booked it 
    public boolean bookSpot(String spot, String User) {
        changeOccupancy(spot, true);
        setTime(spot, false);
        String sql = "UPDATE Users SET  BookedSpot = '" + spot + "' WHERE Username = '" + User + "';";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("book spot failed");
        }
        return true;
    }
// // this method checks if the booking time is more than 10 mins and the user didnt showe up so we unbook the spot. it retrun true if the time is still with in the 10 mins period
    public boolean bookingTimeOut(String spot) {
        String sql = "SELECT BookTime FROM Lot WHERE SpotNumber = '" + spot + "';";
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
                timeBooked = rs.getFloat("BookTime");
                float sec1 = timeBooked / 1000F;
                float minute1 = sec1 / 60F;
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

    public static void main(String[] args) {
        Database db = new Database();
        db.printAll();
   
        db.PINexists(1111);
        db.validPINlength(1111);
        db.validLicensePlate("QWER111");
        db.validUsernameFormat();
        db.checkPINcharacter();
        db.claimedLicensePlate("QWER111");
        db.checkDatabaseEmpty();
        db.userNameExists("User");
        db.getLotOccupancy();
        db.bookingTimeOut("1");
        db.bookSpot("", "");
        db.changeOccupancy("", true);
        db.printAllLot();
    }

}
