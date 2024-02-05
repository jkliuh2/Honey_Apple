<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="d-flex justify-content-between h-100">
	<%-- 좌측상단 로고 링크 --%>
	<div id="logo" class="col-3 d-flex justify-content-center align-items-center">
		<a href="/honey-apple">
			<h1>
				<span id="honey">Honey</span> <span id="apple">Apple</span>
			</h1>
		</a>
	</div>

	<%-- 로그인 안된 상태. --%>
	<c:if test="${empty userNickname}">
		<%-- 로그인 --%>
		<div class="col-1 d-flex justify-content-center align-items-center">
			<a href="/user/sign-in-view" class="sign-in-out"> 로그인 </a>
		</div>
	</c:if>
	<%-- 로그인 된 상태. --%>
	<c:if test="${not empty userNickname}">
		<div class="col-4 d-flex justify-content-between">
			<%-- 유저 정보 --%>
			<a href="#">
				<label class="h-100 d-flex align-items-center">
					<img src="/static/img/blank-profile.webp" alt="프로필 이미지" width="60" height="60">
					<span class="text-dark">${userNickname}</span>
				</label>
			</a>
			
			<%-- 내 꿀템들 + 로그아웃 --%>
			<div>
				<div class="h-50 d-flex align-items-center">
					<a href="#" class="font-weight-bold text-warning">내 꿀템들</a>
				</div>
				<div class="h-50">
					<a href="/user/sign-out" class="sign-in-out">로그아웃</a>
				</div>
			</div>
		</div>
	</c:if>
</div>