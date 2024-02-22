package com.honeyapple;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.honeyapple.common.EncryptUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class FileTest {
	
	@Autowired
	private EncryptUtils ecu;

	@Test
	void 비밀번호암호화() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String password = "bbbb";
		
		String hashedPassword = null;
		if (password != "" && password != null) {
			hashedPassword = EncryptUtils.shaAndHex(password, "SHA-256");
		}
		
		log.info("#### 비밀번호 : {}", password);
		log.info("$$$$ 암호화 비밀번호 : {}", hashedPassword);
	}
}
