<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${member!=null}">
		<form action="logout.do" method="post">
		<a href="main.do?mid=${member}">${memberName}님 환영합니다!</a>
		<input type="submit" value="로그아웃">
		</form>
	</c:when>
	<c:otherwise>
		<form action="login.do" method="post">
		 아이디: <input type="text" name="mid">
		 비밀번호:<input type="password" name="mpw">
		 <input type="submit" value="로그인">
		 <a href="signup.jsp">회원가입</a>
		</form>
	</c:otherwise>
</c:choose>