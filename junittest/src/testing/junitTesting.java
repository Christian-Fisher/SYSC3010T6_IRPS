package testing;

public class junitTesting {

	
	public String[] PIN = { "1234", "1114", "5555"} ;
	public String []password={"pass1" , "pass2" , "pass3"} ; 
	public  String []claimedlicenseplate ;
	public String []licenseplate= {"abcd123", "abcd111", "abcd222"};
	public String []availableSpots={"a1","a2", "a3"};
	
	
	public boolean testLicensePlate(String LicensePlate ) {  // i changed this to boolean 
		
		String a = LicensePlate;
		
		for (int i=0; i < a.length(  ); i++) {
			
			
			if(i < 4) {
		    System.out.println("Char " + i + " is " + a.charAt(i));
		    
		    Character ch =  a.charAt(i);
		    
		if (!(  Character.isAlphabetic(ch) ) )  {
			
			System.out.println("licence have a wrong digit or character");
			
			return false;
		
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
		
		
		return true ;
	}
	
	public boolean testUsernameFormat(String Username ) {
			
			
			
			
			//The user can use characters: - ‘A, a,1, %@, +, -,*,/’
			
			
			
			String a = Username;
			
			for (int i=0; i < a.length(  ); i++) {
			
				  Character ch =  a.charAt(i);
				  
				if (! ( ch == '%' ||    ch =='@' ||    ch=='+' ||  ch=='*' ||  ch== '/' || Character.isDigit(ch) ||  Character.isAlphabetic(ch)     )) {
					
					System.out.println("wrong formate surename");
					
					return false; 
				}
				
	
			}
			
			
			
			return true;
			
			
			
		}


	public boolean testPINformat(String PIN) {
		
		
		String a = PIN;
		
		for (int i=0; i < a.length(  ); i++) {
			
			 Character ch =  a.charAt(i);
			 
			if( !(Character.isDigit(ch) ||  a.charAt(i) == 'A'  || a.charAt(i) == 'B' || a.charAt(i) == 'C' ||a.charAt(i) == 'D' ) ){
				
				return false;
			
			}
			
			
			}
		
		
		
		return true;
		
		
		
	}
		public boolean testInvalidUsernameFormat(String username) {  
				 
				
				String a = username;
				
				int count = 0 ;
				 // loulia@
				
				for (int i=0; i < a.length(  ); i++) {
					
					 Character ch =  a.charAt(i);
					 
					 if ( ( ch == '%' ||    ch =='@' ||    ch=='+' ||  ch=='*' ||  ch== '/' || Character.isDigit(ch) ||  Character.isAlphabetic(ch)     )) {
							
							System.out.println("valid" + i);
							
							count ++;
							
							if(count == a.length()) {
							return false; 
							}
						}
						
				}
				
				return true;
			}
	
		public boolean testPINformatLength(String PIN) {   //int should b e int or String  // done testing 
					
			String a = PIN;
					
						
				if (a.length() > 8 ) {
					
					System.out.println("invald pin legth");
					return false; 
				
				}
				
				
				return true ;
				}
		
		public boolean Pinexists(String PIN ) {
			
			
			
			 String[] PINin = { "1234", "1114", "5555"} ;
			 for (int i=0 ; i < PINin.length ; i++) {
			if(    PINin[i].equals(PIN)   ) {
				
				return true;
			}
			
			 }
			return false;
		}
		
	public String ClaimedLicensePlate(String plate) {
		
		 String []licenseplate= {"abcd123", "abcd111", "abcd222"};
		
		 for (int i=0 ; i < licenseplate.length ; i++) {
				if(    licenseplate[i].equals(plate)   ) {
					
					return "registered";
				}
				
		
		
	}
		 return "not registered "; 
		 
	}
	
	public boolean testAvailableSpots( String spot) {
		
		 String []availableSpots={"a1","a2", "a3"};
		
		 for (int i=0 ; i < availableSpots.length ; i++) {
			 
				if(  (  availableSpots[i].equals(spot)  ) ) {
			
			
			return true;
		}
		
		
		
	}
		 return false ;
  }
	
	public boolean testinAvailableSpots( String spot) {
		
		 String []availableSpots={"a1","a2", "a3"};
		
		 for (int i=0 ; i < availableSpots.length ; i++) {
			 
				if(  (  availableSpots[i].equals(spot)  ) ) {
			
			
			return false;
		}
		
		
		
	}
		 return true ;
 }
	
	
	public boolean ValidPassword(String user ,String password) {   // to be done 
		
		// compare  the pass of  user to pass from database  
		//if( the user has this password           ){    we return true  
	
		return true;
		
		//else return false ;
	}
	
	public boolean InvalidPassword(String user ,String password) {   // to be done 
			
			// compare  the pass of  user to pass from database  
			//if( the user has this password           ){    we return true  
		
			return false;
			
			//else return true ;
		}
	
	
		public boolean testDatabaseEmpty() {   // to be done 
			
			//check the rows and the cloms or check for null if yes return true otherwise return false
			
			return true;
		}
		
	public boolean testInvalidPin(String user ,String pin) {   // to be done 
				
				// compare  the pin of  user to pin from database  
				//if( the user has this pin           ){    we return false  
			
				return false;
				
				//else return true ;
			}
	public boolean ValidPin(String user ,String pin) {   // to be done 
			
			// compare  the pin of  user to pin from database  
			//if( the user has this pin           ){    we return true  
		
			return true;
			
			//else return false ;
		}
	
	public boolean testBooking( String Spot , int time  ) { //check how we are going to code the time   to be done 
		
		// wait for 10 mins then check with the database if the spot is available or not 
		// we can call the avilable spots methods 
		
		return true ;
	}
	
	public boolean testOverTimeOut() {
		
		
	return true;	
		
	}
	public boolean testLessTimeOut() {
		
		
		return true;	
			
		}
		
	public boolean testTimeOut() {
			
			
			return true;	
				
			}
	public String testSystemStatue() {
		
		
		return "full";
	} 
	
	public String historyStatue() {
			
			
			return "full";
		} 
}
