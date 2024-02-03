package com.honeyapple.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
// 암호화 클래스

	// SHA 암호화 - 추가적인 라이브러리가 필요한 것 같아서 일단 보류.
//	// input: 원본 비밀번호
//	// output: hashing된 비밀번호
//	// MD5 암호화와 Hex(16진수) 인코딩
//	public static String shaAndHex(String plainText, String Algorithms)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//		// MessageDigest 인스턴스 생성(MD5)
//		MessageDigest md = MessageDigest.getInstance(Algorithms);
//
//		// 해쉬값 업데이트
//		md.update(plainText.getBytes("utf-8"));
//
//		// Byte To Hex
//		return Hex.encodeHexString(md.digest());
//	}

	// md5 암호화
	public static String md5(String message) {
		String encData = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] bytes = message.getBytes();
			md.update(bytes);
			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; i++) {
				encData += Integer.toHexString(digest[i] & 0xff); // 16진수로 변환하는 과정
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encData;
	}
}
