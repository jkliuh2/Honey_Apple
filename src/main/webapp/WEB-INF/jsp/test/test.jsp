<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 
필요한 기능 : 
 --%>
<div class="medium-size-div">
	<h1>테스트</h1>
	
	<%-- 선택 방식 --%>
	<div class="mt-5">
		<label>
	 		<input type="radio" name="tradeMethod" value="parcel">
	 		택배
	 	</label>
	 	<label>
	 		<input type="radio" name="tradeMethod" value="direct">
	 		직거래
	 	</label>
	</div>
	
	<%-- 직거래 input --%>
	<div>
		<input type="text" id="search" placeholder="키워드를 입력하세요.">
		<button type="button" id="searchBtn" class="btn btn-info">검색</button>
	</div>
 
	<%-- 지도영역 --%>
	<div class="mt-3">
		<div id="map" style="width:100%;height:400px;"></div>
	</div>
</div>



<%-- services와 clusterer, drawing 라이브러리 불러오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d00ddf1f01fe8fc317f5d28329baaf75&libraries=services,clusterer,drawing"></script>

<script>

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

<!--
// 키워드로 장소를 검색합니다
ps.keywordSearch('이태원 맛집', placesSearchCB); 
-->

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}
</script>

<script>
	$(document).ready(function() {
		
		// searchBtn 이벤트
		$('#searchBtn').on('click', function() {
			//alert("검색");
			let keyword = $('#search').val(); // 검색내용.
			
			// 키워드 검색
			ps.keywordSearch(keyword, placesSearchCB); // 검색
		}); // 검색버튼 이벤트끝
	});
</script>