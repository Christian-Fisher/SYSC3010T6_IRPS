package sqlTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testUserNameFormat {

	@Test
	void test() {
		DBconnect connect = new DBconnect();
		connect.testUserNameFormat();
		boolean answer = connect.testUserNameFormat();
		assertEquals(true,answer);
	}

}
