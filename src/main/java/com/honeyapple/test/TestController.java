package com.honeyapple.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honeyapple.test.mapper.TestMapper;

@RequestMapping("/test")
@Controller
public class TestController {
	
	@Autowired
	private TestMapper testMapper;

	// 1.
	@ResponseBody
	@RequestMapping("/1")
	public String test1() {
		return "Hello world!~";
	}
	
	// 2.
	@ResponseBody
	@RequestMapping("/2")
	public Map<String, Object> test2() {
		Map<String, Object> test = new HashMap<>();
		test.put("aaa", 111);
		test.put("bbb", 222);
		test.put("ccc", 333);
		return test;
	}
	
	// 3.
	@RequestMapping("/3")
	public String test3() {
		return "test/test";
	}
	
	// 4.
	@ResponseBody
	@RequestMapping("/4")
	public Map<String, Object> test4() {
		return testMapper.selectTest();
	}
}
