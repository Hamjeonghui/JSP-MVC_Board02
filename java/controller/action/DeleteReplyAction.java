package controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BoardDAO;
import model.vo.ReplyVO;

public class DeleteReplyAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=null;
		
		BoardDAO dao=new BoardDAO();
		ReplyVO vo=new ReplyVO();
		vo.setRid(Integer.parseInt(req.getParameter("rid")));
		vo.setBid(Integer.parseInt(req.getParameter("bid")));
		
		if(dao.deleteReply(vo)) {
			forward=new ActionForward();
			forward.setPath("main.do");
			forward.setRedirect(false);
		}
		
		return forward;
	}

}