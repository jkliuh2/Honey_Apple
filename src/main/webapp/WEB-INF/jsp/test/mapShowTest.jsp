<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="medium-size-div">
	<%-- 지도영역 --%>
	<div class="mt-3">
		<div class="map_wrap">
		    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
		    <div class="hAddr">
		        <span class="title">지도중심기준 행정동 주소정보</span>
		        <span id="centerAddr"></span>
		    </div>
		</div>
	</div>
</div> 

<%-- services와 clusterer, drawing 라이브러리 불러오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d00ddf1f01fe8fc317f5d28329baaf75&libraries=services,clusterer,drawing"></script>

<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 

// 지도 중심좌표
var lat = 37.53856575833124;
var lng = 126.98839668906047;
mapOption = {
    center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
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

//마커를 중앙 위치에 표시합니다 
marker.setPosition(new kakao.maps.LatLng(lat, lng));
marker.setMap(map);

</script>   