package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class method3 {

	@Test
	
	void test() {

	
	junitTesting test = new junitTesting();
			
			String  testPINformat = "1234";
			
			boolean answer = test.testPINformat(testPINformat);
			
			
		assertEquals(true, answer  );
			
		
		
	}

}
