<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${titleName}</title>
<%-- jQuery --%>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<%-- 부트스트랩 --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
<%-- 내가 만든 스타일시트 --%>
<link rel="stylesheet" type="text/css" href="/static/css/style.css">
</head>
<body>
	<div id="wrap" class="container">
		<%-- header --%>
		<header>
			<jsp:include page="../include/header.jsp" />
		</header>
		
		<%-- 검색창(일부 페이지만 존재) --%>
		<nav>
			<jsp:include page="../include/navSearch.jsp" />
		</nav>
		
		<%-- section --%>
		<section class="contents d-flex justify-content-center">
			<jsp:include page="../${viewName}.jsp" />
		</section>
		
		<%-- footer --%>
		<footer class="d-flex justify-content-center">
			<jsp:include page="../include/footer.jsp" />
		</footer>
	</div>
</body>
</html>