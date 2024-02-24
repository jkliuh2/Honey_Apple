package com.honeyapple.interest.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honeyapple.interest.domain.Interest;
import com.honeyapple.interest.mapper.InterestMapper;

@Service
public class InterestBO {
	
	@Autowired
	private InterestMapper interestMapper;
	
	
	// insert+Delete) 관심 토글
	public void interestToggle(int postId, int userId) {
		
		// DB select(존재여부 확인)
		int count = interestMapper.selectInterestCountByPostIdUserId(postId, userId);
		
		if (count > 0) {
			// 이미 존재 -> 삭제
			interestMapper.deleteInterest(postId, userId);
		} else {
			// 없음 -> 생성
			interestMapper.insertInterest(postId, userId);
		}
	}
	

	// select) postId로 관심 숫자 가져오기
	public int getInterestCountByPostId(int postId) {
		return interestMapper.selectInterestCountByPostIdUserId(postId, null);
	}
	// select) postId, userId로 관심 정보 있는지 확인하기
	public boolean filledHeart(int postId, int userId) {
		if (interestMapper.selectInterestCountByPostIdUserId(postId, userId) > 0) {
			return true;
		} else {
			return false;
		}
	}
	// select) buyerId로 관심List 가져오기(최신정렬)
	public List<Interest> getInterestListByBuyerIdSortByRecentest(int buyerId) {
		return interestMapper.selectInterestListByBuyerIdOrderByCreatedAtDesc(buyerId);
	}
}
