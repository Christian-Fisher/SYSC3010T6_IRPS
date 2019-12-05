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
        String url = "jdbc:sqlite:home\\pi\\Desktop\\SYSC3010T6_IRPS\\Database\\IRPSDatabase.db";
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

    public boolean bookingTimeOut(String spot) {
        String sql = "SELECT BookTime FROM Lot WHERE SpotNumber = '" + spot +"';";
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
