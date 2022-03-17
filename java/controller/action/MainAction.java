package controller.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.BoardDAO;
import model.vo.BoardSet;
import model.vo.BoardVO;

public class MainAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ActionForward forward=new ActionForward();

		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();

		System.out.println(req.getParameter("bid"));
		System.out.println(req.getParameter("mid"));
		
		// # 댓글작성, 댓글삭제, 좋아요 기능 사용시 bid를 참조하여 그 글의 상세목록을 메인에 출력! #

		//앞에서 bid 넘어온거 있어?
		if(req.getParameter("bid")!=null) {

			System.out.println("게시글 selectOne 보기");
			vo.setBid(Integer.parseInt(req.getParameter("bid")));
			ArrayList<BoardSet> data = dao.selectOne(vo);
			req.setAttribute("data", data);
			System.out.println(data);

		} else {//bid넘어온거 없으면 전체목록 or 내가 쓴 글 메인에 출력!

			System.out.println("게시글 selectAll 보기");

			//view에서 "더보기" 누르면 작동할 수 있도록!
			int b= 3;//main에 표시될 게시글 개수! 일단 처음엔 3개 보여주고,
			if(req.getParameter("b")!=null) {//"더보기(${b+2})를 누르면"
				b= Integer.parseInt(req.getParameter("b")); //그럼 이제 걔가 b겠지
			}
			//view에서 ${b}쓰니까 여기서 b가 누군지 정의
			req.setAttribute("b", b);
			System.out.println("b"+b);//로그

			// 세션으로 부른애 말고! 파라미터로 불러오는애로 넣자!
			// 세션으로 넣으면 한 번 로그인하면 null이 없는데? 계속 선택보기 될텐데???
			vo.setMid(req.getParameter("mid"));
			ArrayList<BoardSet> datas=dao.selectAll(vo, b);
			req.setAttribute("datas", datas);
			req.setAttribute("mid", req.getParameter("mid"));
			System.out.println(datas);

		}

		//datas나 data가지고 메인으로 곧장 가
		forward.setPath("main.jsp");
		forward.setRedirect(false);

		return forward;
	}

}