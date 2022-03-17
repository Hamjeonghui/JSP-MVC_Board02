package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.MemberDAO;
import model.vo.MemberVO;

public class SignupAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;
		
		MemberDAO dao=new MemberDAO();
		MemberVO vo=new MemberVO();
		vo.setMid(req.getParameter("mid"));
		vo.setMname(req.getParameter("mname"));
		vo.setMpw(req.getParameter("mpw"));
		
		if(dao.insert(vo)) {
			forward=new ActionForward();
			forward.setPath("main.do");
			forward.setRedirect(true);
		}
		
		return forward;
	}

}