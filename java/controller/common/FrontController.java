package controller.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.ActionForward;
import controller.action.DeleteBoardAction;
import controller.action.DeleteReplyAction;
import controller.action.FavAction;
import controller.action.InsertBoardAction;
import controller.action.InsertReplyAction;
import controller.action.LoginAction;
import controller.action.LogoutAction;
import controller.action.MainAction;
import controller.action.SignupAction;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request, response);
	}
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String uri=request.getRequestURI();
		String cp=request.getContextPath();
		String command=uri.substring(cp.length());

		ActionForward forward=null;
		if(command.equals("/main.do")) {

			System.out.println("FC:메인으로 이동");
			try {
				forward=new MainAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(command.equals("/login.do")) {

			System.out.println("FC:로그인 시도");
			try {
				forward=new LoginAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(command.equals("/logout.do")) {

			System.out.println("FC:로그아웃 시도");
			try {
				forward=new LogoutAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(command.equals("/signup.do")) {

			System.out.println("FC:회원가입 요청");
			try {
				forward=new SignupAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else if(command.equals("/fav.do")) {

			System.out.println("FC:좋아요 요청");
			try {
				forward=new FavAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(command.equals("/insertReply.do")) {

			System.out.println("FC:댓글작성 요청");
			try {
				forward=new InsertReplyAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(command.equals("/insertBoard.do")) {

			System.out.println("FC:게시글작성 요청");
			try {
				forward=new InsertBoardAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(command.equals("/deleteReply.do")) {

			System.out.println("FC:댓글 삭제 요청");
			try {
				forward=new DeleteReplyAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(command.equals("/deleteBoard.do")) {

			System.out.println("FC:게시글삭제 요청");
			try {
				forward=new DeleteBoardAction().execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			System.out.println("잘못된 요청");
		}

		//성공 했을때만 디스패처 실행
		if(forward!=null) {
			
			//forward.getRedirect()원래 이건데 boolean이라 get아니고 is임
			
			//response.sendRedirect방식으로 가라고 그랬나?
			if(forward.isRedirect()) {//true
				response.sendRedirect(forward.getPath());
				//디스패처 하라고 안했으니까 이제 requset랑 response 새로 가져가겠지^^
			}else {//false
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());//이 경로로
				dispatcher.forward(request, response);//request, response 내용 갖고가! 	
			}
		} // forward가 null이 아니면 페이지가 이동해버려서 else처리 해주지 않아도 정상작동 된다.
		
		//이전에는 forward가 null일때 에러페이지로 갈 수 있도록 new처리 해주었지만
		// 지금은 <script>처리
		PrintWriter out=response.getWriter();
		out.println("<script>alert('요청처리 실패');history.go(-1);</script>");
		
	}
}
