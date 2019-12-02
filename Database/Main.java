package sqlTest;

public class Main {
	public static void main(String args[]) {
		DBconnect connect = new DBconnect();
		connect.getData();
		connect.checkInputs();
		connect.validPinLength();
		connect.validLicensePlate();
		connect.validPinCharacter();
		connect.validLicensePlateLength();
		connect.testUserNameFormat();
		connect.pinExists();
		connect.claimedLicensePlate();
		connect.validPassword();
		connect.testDatabaseEmpty();
		connect.testAvailableSpots();
	}

}
