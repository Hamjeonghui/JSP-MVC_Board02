package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BoardDAO;
import model.vo.BoardVO;

public class FavAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;
		
		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();
		vo.setBid(Integer.parseInt(req.getParameter("bid")));
		req.setAttribute("bid", vo);
		
		if(dao.update(vo)) {
			forward=new ActionForward();
			forward.setPath("main.do");
			//내가 아는 bid를 main에서 알 필요가 있어!
			forward.setRedirect(false);
		}
		
		return forward;
	}

}