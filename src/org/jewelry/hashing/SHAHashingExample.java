package org.jewelry.hashing;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class SHAHashingExample {
	
	public static MessageDigest generateHashedPassword() throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md;
	}
	
	public static void generateSalt() throws Exception{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[512];
		random.nextBytes(bytes);
	}
	

}
