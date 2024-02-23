<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="medium-size-div">
	<div class="my-5">
		<h1>수정</h1>
	</div>

	<%-- subject --%>
	<div class="my-3">
		<input type="text" id="subject" name="subject" class="form-control" value="${post.subject}">
	</div>

	<%-- 가격 --%>
	<div class="my-3 d-flex align-items-center">
		<input type="number" id="price" class="form-control col-3" value="${post.price / 1000}">
		<div class="ml-1">,000 원</div>
		<label class="ml-3"> 
			<input type="checkbox" id="negotiable">
			<span>네고가능</span>
		</label> 
		<span id="priceDisplay" class="ml-4 text-success"></span>
	</div>

	<%-- 글 내용 --%>
	<div class="my-5">
		<textarea id="content" name="content" rows="10" class="form-control"
			placeholder="내용을 입력하세요."></textarea>
	</div>

	<%-- 이미지 업로드 --%>
	<div class="my-3">
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
		<input type="submit" class="btn btn-primary col-3 form-control"
			value="작성완료">
	</div>
</div>