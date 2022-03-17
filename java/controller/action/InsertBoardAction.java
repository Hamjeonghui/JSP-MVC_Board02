package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BoardDAO;
import model.vo.BoardVO;

public class InsertBoardAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;

		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();
		
		vo.setMid(req.getParameter("mid"));
		vo.setContent(req.getParameter("content"));
		
		
		if(dao.insertBoard(vo)) {
			forward=new ActionForward();
			forward.setPath("main.do");
			forward.setRedirect(true);
		}
		return forward;
	}
}