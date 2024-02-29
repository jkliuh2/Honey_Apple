package com.honeyapple.user.bo;

import org.springframework.stereotype.Service;

@Service
public class HometownBO {
// user.hometown 변환을 위한 BO
	
	
	// 3개로 나뉜 주소코드를 합쳐서 hometown 8자리 코드로 만들어서 리턴
	public String codeMerge(Integer sido, Integer sigugun, Integer dong) {
		// 주소 3개를 조합해서 8자의 String으로 만든다.
		String hometown = "";
		if (sido == null) {
			hometown = null;
		} else {
			hometown += sido;
			if (sigugun != null) {
				hometown += sigugun;
				if (dong != null) {
					hometown += dong;
				}
			}
		}
		// 만약 sigugun이나 dong이 비어있다면 그 부분은 더하지 않고 리턴한다.
		return hometown; // null or str
	}
}
