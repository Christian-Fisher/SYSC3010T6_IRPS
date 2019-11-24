package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testPINformatLength {

	@Test
	void test() {
	
		
		
	junitTesting test = new junitTesting();
			
			String  PIN = "11111111";
			
			boolean answer = test.testPINformatLength(PIN);
			
			
		assertEquals(true, answer  );
			
		}

}
