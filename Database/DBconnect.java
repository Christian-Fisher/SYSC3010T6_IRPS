package sqlTest;

import java.sql.*;

public class DBconnect {
	private Connection connect;
	private Statement state;
	private ResultSet set;
	String userName;
	String PIN;
	String Password;
	String licensePlate;
	String bookedSpot;
	
	
	DBconnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo","root","");
			state = connect.createStatement();
			
		}catch(Exception ex) {
			System.out.println("Error: "+ ex);
		}
	}
	
	public void getData() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			System.out.println("Records from User Info database");
			while(set.next()) {
				userName = set.getString("userName");
				PIN = set.getString("PIN");
				Password = set.getString("Password");
				licensePlate = set.getString("licensePlate");
				bookedSpot = set.getString("bookedSpot");
				System.out.println("Student Number: "+userName+" "+"PIN: "+PIN+" "+"Password: "+Password+" "+"License Plate: "+licensePlate+" "+"Booked Spot: "+bookedSpot+" ");
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void checkInputs() {
		try {
			String query = "select * from userinfo";
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
	
	
	public boolean validPinLength() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				int length;
				length = PIN.length();
				if(length == 4) {
					System.out.println("Valid PIN length");
					return true;
				} else {
					System.out.println("Invalid length, Please try again");
					
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	boolean validPlate;
	public boolean validLicensePlate() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String licensePlate = set.getString("licensePlate");
				String a = licensePlate;
				for (int i=0; i < a.length(  ); i++) {
					if(i < 4) {
				    Character ch =  a.charAt(i);
				    if (!(  Character.isAlphabetic(ch) ) )  {
					System.out.println("licence have a wrong digit or character");
					validPlate = false;
					return false;
				    }
					}if(i >= 4){
					    Character ch =  a.charAt(i);    
					if (! ( Character.isDigit(ch))    )  {
						System.out.println("licence plate have a wrong digit ");
						validPlate = false;
						return false;
					}		
					}
				}
				validPlate = true ;
				return true;

			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
	}
	
	
	public boolean validLicensePlateLength() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String licensePlate = set.getString("licensePlate");
				int licensePlateLength;
				licensePlateLength = licensePlate.length();
				if(licensePlateLength == 7) {
					System.out.println("Valid License Plate length");
					return true;
				} else {
					System.out.println("Invalid License Plate length");
					return false;
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	boolean valid;
	public boolean validPinCharacter() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				for(int i=0;i<PIN.length();i++) {
					char a = PIN.charAt(i);
					if(a == '/' ||a == '@' ||a == '%' ||a == '&' ||a == '$') {
						valid = false;
						System.out.println("Invalid Pin. Please enter a valid four digit PIN");
						return false;
					} else {
						valid = true;
						return true;
					}
				}
				System.out.println("Valid four digit PIN");
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
	}
	
	boolean validName;
	public boolean testUserNameFormat() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String userName = set.getString("userName");
				String a = userName;
				for (int i=0; i < a.length(  ); i++) {
				   Character ch =  a.charAt(i);
				if (! ( ch == '%' ||    ch =='@' ||    ch=='+' ||  ch=='*' ||  ch== '/' || Character.isDigit(ch) ||  Character.isAlphabetic(ch))) {
					System.out.println("Wrong user name format");
					validName = false;
					return false;
				}
			}
			validName = true;
			System.out.println("Good Username format");
			return true;
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
	}
	//Check for UDP connection
	public boolean pinExists() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String PIN = set.getString("PIN");
				if(PIN.equals("2345")) {
					System.out.println("Pin Exists");
					return true;
				} else {
				System.out.println("Fail, the actual value is "+PIN);
				return false;
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	public void claimedLicensePlate() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String licensePlate = set.getString("License Plate");
				if(licensePlate.equals("ABCD452")) {
					System.out.println("Plate Exists");
				} else {
				System.out.println("Fail, the actual plate number is "+licensePlate);
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void validPassword() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String Password = set.getString("Password");
				if(Password.equals("127")) {
					System.out.println("Password Exists");
				} else {
				System.out.println("Fail, the actual plate number is "+Password);
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	boolean status;
	public void testDatabaseEmpty() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				if((query) != null) {
				System.out.println("Data Exists");
				status = true;
			  } else {
			status = false;
			System.out.println("Empty Table");
			  }
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	public void testAvailableSpots() {
		try {
			String query = "select * from userinfo";
			set = state.executeQuery(query);
			while(set.next()) {
				String bookedSpot = set.getString("bookedSpot");
				if(bookedSpot.equals("A1")) {
					System.out.println("A1 is not available");
				}
				if(bookedSpot.equals("A2")) {
					System.out.println("A2 is not available");
				}
				if(bookedSpot.equals("A3")) {
					System.out.println("A3 is not available");
				}
				if(bookedSpot.equals("B1")) {
					System.out.println("B1 is not available");
				}
				if(bookedSpot.equals("B2")) {
					System.out.println("B2 is not available");
				}
				if(bookedSpot.equals("B3")) {
					System.out.println("B3 is not available");
				}
				if(bookedSpot.equals("C1")) {
					System.out.println("C1 is not available");
				}
				if(bookedSpot.equals("C2")) {
					System.out.println("C2 is not available");
				}
				if(bookedSpot.equals("C3")) {
					System.out.println("C3 is not available");
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}

}
