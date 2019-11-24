package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class method2 {

	@Test
	void test() {
		
			junitTesting test1 = new junitTesting();
			
		String  Username = "^^^^^";
				
				boolean answer1 = test1.testUsernameFormat(Username);
				
				
			assertEquals(true, answer1  );
			
		

	}

}
