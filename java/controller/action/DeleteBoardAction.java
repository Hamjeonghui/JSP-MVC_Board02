package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BoardDAO;
import model.vo.BoardVO;

public class DeleteBoardAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;

		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();
		vo.setBid(Integer.parseInt(req.getParameter("bid")));

		if(dao.deleteBoard(vo)) {
			forward=new ActionForward();
			forward.setPath("main.do");
			forward.setRedirect(true);
		}

		return forward;
	}
}