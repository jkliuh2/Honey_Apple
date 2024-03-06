<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="medium-size-div">
	<hr>
	<div class="d-flex justify-content-center my-5">
		<h1>달달한 인기매물</h1>
	</div>
	
	<%-- articleList --%>
	<div id="content">
		<jsp:include page="../article/articleList.jsp" />
	</div>
	
	<%-- 더보기 링크 --%>
	<div class="d-flex justify-content-center mt-3">
		<a href="/honey-apple/search-view"><h4>더 살펴보기 --></h4></a>
	</div>
</div>