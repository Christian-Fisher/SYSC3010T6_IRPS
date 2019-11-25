package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testInvalidPin {

	@Test
	void test() {
		
junitTesting test = new junitTesting();
		//String[] PINin = { "1234", "1114", "5555"} ;
		String  PIN = "1234";
		
		boolean answer = test.testInvalidPin(PIN);
		
		
	assertEquals(true, answer  );
		
		
	}

}
