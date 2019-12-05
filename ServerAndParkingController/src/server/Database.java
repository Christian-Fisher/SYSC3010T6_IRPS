package server;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {

    int PIN1;
    int Occupancy;
    String usernameDB;
    String LicensePlate;
    String[] userNames;
    String[] LicensePlates;
    int[] pins;
    Boolean[] array;

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

    public boolean PINexists(int pin) {
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return spotArray;
    }

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

    public void changeOccupancy(String spot, boolean occupancy) {
        String sql;
        if (occupancy) {
             sql = "UPDATE Lot SET Occupancy = '1' WHERE SpotNumber = '" + spot + "'  ;";
        } else {
             sql = "UPDATE Lot SET Occupancy = '0' WHERE SpotNumber = '" + spot + "'  ;";

        }
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public void setTime(String spot) {
        String sql= "UPDATE Lot SET BookTime = '" + System.currentTimeMillis()/60000+ "' WHERE SpotNumber = '" + spot + "';";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean bookSpot(String spot, String User) {
        
        changeOccupancy(spot, true);
        setTime(spot);
        
        String sql= "UPDATE Users SET  BookedSpot = '" + spot+ "' WHERE Users = '" + User + "';";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
       return true;
    }

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
        db.bookingTimeOut("1");
        db.bookSpot("", "");
        db.changeOccupancy("", true);
        db.printAllLot();
    }

}
