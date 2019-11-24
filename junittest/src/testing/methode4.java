package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class methode4 {

	@Test
	void test() { // not the right way
		junitTesting test = new junitTesting();
		
		String  username = "lo^lia@";
		
		boolean answer = test.testInvalidUsernameFormat(username);
		
		
	assertEquals(true, answer  );
		
	}

}
