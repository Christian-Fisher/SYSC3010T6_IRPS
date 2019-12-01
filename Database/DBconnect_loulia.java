package testing;

import java.sql.*;

public class DBconnect_loulia {
	
	private Connection connect;
	private Statement state;
	private ResultSet set;
	//String Student_Number;
	String [] UserName;
	String []  PIN;
	String [] License_Plate;
	String [] Lot_Number;
	String  PIN1;
	String  License_Plate1;
	//String  Lot_Number1;
	String Spot_Number;
	String Occupany;
	String UserName1;
	String []Password;
	String Password1;

	//String bookedSpot; work on this 
	
	
	
	
	
	
	DBconnect_loulia() {
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
				UserName1 = set.getString("Student Name");
				PIN1 = set.getString("PIN");
				Password1=set.getString("Password");
				License_Plate1 = set.getString("License Plate");
		//		Lot_Number1 = set.getString("Lot Number");
				Spot_Number = set.getString("Spot Number");
				Occupany = set.getString("Occupany");
				System.out.println("Student Number: "+UserName1+" "+"PIN: "+PIN+" "+"License Plate: "+License_Plate+" "+"Lot Number: "+Lot_Number+" "+"Spot Number: "+Spot_Number+" "+"Occupany: "+Occupany);
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public String[] getPasswords( ) {
		int i =0;
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				Password[i] = set.getString("Password");
				i++;
			}
		
	}catch(Exception ex) {
		System.out.println(ex);
	}
		return Password;
	}
	
	public String[] getPINs( ) {
		int i =0;
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				PIN[i] = set.getString("PIN");
				i++;
			}
		
	}catch(Exception ex) {
		System.out.println(ex);
	}
		return PIN;
	}
	
	public String[] getLicense_Plates( ) {
		int i=0;
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				License_Plate[i]  = set.getString("License Plate");
				i++;
			}
		
	}catch(Exception ex) {
		System.out.println(ex);
	}
		return License_Plate;
	}
	
	
	public String[] getNames( ) {
		try {
			int i =0;
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				UserName[i]  = set.getString("Student Name");
				i++;
			}
		
	}catch(Exception ex) {
		System.out.println(ex);
	}
		return UserName;
	}
	
	
	
	
	
	public boolean validPinLength(String PIN ) {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
			//	String PIN = set.getString("PIN");
				int length;
				length = PIN.length();
				if(length == 4) {
					System.out.println("Valid PIN length");
					return true;
				} else {
					System.out.println("Invalid length, Please try again");
					return false;
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
		
	}
	
	boolean validPlate;
	public boolean validLicensePlate() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String License_Plate = set.getString("License Plate");
				String a = License_Plate;
				for (int i=0; i < a.length(  ); i++) {
					if(i < 4) {
				    System.out.println("Char " + i + " is " + a.charAt(i));
				    Character ch =  a.charAt(i);
				    if (!(  Character.isAlphabetic(ch) ) )  {
					System.out.println("licence have a wrong digit or character");
					return  false;
				    }
					}if(i >= 4){
						System.out.println("Char " + i + " is " + a.charAt(i));
					    Character ch =  a.charAt(i);    
					if (! ( Character.isDigit(ch))    )  {
						System.out.println("licence have a wrong digit ");
						return false;
					}		
					}
				}
				validPlate = true ;
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return true;
	}
	
	
	public boolean validLicensePlateLength() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String License_Plate = set.getString("License Plate");
				int licensePlateLength;
				licensePlateLength = License_Plate.length();
				if(licensePlateLength == 7) {
					System.out.println("Valid License Plate length");
					return true;
				} else {
					System.out.println("Invalid License Plate length");
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	boolean valid;
	public void validPinCharacter() {  // we should take it off if using only numbers
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
					}
				}
				System.out.println("Valid four digit PIN");
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	boolean validName;
	
	public void testUserNameFormat() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String Student_Number = set.getString("Student Number");
				String a = Student_Number;
				for (int i=0; i < a.length(  ); i++) {
				   Character ch =  a.charAt(i);
				if (! ( ch == '%' ||    ch =='@' ||    ch=='+' ||  ch=='*' ||  ch== '/' || Character.isDigit(ch) ||  Character.isAlphabetic(ch))) {
					System.out.println("wrong formate surename");
					validName = false; 
				}
			}
			validName = true;
			System.out.println("Good Username format");
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	//Check for UDP connection
	public boolean pinExists( String PIN) {  // this methods  can use get pins 
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String PINdb = set.getString("PIN");
				if(PINdb.equals(PIN)) {
					System.out.println("Pin Exists");
					return true;
				} else {
                    return false;
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		 return false;
	}
	//udp
	public boolean claimedLicensePlate( String LicensePlateapp ) {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String License_Plate = set.getString("License Plate");
				if(License_Plate.equals(LicensePlateapp)) {
					System.out.println("Plate Exists");
					return true;
				} else {
			return false;
				}
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	public boolean validPassword( String password) {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String password1 = set.getString("Password");
				if(password1.equals(password)) {
				
					System.out.println("Password Exists");
					return true;
				} else {
				System.out.println( "doesnt Exists");
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	boolean status;
	public boolean testDatabaseEmpty() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				if((UserName1) != null) {
				System.out.println("Data Exists");
				status = true;
				return true;
			  } else {
			status = false;
			System.out.println("Empty Table");
			  }
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return false;
	}
	
	public void testSystemStatus() {
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String Lot_Number = set.getString("Lot Number");
				//int count;
				if(Lot_Number == " ") {
					System.out.println("Parking is now full");
				}
			}
			System.out.println("Parking is not full");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public boolean[] testAvailableSpots() {
		boolean [] array = null;
		try {
			String query = "select * from IRPS";
			set = state.executeQuery(query);
			while(set.next()) {
				String Spot_Number = set.getString("Spot Number");
				if(Spot_Number.equals("A1")) {
					System.out.println("A1 is not avalable");
					array[0]= false;
				}
				else {
					array[0]= true;
				}
				if(Spot_Number.equals("A2")) {
					System.out.println("A2 is not avalable");
					array[1]= false;
				}
				else {
					array[1]= true;
				}
				if(Spot_Number.equals("A3")) {
					System.out.println("A3 is not avalable");
					array[2]= false;
				}
				else {
					array[2]= true;
				}
				if(Spot_Number.equals("B1")) {
					System.out.println("B1 is not avalable");
					array[3]= false;
				}
				else {
					array[3]= true;
				}
				if(Spot_Number.equals("B2")) {
					System.out.println("B2 is not avalable");
					array[4]= false;
				}
				else {
					array[4]= true;
				}
				if(Spot_Number.equals("B3")) {
					System.out.println("B3 is not avalable");
					array[5]= false;
				}
				else {
					array[5]= true;
				}
				if(Spot_Number.equals("C1")) {
					System.out.println("C1 is not avalable");
					array[6]= false;
				}
				else {
					array[6]= true;
				}
				if(Spot_Number.equals("C2")) {
					System.out.println("C2 is not avalable");
					array[7]= false;
				}
				else {
					array[7]= true;
				}
				if(Spot_Number.equals("C3")) {
					System.out.println("C3 is not avalable");
					array[8]= false;
				}else {
					array[8]= true;
				}
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return array;
	}
	

}

