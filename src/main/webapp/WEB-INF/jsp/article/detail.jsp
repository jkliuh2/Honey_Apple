<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="w-50 mt-5">
	<%-- 이미지(1~5개) --%>
	<div class="d-flex justify-content-between">
		<button type="button" class="arrowBtn d-flex align-items-center border-0" id="leftArrow">
			<img src="/static/img/left-arrow.webp" alt="왼쪽 화살표" width="40" height="40">
		</button>
		<img id="postImg" src="${article.post.imgPath1}" alt="게시물 이미지" width="500" data-path-number="1">
		<button type="button" class="arrowBtn d-flex align-items-center border-0" id="rightArrow">
			<img src="/static/img/right-arrow.webp" alt="오른쪽 화살표" width="40" height="40">
		</button>
	</div>
	
	<%-- 판매자 정보 --%>
	<div class="d-flex align-items-center mt-3">
		<%-- 판매자 프로필이미지 --%>
		<div class="col-2">
			<c:choose>
			<c:when test="${not empty article.user.profileImagePath}">
				<img src="${article.user.profileImagePath}" alt="판매자 프로필 이미지" width="60" height="60">
			</c:when>
			<c:otherwise>
				<img src="/static/img/blank-profile.webp" alt="판매자 프로필 이미지" width="60" height="60">
			</c:otherwise>
			</c:choose>
		</div>
		
		<%-- 판매자 nickname, hometown --%>
		<div class="col-7">
			<a href="/profile/selling-list-view?userId=${article.user.id}" 
			class="font-weight-bold text-dark">
				${article.user.nickname}
			</a>
			<div>
				${article.user.hometown} : 아직 미정
			</div>
		</div>
		
		<%-- 판매자 매너온도 --%>
		<div class="col-3">
			<div>
				매너온도
			</div>
			<div class="font-weight-bold">
				${article.user.temperature}°C
			</div>
		</div>
	</div>
	
	<hr>
	
	<%-- 여기부터 post 내용들 --%>
	<%-- 글 제목 + 거래상태 --%>
	<div class="d-flex justify-content-between align-items-center">
		<h3>${article.post.subject}</h3>
		<span class="text-info font-weight-bold">${article.post.status}</span>
	</div>
	
	<%-- 가격, 네고여부 --%>
	<div class="font-22px font-weight-bold">
		<%-- price --%>
		<span>
			<fmt:formatNumber value="${article.post.price}" />원
		</span> 
		
		<%-- negotiable --%>
		<c:if test="${article.post.negotiable eq 1}">
			<span class="text-success">네고가능</span>
		</c:if>
		<c:if test="${article.post.negotiable eq 0}">
			<span class="text-danger">네고불가</span>
		</c:if>
	</div>
	
	<%-- 글 내용 --%>
	<div class="my-3">
		<pre>${article.post.content}</pre>
	</div>
	
	<%-- 관심(+조회수) --%>
	<small class="text-secondary">
		- 관심 ${article.interestCount}
	</small>
	
	<hr>
	
	<%-- 거래 희망 방법 --%>
	<div class="my-3">
		<%-- 거래방식 --%>
		<div>거래 방식 : <span class="font-weight-bold">${article.post.tradeMethod}</span></div>
		
		<%-- 지도영역 --%>
		<div id="map-div" class="mt-3">
			<div class="map_wrap">
			    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
			    <div class="hAddr">
			        <span class="title">지도중심기준 행정동 주소정보</span>
			        <span id="centerAddr"></span>
			    </div>
			</div>
		</div>		
	</div>
	
	<%-- 버튼 공간 --%>
	<div class="mt-5 d-flex align-items-center">
		<%-- 관심 버튼(구매자용) --%>
		<div class="col-3 d-flex justify-content-center">
			<c:if test="${article.post.sellerId ne userId}">
				<a href="#" id="interestToggle">
					<img src="/static/img/
					<c:if test="${article.filledHeart eq false}">
					empty
					</c:if>
					<c:if test="${article.filledHeart}">
					fill
					</c:if>
					-heart.png" width="35" height="35">
				</a>
			</c:if>
		</div>
		
		<%-- 거래 제안하기(구매자용) or 제안 목록(판매자용) --%>
		<div class="col-6">
			<%-- 채팅방으로 이동(구매자전용, 비-로그인도 보임) --%>
			<c:if test="${article.post.sellerId ne userId}">
				<a href="/chat/chat-room-view?postId=${article.post.id}" class="btn btn-primary form-control">거래 제안하기</a>
			</c:if>
			
			<%-- 채팅목록으로 이동(판매자전용) --%>
			<c:if test="${article.post.sellerId eq userId}">
				<a href="/chat/chat-list-view?postId=${article.post.id}" class="btn btn-primary form-control">거래 제안목록(${article.chatRoomCount})</a>
			</c:if>
		</div>
		
		<%-- 수정버튼(판매자용) --%>
		<div class="col-3">
			<c:if test="${article.post.sellerId eq userId && article.post.status eq '판매중'}">
				<a href="/post/update-view?postId=${article.post.id}" class="btn btn-secondary form-control">수정</a>
			</c:if>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 그림 화살표 버튼 이벤트
		$('.arrowBtn').on('click', function() {
			//alert("화살표");
			let direction = $(this).attr("id"); // leftArrow / rightArrow
			let pathNum = $('#postImg').data("path-number");
			
			// null아닌 imgPath들 List에 넣기
			var imgPathList = ['${article.post.imgPath1}'];
			if (${not empty article.post.imgPath2}) {
				imgPathList.push('${article.post.imgPath2}');
			}
			if (${not empty article.post.imgPath3}) {
				imgPathList.push('${article.post.imgPath3}');
			}
			if (${not empty article.post.imgPath4}) {
				imgPathList.push('${article.post.imgPath4}');
			}
			if (${not empty article.post.imgPath5}) {
				imgPathList.push('${article.post.imgPath5}');
			}
			//console.log(imgPathList);
			
			// 화살표 방향에 따른 그림 바꾸기
			if (direction == 'leftArrow') { // 왼쪽 화살표
				pathNum = pathNum - 1;
				if (pathNum < 1) {
					pathNum = imgPathList.length;
					//console.log(pathNum);
				}
				
				$('#postImg').attr("src", imgPathList[pathNum - 1]); // 이미지 src 변경
				$('#postImg').data("path-number", pathNum); // 이미지의 data값 변경
				
			} else if (direction == 'rightArrow') { // 오른쪽 화살표
				pathNum = pathNum + 1;
				if (pathNum > imgPathList.length) {
					pathNum = 1;
				}
				
				$('#postImg').attr("src", imgPathList[pathNum - 1]);
				$('#postImg').data("path-number", pathNum);
			}
			
		}); // 화살표 클릭이벤트 끝
		
		// 관심 토글버튼
		$('#interestToggle').on('click', function(e) {
			e.preventDefault(); // <a> 페이지 올라가기 방지
			//alert("관심!");
			let postId = ${article.post.id}
			
			$.ajax({
				url:"/interest/" + postId
				
				, success:function(data) {
					if (data.code == 200) {
						// 토글 성공시 => 새로고침
						location.reload();
					} else if (data.code == 300) {
						// 로그인 만료시
						alert(data.error_message);
						location.href="/user/sign-in-view";
					} else {
						alert(data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
				}
			});//ajax 끝
		}); // 관심 토글이벤트 끝
	}); // 레디이벤트 끝
</script>

<%-- services와 clusterer, drawing 라이브러리 불러오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d00ddf1f01fe8fc317f5d28329baaf75&libraries=services,clusterer,drawing"></script>
<script>
var mapContainer = document.getElementById('map'); // 지도를 표시할 div 

// 지도 중심좌표
var lat = ${not empty article.post.latitude ? article.post.latitude : 1},
	lng = ${not empty article.post.longitude ? article.post.longitude : 1};

if (lat == 1 || lng == 1) {
	$('#map-div').addClass("d-none");
	lat = 37.566826;
	lng = 126.9786567;
}
	
var	tradeLocation = new kakao.maps.LatLng(lat, lng),
	mapOption = {
    center: tradeLocation, // 지도의 중심좌표
    level: 2 // 지도의 확대 레벨
};  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

searchAddrFromCoords(map.getCenter(), displayInfo); // 마커찍힌 지점의 정보를 왼쪽상단 창에 표시

// 마커 지점의 주소 가져오는 함수 -> 후처리까지
searchDetailAddrFromCoords(tradeLocation, function(result, status) {
	if (status === kakao.maps.services.Status.OK) {
        var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
        detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
        
        var content = '<div class="bAddr">' +
                        '<span class="title">법정동 주소정보</span>' + 
                        detailAddr + 
                    '</div>';

        // 마커를 클릭한 위치에 표시합니다 
        marker.setPosition(tradeLocation);
        marker.setMap(map);

        // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
        infowindow.setContent(content);
        infowindow.open(map, marker);
        
     	// 마커찍힌 지점의 정보를 왼쪽상단 창에 표시
        searchAddrFromCoords(map.getCenter(), displayInfo); 
    }   
});


//중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function() {
    searchAddrFromCoords(map.getCenter(), displayInfo);
});

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

//지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');

        for(var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === 'H') {
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }    
}
</script>