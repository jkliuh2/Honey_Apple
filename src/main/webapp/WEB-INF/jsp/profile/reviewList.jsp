<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="mt-3">
<%--
	<c:forEach items="${reviewCardList}" var="reviewCard">
 --%>
 	<c:forEach begin="1" end="3">
	<div>
		<img src="/static/img/blank-profile.webp" width="25" height="25" alt="프로필 이미지">
		<small class="font-weight-bold ml-2">구매자 닉네임(유저 닉네임)</small>
		<small class="ml-2">강남구 역삼동(유저 동네)</small>
	</div>
	<div class="mt-2">
		예쁘게 입히세요.(review.review)
	</div>
	<div class="mt-2">
		<small class="gray-text">3개월 전(createdAt)</small>
	</div>
	<hr>
	</c:forEach>
</div>