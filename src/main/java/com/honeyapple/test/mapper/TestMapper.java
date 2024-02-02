package com.honeyapple.test.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

	public Map<String, Object> selectTest();
}
