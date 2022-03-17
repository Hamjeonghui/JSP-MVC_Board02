package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.MemberDAO;
import model.vo.MemberVO;

public class LoginAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;

		MemberDAO dao=new MemberDAO();
		MemberVO vo=new MemberVO();

		vo.setMid(req.getParameter("mid"));
		vo.setMpw(req.getParameter("mpw"));
		vo=dao.selectOne(vo);

		if(vo!=null) {//성공하면
			//세션에 mid저장!
			forward=new ActionForward();
			HttpSession session= req.getSession();
			session.setAttribute("member", vo.getMid());
			session.setAttribute("memberName", vo.getMname());//"ㅇㅇ"님 환영합니다 하려고
			forward.setPath("main.do");
			forward.setRedirect(true);	
		}
		// 아이디가 없거나 비번불일치등의 이유로 login실패할때, mvo는 null이다.
		// -> forward가 null
		return forward;
	}
}