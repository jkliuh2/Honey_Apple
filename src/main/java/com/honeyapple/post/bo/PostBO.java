package com.honeyapple.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.honeyapple.common.FileManagerService;
import com.honeyapple.post.domain.Post;
import com.honeyapple.post.mapper.PostMapper;
import com.honeyapple.user.entity.UserEntity;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	
	// C
	// input: params / output: Integer(postId pk값)
	public int addPost(
			int userId, String userLoginId, String subject, String content, int price, int negotiable,
			MultipartFile imgFile1, MultipartFile imgFile2, MultipartFile imgFile3,
			MultipartFile imgFile4, MultipartFile imgFile5,
			String tradeMethod, Double latitude, Double longitude
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
		post.setImgPath2(imgPathList.get(1));
		post.setImgPath3(imgPathList.get(2));
		post.setImgPath4(imgPathList.get(3));
		post.setImgPath5(imgPathList.get(4));
		
		post.setTradeMethod(tradeMethod);
		post.setLatitude(latitude);
		post.setLongitude(longitude);
		
		// DB insert + 리턴
		postMapper.insertPost(post);
		return post.getId();
	}
	
	
	// select by postId
	public Post getPostById(int postId) {
		return postMapper.selectPostById(postId);
	}
	// select) 판매중+최신+숫자제한
	public List<Post> getPostListByStatusOrderByIdDescLimit6(String status) {
		return postMapper.selectPostListByStatusOrderByIdDescLimit6(status);
	}
	// select) sellerId + status + id내림차순 -> List
	public List<Post> getPostListBySellerIdStatusOrderByIdDesc(int sellerId, String status, String exceptStatus) {
		return postMapper.selectPostListBySellerIdStatusOrderByIdDesc(sellerId, status, exceptStatus);
	}
	// select) keyword + userList로 검색 -> List
	public List<Post> getPostListByKewordUserList(String keyword, List<UserEntity> userList) {
		return postMapper.selectPostListByKewordAndUserList(keyword, userList);
	}
	
	
	// Update - id, status(거래상태)
	public void updatePostByIdStatus(int postId, String status) {
		postMapper.updatePostByIdStatus(postId, status);
	}
	// update - 글 수정
	public void updatePost(int userId, String userLoginId, int postId, 
			String subject, String content, int price, int negotiable,
			MultipartFile imgFile2, MultipartFile imgFile3, 
			MultipartFile imgFile4, MultipartFile imgFile5) {
		
		// 이미지 파일 처리
		List<MultipartFile> imgFileList = new ArrayList<>();
		// img1은 필수
		imgFileList.add(imgFile2);
		imgFileList.add(imgFile3);
		imgFileList.add(imgFile4);
		imgFileList.add(imgFile5);
		
		List<String> imgPathList = new ArrayList<>();
		
		for (MultipartFile imgFile : imgFileList) {
			String imgPath = null; // DB로 전달되는 이미지파일Path
			
			if (imgFile != null) {
				// File이 null이 아니면 path 변환
				imgPath = fileManagerService.saveFile(userLoginId, imgFile); // 이미지 파일저장 + Path리턴(File없으면 NULL)
			}
			imgPathList.add(imgPath); // path or null
		}
		/////// 이미지 파일 -> path로 전환 끝
		
		// DTO에 담기
		Post post = new Post();
		// id, sellerId로 WHERE문 구성
		post.setId(postId);
		post.setSellerId(userId);
		// 이 밑으로는 모두 SET 필드들
		post.setSubject(subject);
		post.setPrice(price);
		post.setNegotiable(negotiable);
		post.setContent(content);
		post.setImgPath2(imgPathList.get(0));
		post.setImgPath3(imgPathList.get(1));
		post.setImgPath4(imgPathList.get(2));
		post.setImgPath5(imgPathList.get(3));
		
		// DB - update
		postMapper.updatePost(post);
	}
}
