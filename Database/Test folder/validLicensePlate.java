package sqlTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class validLicensePlate {

	@Test
	void test() {
		DBconnect connect = new DBconnect();
		connect.validPinLength();
		boolean answer = connect.validLicensePlate();
		assertEquals(true,answer);
	}

}
