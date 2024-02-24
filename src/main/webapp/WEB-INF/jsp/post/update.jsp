<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div">
	<div class="my-5">
		<h1>게시글 수정</h1>
	</div>

	<%-- subject --%>
	<div class="my-3">
		<input type="text" id="subject" name="subject" class="form-control" value="${post.subject}">
	</div>

	<%-- 가격 --%>
	<div class="my-3 d-flex align-items-center">
		<input type="number" id="price" class="form-control col-3">
		<div class="ml-1">,000 원</div>
		<label class="ml-3"> 
			<c:if test="${post.negotiable eq 0}">
			<input type="checkbox" id="negotiable">
			</c:if>
			<c:if test="${post.negotiable eq 1}">
			<input type="checkbox" id="negotiable" checked>
			</c:if>
			<span>네고가능</span>
		</label> 
		<span id="priceDisplay" class="ml-4 text-success">
			<fmt:formatNumber value="${post.price}" />원
		</span>
	</div>

	<%-- 글 내용 --%>
	<div class="my-5">
		<textarea id="content" name="content" rows="10" class="form-control">${post.content}</textarea>
	</div>
	
	<%-- 업로드 되어있는 이미지 미리보기 --%>
	<div class="mb-3">
		<span>- 이미 업로드된 이미지(삭제불가)</span>
	</div>
	<div class="d-flex">
		<img src="#" id="img1" width="80" height="80" class="ml-3 d-none">
		<img src="#" id="img2" width="80" height="80" class="ml-3 d-none">
		<img src="#" id="img3" width="80" height="80" class="ml-3 d-none">
		<img src="#" id="img4" width="80" height="80" class="ml-3 d-none">
		<img src="#" id="img5" width="80" height="80" class="ml-3 d-none">
	</div>

	<%-- 이미지 업로드 --%>
	<div class="mt-5">
		<p>- 이미지 파일 업로드(최소 1개의 사진이 필요합니다.)</p>
		<div id="img1-box" class="mb-2">
			*파일 1 : <input type="file" id="imgFile1" class="img-input"
				accept=".jpg, .png, .gif, .jpeg">
		</div>
		<div id="img2-box" class="mb-2 d-none">
			파일 2 : <input type="file" id="imgFile2" class="img-input"
				accept=".jpg, .png, .gif, .jpeg">
		</div>
		<div id="img3-box" class="mb-2 d-none">
			파일 3 : <input type="file" id="imgFile3" class="img-input"
				accept=".jpg, .png, .gif, .jpeg">
		</div>
		<div id="img4-box" class="mb-2 d-none">
			파일 4 : <input type="file" id="imgFile4" class="img-input"
				accept=".jpg, .png, .gif, .jpeg">
		</div>
		<div id="img5-box" class="mb-2 d-none">
			파일 5 : <input type="file" id="imgFile5" class="img-input"
				accept=".jpg, .png, .gif, .jpeg">
		</div>
	</div>

	<%-- 거래 희망 장소 --%>
	<div class="my-5"></div>

	<%-- 작성완료 버튼 --%>
	<div class="my-5 d-flex justify-content-end">
		<button type="button" id="updateBtn" class="btn btn-primary col-3 form-control">수정완료</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		///////////////////////// document 준비된 후 호출되는 함수들
		// price input 넣기
		$('#price').val(${post.price} / 1000); 
		// 기존 업로드 파일 처리
		var arr = [,,,,];
		arr[0] = "${post.imgPath1}"; // NULL이면 ""로 저장
		arr[1] = "${post.imgPath2}";
		arr[2] = "${post.imgPath3}";
		arr[3] = "${post.imgPath4}";
		arr[4] = "${post.imgPath5}";
		for (let i = 0; i < 5; i++) {
			if (arr[i] == "") {
				break;
			}
			// 이미 파일 존재하는 업로드버튼 비활성화 + 미리보기 띄우기
			$('#imgFile' + (i + 1)).prop("disabled", true);
			$('#img' + (i + 1)).attr("src", arr[i]);
			$('#img' + (i + 1)).removeClass("d-none");
			// 다음 업로드 div 보여주기
			$('#img' + (i + 2) + '-box').removeClass('d-none');
		}
		////////////////////////////// dom 로드 이후 실행되는 함수 끝
		
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
		}); // 가격input change이벤트 끝		
		
		
		
		/////// 글 수정 이벤트
		$('#updateBtn').on('click', function() {
			//alert("submit");
			
			// validation check
			let subject = $('#subject').val().trim();
			if (!subject) {
				alert("제목을 입력하세요.");
				$('#subject').focus();
				return;
			}
			
			let content = $('#content').val();
			if (!content) {
				alert("내용을 입력하세요.");
				$('#content').focus();
				return;
			}
			
			// 이미지 파일 유효성 검사(+ 확장자명 검사)
			for (let i = 2; i <= 5; i++) {
				let fileId = "#imgFile" + i;
				let fileName = $(fileId).val();
				
				// file == null이면 반복문탈출(어차피 이 뒤로도 전부 NULL일테니)
				if (!fileName) {
					break;
				}
				
				// 파일 확장자명 검사
				let extension = fileName.split(".").pop().toLowerCase();
				if ($.inArray(extension, ['jpg', 'png', 'gif', 'jpeg']) == -1) {
					alert(i + "번 파일 업로드 실패 : 이미지 파일만 업로드 할 수 있습니다.");
					$(fileId).focus();
					return;
				}
			}
			
			// 변수화 : postId, price, negotiable -> 유효성체크 + 변수화
			// postId
			let postId = ${post.id};
			
			// price
			let price = $('#price').val();
			if (!price) {
				alert("가격을 입력하세요.");
				$('#price').focus();
				return;
			}
			price = (price * 1000);
			
			// negotiable
			let negotiable = 0;
			if ($('#negotiable').is(':checked')) {
				// 체크 되었으면 1
				negotiable = 1;
			}

			
			// ajax - INSERT
			// params : (변수화O) subject, content, price, negotiable / (변수화X) imgFile1~5 
			let formData = new FormData();
			formData.append("postId", postId);
			formData.append("subject", subject);
			formData.append("content", content);
			formData.append("price", price);
			formData.append("negotiable", negotiable);
			formData.append("imgFile2", $('#imgFile2')[0].files[0]);
			formData.append("imgFile3", $('#imgFile3')[0].files[0]);
			formData.append("imgFile4", $('#imgFile4')[0].files[0]);
			formData.append("imgFile5", $('#imgFile5')[0].files[0]);
			
			$.ajax({
				type:"POST"
				, url:"/post/update"
				, data:formData
				, enctype:"multipart/form-data"
				, processData:false
				, contentType:false
				
				, success:function(data) {
					if (data.code == 200) {
						// 성공
						alert("게시글 수정을 완료했습니다.");
						location.href="/article/detail-view?postId=" + data.postId;
					} else {
						alert("게시글 생성 실패 : " + data.error_message);
					}
				}
				, error:function(request, status, error) {
					alert("게시글 생성에 실패했습니다. 관리자에게 문의해주세요.");
				}
			}); // ajax 끝		
		}); // 수정 이벤트 끝
	}); // 레디이벤트
</script>