<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="medium-size-div">
	<hr>
	<div class="d-flex justify-content-center my-5">
		<h1>달달한 인기매물</h1>
	</div>

	<%-- 게시물들 --%>
	<div class="d-flex flex-wrap justify-content-between">

		<%-- 게시물 Card (반복문) --%>
		<c:forEach begin="1" end="6">
			<a href="#" class="card card-size m-3"> <%-- 이미지 --%> <img
				src="/static/img/test-img.webp" class="card-img-top" alt="중고거래 이미지">
				<div class="card-body text-dark">
					<%-- 글 제목 --%>
					<h5 class="card-title">중고물품 팔아요.</h5>
					<%-- 가격 --%>
					<div class="font-weight-bold">15,000원</div>
					<%-- 판매자 위치정보 --%>
					<div>
						<small>경기도 성남시 분당구 구미동</small>
					</div>
					<%-- 관심 수 --%>
					<div>
						<small>관심</small>
					</div>
					<%-- 업데이트 시간 --%>
					<div>
						<small>1시간 전</small>
					</div>
				</div>
			</a>
		</c:forEach>
		<%-- 반복문 끝 --%>

	</div>
</div>