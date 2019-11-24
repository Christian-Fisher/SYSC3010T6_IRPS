package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClaimedLicensePlate {

	@Test
	void test() {
		junitTesting test = new junitTesting();
		
		// String []licenseplate= {"abcd123", "abcd111", "abcd222"};
		
		String  plate = "abcd123";
		
		String answer = test.ClaimedLicensePlate(plate);
		
		
	assertEquals("registered", answer  );
		
	}

}
