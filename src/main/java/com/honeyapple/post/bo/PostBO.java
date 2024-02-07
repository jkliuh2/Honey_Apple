package com.honeyapple.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.common.FileManagerService;
import com.honeyapple.post.domain.Post;
import com.honeyapple.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	

	// C
	// input: params / output: Post
	public Post addPost(
			int userId, String userLoginId, String subject, String content, int price, int negotiable,
			MultipartFile imgFile1, MultipartFile imgFile2, MultipartFile imgFile3,
			MultipartFile imgFile4, MultipartFile imgFile5
			) {
		
		// 이미지 파일 처리
		List<MultipartFile> imgFileList = new ArrayList<>();
		imgFileList.add(imgFile1);
		imgFileList.add(imgFile2);
		imgFileList.add(imgFile3);
		imgFileList.add(imgFile4);
		imgFileList.add(imgFile5);
		
		List<String> imgPathList = new ArrayList<>();
		
		for (MultipartFile imgFile : imgFileList) {
			String imgPath = null; // DB로 전달되는 이미지파일Path
			
			if (imgFile != null) {
				imgPath = fileManagerService.saveFile(userLoginId, imgFile); // 이미지 파일저장 + Path리턴(File없으면 NULL)
			}
			
			imgPathList.add(imgPath); // path or null
		}
		/////// 이미지 파일 -> path로 전환 끝
		
		// DTO에 담기
		Post post = new Post();
		post.setSellerId(userId);
		post.setSubject(subject);
		post.setPrice(price);
		post.setNegotiable(negotiable);
		post.setContent(content);
		post.setImgPath1(imgPathList.get(0));
		post.setImgPath1(imgPathList.get(1));
		post.setImgPath1(imgPathList.get(2));
		post.setImgPath1(imgPathList.get(3));
		post.setImgPath1(imgPathList.get(4));
		
		// DB insert + 리턴
		return postMapper.insertPost(post);
	}
}
