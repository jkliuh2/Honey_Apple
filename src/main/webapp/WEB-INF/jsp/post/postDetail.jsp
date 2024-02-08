<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50 mt-5">
	<%-- 이미지(1~5개) --%>
	<div class="d-flex justify-content-center">
		<img src="/static/img/test-img.webp" alt="게시물 이미지" width="450">
	</div>
	
	<%-- 판매자 정보 --%>
	<div class="d-flex align-items-center">
		<%-- 판매자 프로필이미지 --%>
		<div class="col-2">
			<img src="/static/img/blank-profile.webp" alt="판매자 프로필 이미지" width="60" height="60">
		</div>
		
		<%-- 판매자 nickname, hometown --%>
		<div class="col-7">
			<div class="font-weight-bold">
				sellerNickname
			</div>
			<div>
				sellerhometown
			</div>
		</div>
		
		<%-- 판매자 매너온도 --%>
		<div class="col-3">
			<div>
				매너온도
			</div>
			<div class="font-weight-bold">
				36.5℃
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- 여기부터 post 내용들 --%>
	<%-- 글 제목 --%>
	<h3>글 제목</h3>
	
	<%-- 가격, 네고여부 --%>
	<div class="font-22px font-weight-bold">
		70,000원 네고가능
	</div>
	
	<%-- 글 내용 --%>
	<div class="my-3">
		내용내용ㄴ어ㅣㅇㄴ머이ㅏㅇ머
		엄니엉나ㅣㅁ
		ㅇㅁ어ㅏ님언미ㅏㅇㅁ
	</div>
	
	<%-- 관심(+조회수) --%>
	<small class="text-secondary">
		- 관심 10
	</small>
	
	<hr>
	
	<%-- 거래 희망장소 --%>
	<div class="my-3">
		<div class="font-weight-bold">- 거래희망장소</div>
		<div>
			지도 들어갈 공간
		</div>
	</div>
	
	<%-- 버튼 공간 --%>
	<div class="mt-5 d-flex align-items-center">
		<%-- 관심 버튼(구매자용) --%>
		<div class="col-3 d-flex justify-content-center">
			<a href="#">
				<img src="/static/img/empty-heart.png" width="35" height="35">
			</a>
		</div>
		
		<%-- 거래 제안하기(구매자용) or 제안 목록(판매자용) --%>
		<div class="col-6">
			<a href="#" class="btn btn-primary form-control">거래 제안하기</a>
		</div>
		
		<%-- 수정버튼(판매자용) --%>
		<div class="col-3">
			<a href="#" class="btn btn-secondary form-control">수정</a>
		</div>
	</div>
</div>