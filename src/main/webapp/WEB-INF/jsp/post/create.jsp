<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="medium-size-div">
	<div class="my-5">
		<h1>게시글 쓰기</h1>
	</div>
	
	<form id="postCreate" method="POST" action="/post/create">
		<%-- subject --%>
		<div class="my-3">
			<input type="text" id="subject" name="subject" class="form-control" placeholder="제목을 입력하세요.">
		</div>
		
		<%-- 가격 --%>
		<div class="my-3 d-flex align-items-center">
			<input type="number" id="price" class="form-control col-3" placeholder="가격">
			<div class="ml-1">
				,000 원
			</div>
			<label class="ml-3">
				<input type="checkbox" id="negotiable">
				<span>네고가능</span>
			</label>
			<span id="priceDisplay" class="ml-4 text-success"></span>
		</div>
		
		<%-- 글 내용 --%>
		<div class="my-5">
			<textarea id="content" name="content" rows="10" class="form-control" placeholder="내용을 입력하세요."></textarea>
		</div>
		
		<%-- 이미지 업로드 --%>
		<div class="my-3">
			<p>- 이미지 파일 업로드(최소 1개의 사진이 필요합니다.)</p>
			<div id="img1-box" class="mb-2">
				*파일 1 : <input type="file" id="imgFile1" class="img-input" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img2-box" class="mb-2 d-none">
				파일 2 : <input type="file" id="imgFile2" class="img-input" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img3-box" class="mb-2 d-none">
				파일 3 : <input type="file" id="imgFile3" class="img-input" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img4-box" class="mb-2 d-none">
				파일 4 : <input type="file" id="imgFile4" class="img-input" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img5-box" class="mb-2 d-none">
				파일 5 : <input type="file" id="imgFile5" class="img-input" accept=".jpg, .png, .gif, .jpeg">
			</div>
		</div>
		
		<%-- 거래 방식 --%>
		<div class="my-3">
			거래 방식 : 
			<label class="ml-2">
		 		<input type="radio" name="tradeMethod" value="택배" checked>
		 		택배
		 	</label>
		 	<label class="ml-2">
		 		<input type="radio" name="tradeMethod" value="직거래">
		 		직거래
		 	</label>
		</div>
		
		<%-- 지도(직거래 선택시) --%>
		<div id="map-div" class="mt-3">
			<%-- 주소 검색기 --%>
			<div class="m-3 d-flex">
				<input class="col-4 form-control" type="text" id="search" placeholder="키워드를 입력하세요.">
				<button type="button" id="searchBtn" class="btn btn-info col-1 form-control">검색</button>
			</div>
			
			<%-- 지도 --%>
			<div class="map_wrap">
			    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
			    <div class="hAddr">
			        <span class="title">지도중심기준 행정동 주소정보</span>
			        <span id="centerAddr"></span>
			    </div>
			</div>
			
			<%-- 마커의 정보 --%>
			<div class="mt-3">
				<div>
					<input type="text" id="load-address-name" placeholder="도로명주소(존재하지 않을 수도 있습니다.)" class="address-input form-control w-50 d-none" readonly>
				</div>
				<div>
					<input type="text" id="address-name" placeholder="지번 주소" class="address-input form-control w-50 d-none" readonly>
				</div>
				<div>
					<input type="text" id="lat" name="latitude" placeholder="위도값" class="form-control d-none" readonly>
					<input type="text" id="lng" name="longitude" placeholder="경도값" class="form-control d-none" readonly>
				</div>
			</div>
		</div>
		
		<%-- 작성완료 버튼 --%>
		<div class="my-5 d-flex justify-content-end">
			<input type="submit" class="btn btn-primary col-3 form-control" value="작성완료">
		</div>
	</form>
</div>

<script>
	$(document).ready(function() {
		
		// 거래방식 radio change 이벤트
		$('input[name=tradeMethod]').on('change', function() {
			let tradeMethod = $(this).val();
			// 직거래 -> 지도 띄우기. 택배 -> 지도 없애기
			if (tradeMethod == '택배') {
				$('.address-input').addClass("d-none");
			} else {
				$('.address-input').removeClass("d-none");
			}
		}); // radio이벤트 끝
		
		// 주소검색 이벤트 -> 지도를 검색위치로 변경한다.
		$('#searchBtn').on('click', function() {
			//alert("검색");
			let keyword = $('#search').val(); // 검색내용.
			
			// 키워드 검색
			ps.keywordSearch(keyword, placesSearchCB); // 검색
		}); // 검색버튼 이벤트끝
		
		// 이미지파일 input change 이벤트
		$('.img-input').on('change', function(e) {
			let file = e.target.files[0];
			let idNum = Number($(this).attr("id").split("e")[1]); // id의 숫자값
			
			// 업로드되있던 파일을 취소했을 때.
			if (file == null) {
				// 해당 input 이하의 모든 input들을 비워야 한다.
				for (let i = idNum; i < 5; i++) {
					// input 비우기
					$('#imgFile' + (i + 1)).val("");
					
					// input div 감추기
					$('#img' + (i + 1) + '-box').addClass('d-none');
				}
				return;
			}
			if (idNum == 5) {
				// 마지막 이미지면 그냥 return
				return;
			}
			
			// 다음 업로드 div 보여주기
			$('#img' + (idNum + 1) + '-box').removeClass('d-none');
			
		}); // 이미지input change끝
		
		
		// 가격 change 이벤트
		$('#price').on('change', function() {
			//alert("가격")	;
			
			// 가격 메시지 삭제
			$('#priceDisplay').text("");
			
			// 유효성 체크
			let price = $(this).val();
			if (!price) {
				return;
			}
			if (price <= 0) {
				alert("불가능한 가격입니다.");
				$(this).val("");
				return;
			}
			
			// 메시지 작성
			let koPrice = new Intl.NumberFormat('en-US').format(price);
			let priceDisplay = koPrice + ",000원";
			$('#priceDisplay').text(priceDisplay);
		});
		
		
		// submit 이벤트
		$('#postCreate').on('submit', function(e) {
			//alert("submit");
			e.preventDefault();
			
			// validation check
			let subject = $('#subject').val().trim();
			if (!subject) {
				alert("제목을 입력하세요.");
				$('#subject').focus();
				return false;
			}
			
			let content = $('#content').val();
			if (!content) {
				alert("내용을 입력하세요.");
				$('#content').focus();
				return false;
			}
			
			// 이미지 파일 유효성 검사(+ 확장자명 검사)
			for (let i = 1; i <= 5; i++) {
				let fileId = "#imgFile" + i;
				let fileName = $(fileId).val();
				
				// 최소 1장은 업로드 해야 함.
				if (!fileName) {
					if (i == 1) {
						alert("최소 한 장의 상품 이미지는 업로드해야 합니다.");
						return false;
					}
					// 2번부터 파일 비어있으면 반복문 탈출(확장자명 검사 X)
					break;
				}
				
				// 파일 확장자명 검사
				let extension = fileName.split(".").pop().toLowerCase();
				if ($.inArray(extension, ['jpg', 'png', 'gif', 'jpeg']) == -1) {
					alert(i + "번 파일 업로드 실패 : 이미지 파일만 업로드 할 수 있습니다.");
					$(fileId).focus();
					return false;
				}
			}
			
			// name 처리 안한 값들 : price, negotiable -> 유효성체크 + 변수화
			// price
			let price = $('#price').val();
			if (!price) {
				alert("가격을 입력하세요.");
				$('#price').focus();
				return false;
			}
			price = (price * 1000);
			
			// negotiable
			let negotiable = 0;
			if ($('#negotiable').is(':checked')) {
				// 체크 되었으면 1
				negotiable = 1;
			}
			
			// 거래방식 및 위도-경도
			let tradeMethod = $('input[name=tradeMethod]:checked').val();
			let latitude = $('#lat').val();
			let longitude = $('#lng').val();
			if (tradeMethod == '택배') {
				latitude = "";
				longitude = "";
			} else if (!latitude || !longitude) {
				// 직거래 -> 위-경도 미선택시 유효성체크
				alert("지도를 클릭하면 거래위치를 설정할 수 있습니다.");
				return false;
			}
			
			
			// ajax - INSERT
			// params : (변수화O) subject, content, price, negotiable / (변수화X) imgFile1~5 
			let formData = new FormData();
			formData.append("subject", subject);
			formData.append("content", content);
			formData.append("price", price);
			formData.append("negotiable", negotiable);
			formData.append("imgFile1", $('#imgFile1')[0].files[0]);
			formData.append("imgFile2", $('#imgFile2')[0].files[0]);
			formData.append("imgFile3", $('#imgFile3')[0].files[0]);
			formData.append("imgFile4", $('#imgFile4')[0].files[0]);
			formData.append("imgFile5", $('#imgFile5')[0].files[0]);
			formData.append("tradeMethod", tradeMethod);
			formData.append("latitude", latitude);
			formData.append("longitude", longitude);
			
			$.ajax({
				type:"POST"
				, url:"/post/create"
				, data:formData
				, enctype:"multipart/form-data"
				, processData:false
				, contentType:false
				
				, success:function(data) {
					if (data.code == 200) {
						// 성공
						location.href="/article/detail-view?postId=" + data.postId;
					} else {
						alert("게시글 생성 실패 : " + data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("게시글 생성에 실패했습니다. 관리자에게 문의해주세요.");
				}
			}); // ajax 끝
			
		}); // submit 이벤트 끝
	}); // 레디이벤트 끝
</script>

<%-- services와 clusterer, drawing 라이브러리 불러오기 --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d00ddf1f01fe8fc317f5d28329baaf75&libraries=services,clusterer,drawing"></script>

<script>
//지도
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

//키워드 검색 완료 시 호출되는 콜백함수 입니다
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