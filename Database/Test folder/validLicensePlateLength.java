package sqlTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class validLicensePlateLength {

	@Test
	void test() {
		DBconnect connect = new DBconnect();
		connect.validLicensePlateLength();
		boolean answer = connect.validLicensePlateLength();
		assertEquals(true,answer);
	}

}
