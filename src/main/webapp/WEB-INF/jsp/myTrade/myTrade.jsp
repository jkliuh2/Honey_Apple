<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50">
	<%-- 메뉴 --%>
	<div class="mt-5">
		<ul class="nav nav-pills nav-fill">
			<li class="nav-item">
				<a class="nav-link active" href="#" data-menu="interestList">관심글</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#" data-menu="wantToBuyList">가격 제안중</a>
			</li>
		 	<li class="nav-item">
		   		<a class="nav-link" href="#" data-menu="reservationList">예약중</a>
		 	</li>
		 	<li class="nav-item">
		   		<a class="nav-link" href="#" data-menu="purchaseComplete">구매완료</a>
		 	</li>
		</ul>
		<hr>
	</div>
	
	<%-- 리스트 나오는 곳 --%>
	<div id="contents">
		<jsp:include page="articleList.jsp" />
	</div> <%-- id=contents 끝 --%>
</div>

<script>
	$(document).ready(function() {
		
		// nav-link 클릭 이벤트(메뉴 클릭시)
		$('.nav-link').on('click', function(e) {
			e.preventDefault(); // 페이지 올라가기 기능 막기
			let clickedMenu = $(this).data("menu");

			// 기본페이지 -> 그냥 페이지 새로고침
			if (clickedMenu == "interestList") {
				location.reload();
				return;
			}
			
			// ajax - #contents 내용 바꾸기
			// let userId = ${userId}; // 로그인유저정보는 세션에서 가져오면 된다.
			$.ajax({
				type:"GET"
				, url:"/my-trade/change-menu"
				, data:{"menu":clickedMenu}
			
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