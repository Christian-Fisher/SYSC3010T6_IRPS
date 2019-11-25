package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testAvailableSpots {

	@Test
	void test() {
		junitTesting test = new junitTesting();
		
		//String []availableSpots={"a1","a2", "a3"};
		
		String  spot = "a1";
		
		boolean answer = test.testAvailableSpots(spot);
		
		
	assertEquals(true, answer  );
		
	}

}
