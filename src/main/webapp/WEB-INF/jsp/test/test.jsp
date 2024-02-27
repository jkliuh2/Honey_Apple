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
	 		<input type="radio" name="tradeMethod" value="택배">
	 		택배
	 	</label>
	 	<label>
	 		<input type="radio" name="tradeMethod" value="직거래">
	 		직거래
	 	</label>
	</div>
	
	<%-- 직거래 input --%>
	<div>
		<input type="text" id="search" placeholder="키워드를 입력하세요.">
		<button type="button" id="searchBtn" class="btn btn-info">검색</button>
	</div>
	
	<%-- 클릭한 지역 주소 담기 --%>
	<div>
		<div>
			<input type="text" id="load-address-name" placeholder="도로명주소" class="form-control" readonly>
		</div>
		<div>
			<input type="text" id="address-name" placeholder="지번 주소" class="form-control" readonly>
		</div>
		<div>
			<input type="text" id="lat" placeholder="위도값" class="form-control" readonly>
		</div>
		<div>
			<input type="text" id="lng" placeholder="경도값" class="form-control" readonly>
		</div>
	</div>
	
	<%-- 확인 버튼 --%>
	<div>
		<button type="button" id="checkBtn" class="btn btn-primary">확인</button>
	</div>
 
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
// 지도
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

//현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
searchAddrFromCoords(map.getCenter(), displayCenterInfo);

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


//지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
            
            var content = '<div class="bAddr">' +
                            '<span class="title">법정동 주소정보</span>' + 
                            detailAddr + 
                        '</div>';

            // 마커를 클릭한 위치에 표시합니다 
            marker.setPosition(mouseEvent.latLng);
            marker.setMap(map);

            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
            infowindow.setContent(content);
            infowindow.open(map, marker);
            
            // 외부 input에 도로명주소+지번주소 표시
            document.getElementById('load-address-name').value = !!result[0].road_address ? result[0].road_address.address_name : '';
            document.getElementById('address-name').value = result[0].address.address_name;
            
            // 외부 input에 클릭한 지점의 위도,경도 넣기
            document.getElementById('lat').value = mouseEvent.latLng.getLat(); // 위도
            document.getElementById('lng').value = mouseEvent.latLng.getLng(); // 경도
        }   
    });
});

//중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function() {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
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

<script>
	$(document).ready(function() {
		
		// searchBtn 이벤트 -> 지도를 검색위치로 변경한다.
		$('#searchBtn').on('click', function() {
			//alert("검색");
			let keyword = $('#search').val(); // 검색내용.
			
			// 키워드 검색
			ps.keywordSearch(keyword, placesSearchCB); // 검색
		}); // 검색버튼 이벤트끝
		
		
		// 확인 버튼 이벤트 (위도, 경도 서버보내기)
		$('#checkBtn').on('click', function() {
			let lat = $('#lat').val();
			let lng = $('#lng').val();
			
			// 유효성 체크
			if (!lat || !lng) {
				alert("지도를 클릭하면 위치가 지정됩니다.");
				return;
			}
			
			// 서버로 위도, 경도 요청-응답 받기
			$.ajax({
				type:"POST"
				, url:"/test-geo"
				, data:{"latitude":lat, "longitude":lng}
			
				, success:function(data) {
					if (data.code == 200) {
						alert("성공");
					} else {
						alert("실패");
					}
				}
				, error:function() {
					alert("아예 실패");
				}
			}); // ajax 끝
		}); // 확인 버튼이벤트 끝
	}); // 레디이벤트 끝
</script>