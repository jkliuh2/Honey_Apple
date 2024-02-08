package com.honeyapple.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	

	/**
	 * 글 생성 API
	 * (로그인 only)
	 * @param subject
	 * @param price
	 * @param negotiable
	 * @param content
	 * @param imgFile1
	 * @param imgFile2
	 * @param imgFile3
	 * @param imgFile4
	 * @param imgFile5
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("price") int price,
			@RequestParam("negotiable") int negotiable,
			@RequestParam("content") String content,
			@RequestParam("imgFile1") MultipartFile imgFile1,
			@RequestParam(name = "imgFile2", required = false) MultipartFile imgFile2,
			@RequestParam(name = "imgFile3", required = false) MultipartFile imgFile3,
			@RequestParam(name = "imgFile4", required = false) MultipartFile imgFile4,
			@RequestParam(name = "imgFile5", required = false) MultipartFile imgFile5,
			HttpSession session
			) {
		// request params : (변수화O) subject, content, price, negotiable / (변수화X) imgFile1~5
		
		// 세션의 유저 정보 가져오기
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// DB insert
		int postId = postBO.addPost(userId, userLoginId, subject, content, price, negotiable,
				imgFile1, imgFile2, imgFile3, imgFile4, imgFile5);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (postId > 0) {
			result.put("code", 200);
			result.put("postId", postId);
		} else {
			result.put("code", 500);
			result.put("error_message", "DB insert에 실패했습니다.");
		}
		
		return result;
	}
}
