package com.wipro.automation.snow.lib;

import java.security.Key;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
*/

public class Crypto
{
	private static final String ALGORITHM = "AES";
	private static final String KEY = "4~WfjW$h37^rK4dJ";
	
	public static String encrypt(String value) throws Exception
	{
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(Crypto.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		//String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
		String encryptedValue64 = Base64.getEncoder().encodeToString(encryptedByteValue);
		
		return encryptedValue64;
	}
	
	public static String decrypt(String value) throws Exception
	{
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(Crypto.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		//byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
		byte [] decryptedValue64 = Base64.getDecoder().decode(value);
		byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
		String decryptedValue = new String(decryptedByteValue,"utf-8");
		
		return decryptedValue;
	}
	
	public static Key generateKey() throws Exception
	{
		Key key = new SecretKeySpec(Crypto.KEY.getBytes(), Crypto.ALGORITHM);
		return key;
	}
	
	public static String getString(String encString)
	{
		byte [] barr = Base64.getDecoder().decode(encString);
		return new String(barr);
	}
	
	public static void main(String[] args) throws Exception
	{
		String scan;
		
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the value to be encrypted : ");
		scan = s.next();
		s.close();
		
		//System.out.println("Encrypted value is : " + Crypto.encrypt(pass));
		System.out.println("Encrypted value is : " + Crypto.encrypt(scan));
	}
	
}