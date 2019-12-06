package sqlTest;

import static org.junit.jupiter.api.Assertions.*;
import junit.framework.TestCase;

import org.junit.jupiter.api.Test;

class AllTests extends TestCase{

	@Test
	void testValidPinLength() {
		DBconnect connect = new DBconnect();
		connect.validPinLength();
		boolean answer = connect.validPinLength();
		assertEquals(true,answer);
	}
	
	@Test
	void testvalidLicensePlate() {
		DBconnect connect = new DBconnect();
		connect.validPinLength();
		boolean answer = connect.validLicensePlate();
		assertEquals(true,answer);
	}

	@Test
	void testvalidPinCharacter() {
		DBconnect connect = new DBconnect();
		connect.validPinCharacter();
		boolean answer = connect.validPinCharacter();
		assertEquals(true,answer);
	}
		
	@Test
	void testvalidLicensePlateLength() {
		DBconnect connect = new DBconnect();
		connect.validLicensePlateLength();
		boolean answer = connect.validLicensePlateLength();
		assertEquals(true,answer);
	}
	
	@Test
	void testtestUserNameFormat() {
		DBconnect connect = new DBconnect();
		connect.testUserNameFormat();
		boolean answer = connect.testUserNameFormat();
		assertEquals(true,answer);
	}

	@Test
	void testpinExists() {
		DBconnect connect = new DBconnect();
		connect.pinExists();
		boolean answer = connect.pinExists();
		assertEquals(true,answer);
	}

	
}
