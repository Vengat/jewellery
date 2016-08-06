package org.jewelry.hashing;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;


public class SaltingExample {
	// ...
	 
	public static int generateSalt() {
	   try {
	     SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
	     // Generate 20 integers 0..20
	     for (int i = 0; i < 20; i++) {
	    	 int randomNo= number.nextInt(21);
	       //System.out.println(number.nextInt(21));
	     }
	   } catch (NoSuchAlgorithmException nsae) { 
	     // Forward to handler
	   }
	return 0;
	}
}
