package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testmethod1 {

	@Test
	void test() {
		
		junitTesting test = new junitTesting();
		
		String  LicensePlate = "abcd123";
		
		boolean answer = test.testLicensePlate(LicensePlate);
		
		
	assertEquals(true, answer  );
		
	
	

	}
	
	
}
