<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="w-50">
	<%-- 프로필 유저 정보, 정보수정 버튼 --%>
	<div class="d-flex justify-content-center mt-3">
		<%-- 유저 정보 --%>
		<div class="col-9 d-flex">
			<%-- 프로필 이미지 --%>
			<img src="/static/img/blank-profile.webp" width="70" height="70" alt="프로필 이미지">
			<%-- 닉네임, 매너온도 --%>
			<div>
				<div class="font-weight-bold">${user.nickname}</div>
				<div>매너온도 ${user.temperature}°C</div>
			</div>
			<%-- 유저 동네 --%>
			<div>
				경기도 성남시 분당구 구미동 - 미설정
			</div>
		</div>
		<%-- 정보 수정 버튼(프로필유저 = 로그인유저) --%>
		<div class="col-3 d-flex justify-content-center align-items-center">
			<c:if test="${userId eq user.id}">
			<button type="button" id="userUpdateBtn" class="btn btn-secondary">정보 수정</button>
			</c:if>
		</div>
	</div> <%-- 프로필 유저정보 끝 --%>
	
	<%-- 글 생성 버튼(프로필유저 = 로그인유저) --%>
	<c:if test="${userId eq user.id}">
		<a href="/post/create-view" class="btn btn-light form-control">새로운 물건 올리기</a>
	</c:if>
	
	<%-- 메뉴 --%>
	
</div>