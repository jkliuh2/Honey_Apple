<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50">
	<%-- 프로필 유저 정보, 정보수정 버튼 --%>
	<div class="d-flex justify-content-center mt-3">
		<%-- 유저 정보 --%>
		<div class="col-9 d-flex p-0">
			<%-- 프로필 이미지 --%>
			<img src="/static/img/blank-profile.webp" width="70" height="70" alt="프로필 이미지">
			<%-- 닉네임, 매너온도 --%>
			<div class="ml-2">
				<div class="font-weight-bold">${user.nickname}</div>
				<div>매너온도 ${user.temperature}°C</div>
			</div>
			<%-- 유저 동네 --%>
			<div>
				<small>경기도 성남시 분당구 구미동 - 미설정</small>
			</div>
		</div>
		<%-- 정보 수정 버튼(프로필유저 = 로그인유저) --%>
		<div class="col-3 d-flex justify-content-end align-items-center">
			<c:if test="${userId eq user.id}">
			<button type="button" id="userUpdateBtn" class="btn btn-secondary">정보 수정</button>
			</c:if>
		</div>
	</div> <%-- 프로필 유저정보 끝 --%>
	
	<%-- 글 생성 버튼(프로필유저 = 로그인유저) --%>
	<c:if test="${userId eq user.id}">
		<a href="/post/create-view" class="btn btn-info form-control mt-3">새로운 물건 올리기</a>
	</c:if>
	
	<%-- 메뉴 --%>
	<div class="mt-5">
		<ul class="nav nav-pills nav-fill">
			<li class="nav-item">
				<a class="nav-link active" href="#" data-menu="sellingList">판매 물품</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#" data-menu="reviewList">거래 후기</a>
			</li>
		 	<li class="nav-item">
		   		<a class="nav-link" href="#" data-menu="soldList">판매 완료 물품</a>
		 	</li>
		</ul>
		<hr>
	</div>
	
	<%-- 리스트 나오는 곳 --%>
	<div id="contents">
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
</div>

<script>
	$(document).ready(function() {
		
		// nav-link 클릭 이벤트(메뉴 클릭시)
		$('.nav-link').on('click', function(e) {
			e.preventDefault(); // 페이지 올라가기 기능 막기
			let clickedMenu = $(this).data("menu");

			// 판매물품 -> 그냥 페이지 새로고침
			if (clickedMenu == "sellingList") {
				location.reload();
				return;
			}
			
			// ajax - #contents 내용 바꾸기
			let userId = ${user.id}; // 프로필 유저id
			$.ajax({
				type:"GET"
				, url:"/profile/change-menu"
				, data:{"menu":clickedMenu, "userId":userId}
			
				, success:function(data) {
					$('#contents').html(data); // 아래 내용 바꾸기
				}
				, error:function(request, status, error) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
				}
			}); // ajax 끝
			
			// active 메뉴 변경
			$('.nav-link').removeClass('active');
			$(this).addClass('active');
			
		}); // nav-link 클릭이벤트 끝
	}); // 레디이벤트 끝
</script>