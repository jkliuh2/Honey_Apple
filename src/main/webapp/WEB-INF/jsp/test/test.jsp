<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="medium-size-div">
	<h1>테스트</h1>
	
	<%-- 지도영역 --%>
	<div id="map" style="width:500px;height:400px;"></div>
</div>



<%-- 지도그리는 jsAPI 가져오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d00ddf1f01fe8fc317f5d28329baaf75"></script>
<%-- services와 clusterer, drawing 라이브러리 불러오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=APIKEY&libraries=services,clusterer,drawing"></script>
<script>
	//지도를 띄우는 코드 작성
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
		level: 3 //지도의 레벨(확대, 축소 정도)
	};
	
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
</script>