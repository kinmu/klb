package bsmedia.system.seed;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/****************************************************
 * <PRE>
 * @Project Name	: KONGJU
 * @File Name		: Seed.java
 * @Comment			: ?�호??& 복호???�스???�이�?
 * @Author			: eunjung
 * @Creation Date	: 2012. 1. 13.?�후 4:15:40
 * Copyright ?�b&s media All Right Reserved
 * </PRE>
 ****************************************************/
public class Seed {

	public static void main(String[] args) throws Exception {
		String text = "Seed ?�호??Test \t ?�되겠�?. HaHa~~"; //param�?
		String key = "vindzalkerowjfhp"; 					//길이�?무조�?16개이�???
		StringBuilder trace = new StringBuilder();
		
		trace.append("Plain Text :: [").append(text).append("]");
		System.out.println(trace.toString());
		
		//?�호??
		SeedCipher seed = new SeedCipher();
		String encryptText = Base64.encode(seed.encrypt(text, key.getBytes(), "UTF-8"));
		
		trace = new StringBuilder();
		trace.append("Encrypt Text (Base64 Encoding) :: [").append(encryptText).append("]");
		System.out.println(trace.toString());
		
		//복호??
		byte[] encryptbytes = Base64.decode(encryptText);
		String decryptText = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
		
		trace = new StringBuilder();
		trace.append("Decrypt Text :: [").append(decryptText).append("]");
		System.out.println(trace.toString());
	}
	
	/**
	 * METHOD Comment : ?�호??
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public final static String validateSEEDencode(String input) throws Exception {
		
		String key = "vindzalkerowjfhp"; //길이  	
		SeedCipher seed = new SeedCipher();
		String encryptText = Base64.encode(seed.encrypt(input, key.getBytes(), "UTF-8"));
		
		return encryptText;
	}
		
	/**
	 * METHOD Comment : 복호??
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public final static String validateSEEDdecode(String input) throws Exception {
		
		String key = "vindzalkerowjfhp"; //길이 	
		SeedCipher seed = new SeedCipher();
		byte[] encryptbytes = Base64.decode(input);
		String decryptText = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
		
		return decryptText;
	}	
	
}
