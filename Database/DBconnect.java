package sqlTest;

import java.sql.*;

public class DBconnect {
	private Connection connect;
	private Statement state;
	private ResultSet set;
	
	
	DBconnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/IRPS","root","");
			state = connect.createStatement();
			
		}catch(Exception ex) {
			System.out.println("Error: "+ ex);
		}
	}
	
	public void getData() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			System.out.println("Records from database");
			while(set.next()) {
				String Student_Number = set.getString("Student Number");
				String PIN = set.getString("PIN");
				String License_Plate = set.getString("License Plate");
				String Lot_Number = set.getString("Lot Number");
				String Spot_Number = set.getString("Spot Number");
				String Occupany = set.getString("Occupany");
				System.out.println("Student Number: "+Student_Number+" "+"PIN: "+PIN+" "+"License Plate: "+License_Plate+" "+"Lot Number: "+Lot_Number+" "+"Spot Number: "+Spot_Number+" "+"Occupany: "+Occupany);
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void checkInputs() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				if(PIN.equals("2345")) {
					System.out.println("Pass");
				} else {
				System.out.println("Fail, the actual value is "+PIN);
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	public void validPinLength() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				int length;
				length = PIN.length();
				if(length == 4) {
					System.out.println("Valid PIN length");
				} else {
					System.out.println("Invalid length, Please try again");
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public void validLicensePlate() {
		try {
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	boolean valid;
	public void validPinCharacter() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				for(int i=0;i<PIN.length();i++) {
					char a = PIN.charAt(i);
					if(a == '/' ||a == '@' ||a == '%' ||a == '&' ||a == '$') {
						valid = false;
						System.out.println("Invalid Pin. Please enter a valid four digit PIN");
					} else {
						valid = true;
						System.out.println("Valid four digit PIN");
					}
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
	}

}
