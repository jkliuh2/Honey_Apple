<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="w-50">
	<div class="d-flex justify-content-center m-4">
		<h1>회원가입</h1>
	</div>
	
	<%-- 아이디 영역 --%>
	<div class="height-70-box">
		<%-- 아이디 input, 중복확인 버튼 --%>
		<div class="d-flex justify-content-between">
			<input type="text" id="loginId" class="form-control col-8" placeholder="아이디를 입력하세요.">
			<button type="button" id="loginIdCheckBtn" class="btn btn-info col-2">중복확인</button>
		</div>
		<%-- 아이디 중복확인 메세지 --%>
		<div class="mt-2">
			<small class="text-danger">중복된 아이디입니다.</small>
		</div>
	</div>
	
	<%-- 닉네임 영역 --%>
	<div class="height-70-box">
		<%-- 닉네임 input, 중복확인 버튼 --%>
		<div class="d-flex justify-content-between">
			<input type="text" id="nickname" class="form-control col-8" placeholder="닉네임을 입력하세요.">
			<button type="button" id="nicknameCheckBtn" class="btn btn-info col-2">중복확인</button>
		</div>
		<%-- 닉네임 중복확인 메세지 --%>
		<div class="mt-2">
			<small class="text-danger">중복된 아이디입니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 영역 --%>
	<div class="height-70-box">
		<input type="password" id="password" class="form-control col-8" placeholder="비밀번호">
		<%-- 비밀번호 메시지 --%>
		<div class="mt-2">
			<small class="text-danger">비밀번호가 너무 짧습니다.</small>
		</div>
	</div>
	
	<%-- 비밀번호 확인 영역 --%>
	<div class="height-70-box">
		<input type="password" id="passwordCheck" class="form-control col-8" placeholder="비밀번호 확인">
		<%-- 비밀번호 확인 메시지 --%>
		<div class="mt-2">
			<small class="text-danger">비밀번호가 일치하지 않습니다.</small>
		</div>
	</div>
	
	<%-- 이메일 영역 --%>
	<div class="height-70-box">
		<input type="text" id="email" class="form-control col-8" placeholder="이메일을 입력하세요.">
	</div>
	
	<%-- submit 버튼 --%>
	<div class="d-flex justify-content-center">
		<button type="button" id="signUpBtn" class="btn btn-primary form-control col-6">회원가입</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		
		// 회원가입
	}); // 레디이벤트 끝
</script>