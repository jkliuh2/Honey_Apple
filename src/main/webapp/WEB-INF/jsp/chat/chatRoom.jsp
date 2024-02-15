<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div mt-5">
	<%-- 게시글 및 판매자 정보(header) --%>
	<div id="chat-room-header" class="d-flex justify-content-between align-items-center">
		<%-- 게시글 간략 정보 --%>
		<div class="d-flex">
			<div class="d-flex align-items-center mr-2">
				<img src="/static/img/test-img.webp" width="60" height="60">
			</div>
			<div>
				<%-- 제목 --%>
				<div class="font-weight-bold">
					${post.subject}
				</div>
				<%-- 가격 --%>
				<div>
					<fmt:formatNumber value="${post.price}" />원
				</div>
				<%-- 주소 --%>
				<div>
					부산 광안리
				</div>
			</div>
		</div>
		
		<%-- 판매자 정보 --%>
		<div class="d-flex">
			<div class="d-flex align-items-center mr-2">
				<img src="/static/img/blank-profile.webp" width="60" height="60">
			</div>
			<div>
				<div class="font-weight-bold">
					${seller.nickname}
				</div>
				<div>
					매너온도
				</div>
				<div>
					${seller.temperature}°C
				</div>
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- 채팅 메시지 --%>
	<div id="chat-message-section">
		<ul>
		<c:forEach items="${chatMessageList}" var="chatMessage">
			<c:choose>
				<%-- 로그인 유저의 메시지 --%>
				<c:when test="${chatMessage.sellerId eq userId || chatMessage.buyerId eq userId}">
					<li class="mt-1">
						<div class="my-message d-flex justify-content-end">
							<div class="message d-flex align-items-center justify-content-center">${chatMessage.content}</div>
						</div>
					</li>
				</c:when>
				
				<%-- 예약, 예약취소, 거래완료 메시지 --%>
				<c:when test="${empty chatMessage.sellerId && empty chatMessage.buyerId}">
					<li class="my-3">
						<div class="text-center">
							<small class="font-weight-bold text-info">--- ${chatMessage.content} ---</small>
						</div>
					</li>
				</c:when>
				
				<%-- 상대편의 메시지 --%>
				<c:otherwise>
					<li class="mt-1">
						<div class="you-message d-flex justify-content-front">
							<div class="message d-flex align-items-center justify-content-center">${chatMessage.content}</div>
						</div>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</ul>
	</div>
	
	<hr>
	
	<%-- 예약 및 입력칸 --%>
	<form method="post" action="/chat/enter-message">
	<div class="mt-3 d-flex justify-content-between">
		<%-- 예약 버튼(판매자만 보임) --%>
		<div class="col-2">
			<c:if test="${not empty chatRoom.id && userId == post.sellerId && chatRoom.tradeStatus ne '완료'}">
				<button type="button" class="btn btn-secondary form-control" data-toggle="modal" data-target="#reservationModal">
					<c:if test="${chatRoom.tradeStatus eq '제안중'}">
						예약
					</c:if>
					<c:if test="${chatRoom.tradeStatus eq '예약'}">
						예약취소
					</c:if>
				</button>
			</c:if>
		</div>
		<div class="col-7 input-group">
			<input type="text" id="content" name="content" class="form-control" placeholder="내용을 입력하세요.">
			<input type="text" class="d-none" name="chatId" value="${chatRoom.id}">
			<input type="text" class="d-none" name="postId" value="${post.id}">
			<div class="input-group-append">
				<button class="btn btn-primary" type="submit" disabled id="contentBtn">입력</button>
			</div>
		</div>
	</div>
	</form>
</div>

<!-- Modal -->
<div class="modal fade" id="reservationModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" data-chat-id="${chatRoom.id}">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">예약/취소 확인</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <c:if test="${chatRoom.tradeStatus eq '제안중'}">
        	거래를 예약하시겠습니까?
        </c:if>
        <c:if test="${chatRoom.tradeStatus eq '예약'}">
        	거래를 취소하시겠습니까?
        </c:if>
      </div>
      <div class="modal-footer">
        <button type="button" id="reserveCheckBtn" class="btn btn-primary">확인</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>

<script>
	$(document).ready(function() {
		
		// modal 창
		$('#reservationModal #reserveCheckBtn').on('click', function() {
			let chatId = $('#reservationModal').data("chat-id");
			
			// 2. 예약 토글
			$.ajax({
				type:"POST"
				, url:"/chat/trade-reservation-toggle"
				, data:{"chatId":chatId}
				, success:function(data) {
					if (data.code == 200) {
						// 예약or예약취소 성공
						alert(data.success_message);
						location.reload();
					} else if (data.code = 300) {
						// 예약시도시 - 이미 다른 채팅방에서 예약상태
						alert(data.reservation_dupl_message);
						location.reload();
					} else {
						// 그 외 db 오류
						alert(data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("예약관리에 실패했습니다. 관리자에게 문의해주세요.");
				}
			}); // ajax 끝
		}); // 모달창 확인 이벤트
		
		
		// content change이벤트
		$('#content').on('change', function() {
			$('#contentBtn').prop('disabled', false);
			
			let content = $(this).val();
			if (!content) {
				$('#contentBtn').prop('disabled', true); // submit 버튼 활성화
			}
		}); // content change 이벤트끝
		
	});// 레디이벤트 끝
</script>