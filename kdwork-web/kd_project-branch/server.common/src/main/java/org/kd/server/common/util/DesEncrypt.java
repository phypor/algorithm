package org.kd.server.common.util;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncrypt {
	public static final String USER_ACCESS_TOKEN_KEY = "hj52hy9z";

	private static byte[] iv = { 4, 2, 9, 2, 3, 4, 2, 7 };

	public static String encrypt(String encryptKey, String encryptString) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
			return BASE64.encode(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String decryptKey, String decryptString) {
		try {
			byte[] byteMi = BASE64.decode(decryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String a = DesEncrypt.encrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY, "worldasdadsdasdadssadadasda");
		System.out.println("encoding:"+a);
		
		String b = DesEncrypt.decrypt(DesEncrypt.USER_ACCESS_TOKEN_KEY, a);
		System.out.println("b:"+b);
	}
}
