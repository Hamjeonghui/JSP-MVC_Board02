<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.vo.*,java.util.*" %>
<jsp:useBean id="boardDAO" class="model.dao.BoardDAO" />
<jsp:useBean id="bvo" class="model.vo.BoardVO" />
<jsp:setProperty property="*" name="bvo"/>
<jsp:useBean id="rvo" class="model.vo.ReplyVO" />
<jsp:setProperty property="*" name="rvo"/>
<jsp:useBean id="memberDAO" class="model.dao.MemberDAO" />
<jsp:useBean id="mvo" class="model.vo.MemberVO" />
<jsp:setProperty property="*" name="mvo"/>
<%
	String action=request.getParameter("action");
	
	if(action.equals("main")){
		ArrayList<BoardSet> datas=boardDAO.selectAll(3);
		request.setAttribute("datas", datas);
		pageContext.forward("main.jsp");
	}
	else if(action.equals("fav")){
		if(boardDAO.update(bvo)){
			response.sendRedirect("controller.jsp?action=main");
		}
		else{
			throw new Exception("좋아요 처리중에 문제발생!");
		}
	}
	else if(action.equals("login")){
		MemberVO data=memberDAO.selectOne(mvo);
		if(data==null){
			out.println("<script>alert('로그인 실패!');history.go(-1);</script>");
		}
		else{
			session.setAttribute("member", data.getMid());
			response.sendRedirect("controller.jsp?action=main");
		}
	}
	else if(action.equals("logout")){
		session.invalidate();
		///// session.removeAttribute("member");
		response.sendRedirect("controller.jsp?action=main");
	}
	else if(action.equals("signup")){
		// 회원가입 요청
		if(memberDAO.insert(mvo)){
			out.println("<script>alert('회원가입 완료!');window.close();</script>");
		}
		else{
			out.println("<script>alert('회원가입 실패!!!');history.go(-1);</script>");
		}
	}
	else if(action.equals("insertReply")){
		if(boardDAO.insertReply(rvo)){
			response.sendRedirect("controller.jsp?action=main");
		}
		else{
			throw new Exception("댓글 작성중에 문제발생!");
		}
	}
	else if(action.equals("insertBoard")){
		if(boardDAO.insertBoard(bvo)){
			response.sendRedirect("controller.jsp?action=main");
		}
		else{
			throw new Exception("글 작성중에 문제발생!");
		}
	}
	else if(action.equals("deleteReply")){
		if(boardDAO.deleteReply(rvo)){
			response.sendRedirect("controller.jsp?action=main");
		}
		else{
			throw new Exception("댓글 삭제중에 문제발생!");
		}
	}
	else if(action.equals("deleteBoard")){
		if(boardDAO.deleteBoard(bvo)){
			response.sendRedirect("controller.jsp?action=main");
		}
		else{
			throw new Exception("글 삭제중에 문제발생!");
		}
	}
%>