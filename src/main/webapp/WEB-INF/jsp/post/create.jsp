<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
				*파일 1 : <input type="file" id="imgFile1" class="img-input" name="imgFile1" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img2-box" class="mb-2 d-none">
				파일 2 : <input type="file" id="imgFile2" class="img-input" name="imgFile2" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img3-box" class="mb-2 d-none">
				파일 3 : <input type="file" id="imgFile3" class="img-input" name="imgFile3" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img4-box" class="mb-2 d-none">
				파일 4 : <input type="file" id="imgFile4" class="img-input" name="imgFile4" accept=".jpg, .png, .gif, .jpeg">
			</div>
			<div id="img5-box" class="mb-2 d-none">
				파일 5 : <input type="file" id="imgFile5" class="img-input" name="imgFile5" accept=".jpg, .png, .gif, .jpeg">
			</div>
		</div>
		
		<%-- 거래 희망 장소 --%>
		<div class="my-5">
		</div>
		
		<%-- 작성완료 버튼 --%>
		<div class="my-5 d-flex justify-content-end">
			<input type="submit" class="btn btn-primary col-3 form-control" value="작성완료">
		</div>
	</form>
		
	<%-- 시험용 버튼 --%>
	<div class="my-5">>
		<button type="button" id="testBtn">시험용 버튼</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 실험용
		$('#testBtn').on('click', function() {
			let str = "imgFile3";
			let num = Number(str.split("e")[1]);
			alert(num);
			let num2 = num + 1;
			alert(num2);
		});
		
		// 이미지파일 input change 이벤트
		$('.img-input').on('change', function(e) {
			let file = e.target.files[0];
			let idNum = Number($(this).attr("id").split("e")[1]); // id의 숫자값
			
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
			let priceDisplay = price + ",000원";
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
			alert("subject 검사 통과."); ///////////////////////////////
			
			let content = $('#content').val();
			if (!content) {
				alert("내용을 입력하세요.");
				$('#content').focus();
				return false;
			}
			alert("content 검사 통과"); ////////////////////////////////
			
			//let fileName1 = $('#imgFile1').val();
			//let fileName2 = $('#imgFile2').val();
			//let fileName3 = $('#imgFile3').val();
			//let fileName4 = $('#imgFile4').val();
			//let fileName5 = $('#imgFile5').val();
			for (let i = 1; i <= 5; i++) {
				alert("이미지 for문 진입"); ///////////////////////////////////////
				let fileId = "#imgFile" + i;
				alert("fileId:" + fileId); //////////////////////////////
				let fileName = $(fileId).val();
				alert("fileName:" + fileName); ////////////////////////////
				// 최소 1장은 업로드 해야 함.
				if (i = 1 && !fileName) {
					alert("최소 한 장의 상품 이미지는 업로드해야 합니다.");
					return false;
				}
				alert("file1번 검사완료"); /////////////////////////////////////
				
				if (!fileName) {
					return;
				}
				
				// 파일 확장자명 검사
				//accept=".jpg, .png, .gif, .jpeg"
				let extension = fileName.split(".").pop().toLowerCase();
		
				if ($.inArray(extension, ['jpg', 'png', 'gif', 'jpeg']) == -1) {
					alert("이미지 파일만 업로드 할 수 있습니다.");
					$(fileId).val(""); // 실패한 파일 비우기
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
				negotiable = 1;
			}
			
			return false;
		}); // submit 이벤트 끝
	}); // 레디이벤트 끝
</script>