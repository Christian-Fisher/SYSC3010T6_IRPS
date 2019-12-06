package sqlTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class pinExists {

	@Test
	void test() {
		DBconnect connect = new DBconnect();
		connect.pinExists();
		boolean answer = connect.pinExists();
		assertEquals(true,answer);
	}

}
