<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div">
	<hr>
	<div class="d-flex justify-content-center my-5">
		<h1>달달한 인기매물</h1>
	</div>

	<%-- 게시물들 --%>
	<div class="d-flex flex-wrap justify-content-between">

		<%-- 게시물 Card (반복문) --%>
		<c:forEach items="${articleList}" var="article">
			<a href="/article/detail-view?postId=${article.post.id}" class="card card-size m-3"> <%-- 이미지 --%> 
				<img src="${article.post.imgPath1}" class="card-img-top" alt="중고거래 이미지">
				<div class="card-body text-dark">
					<%-- 글 제목 --%>
					<h5 class="card-title">${article.post.subject}</h5>
					<%-- 가격 --%>
					<div class="font-weight-bold">
						<fmt:formatNumber value="${article.post.price}" />원
					</div>
					<%-- 판매자 위치정보 --%>
					<div>
						<small>${article.user.hometown} / 동네 미설정</small>
					</div>
					<%-- 관심 수 --%>
					<div>
						<small>관심 ${article.interestCount}</small>
					</div>
					<%-- 업데이트 시간 --%>
					<div>
						<small>1시간 전 / 아직 미설정</small>
					</div>
				</div>
			</a>
		</c:forEach>
		<%-- 반복문 끝 --%>

	</div>
</div>