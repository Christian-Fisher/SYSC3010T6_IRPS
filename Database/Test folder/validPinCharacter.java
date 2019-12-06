package sqlTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class validPinCharacter {

	@Test
	void test() {
		DBconnect connect = new DBconnect();
		connect.validPinCharacter();
		boolean answer = connect.validPinCharacter();
		assertEquals(true,answer);
	}

}
