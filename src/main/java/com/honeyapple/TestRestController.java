package com.honeyapple;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	
	// 위,경도 확인용 API
	@PostMapping("/test-geo")
	public Map<String, Object> testGeo(
			@RequestParam(name = "latitude", required = false) Double latitude, // 위도
			@RequestParam(name = "longitude", required = false) Double longitude // 경도
			) {
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	}
}
