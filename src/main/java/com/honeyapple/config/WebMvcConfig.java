package com.honeyapple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.honeyapple.common.FileManagerService;
import com.honeyapple.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	// 인터셉터 연결을 위한 처리 1
	@Autowired
	private PermissionInterceptor interceptor;
	
	// 인터셉터 연결을 위한 처리 2	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**") // 모든(혹은 특정) 주소에 대해 인터셉터 검사 하겠다.
		.excludePathPatterns("/static/**", "/error", "/user/sign-out") // 인터셉터 검사 제외할 것이다.(css나 이미지 같은 것들.)
		;
	}	
	
	
	
	// 웹 이미지 path와 서버에 업로드 된 실제 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(
			ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);
		// 슬래쉬(/) 갯수 주의. MAC:2, Window:3
		
		// http://localhost/images/aaaa_1705483608048/ship-8489583_1280.jpg
		// 이제 위 주소로 브라우저에 치면, 실제 이미지 파일경로에 있는 이미지와 매칭되서 나온다.
	}
}