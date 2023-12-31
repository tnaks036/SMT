package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;

import Model.Ans.Ans;
import Model.Board.Board;
import Model.Image.Image;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}

	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length());
		String site = null;
		
		Board board = new Board(); //게시판 query
		Image img = new Image();
		Ans ans = new Ans();
		Gson gson = new Gson();
		
		switch(command) {
			case "/boardPage" : 
				site = "/WEB-INF/Board/Board_List.jsp";
				break;
				
			case "/goBoard" : // 게시판
				String boardList = gson.toJson(board.goBoard(request, response));
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(boardList);
		        return;
				
			case "/insertBoardFrm" : //게시판 등록폼
				site = "/WEB-INF/Board/Board_Insert.jsp";
				break;
		
			case "/insertBoard" : //DB에 글 등록
				board.insBoard(img.fileUpload(request),response); //이미지등록
//				img.delImg();
				site = "boardPage";

				response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        out.println("<script>alert('등록이 완료되었습니다.'); location.href='" + site + "';</script>");
		        out.flush();
				break;
		
			case "/updateFrm" : //게시판 수정폼
				site = board.updateFrm(request, response);
				break;
				
			case "/updateBoard" : //수정 DB에 올리기
				MultipartRequest mul = img.fileUpload(request);
				board.updBoard(mul, response);
				site = "BoardInfo?board_ID=" + mul.getParameter("board_ID") ;
				response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out1 = response.getWriter();
		        out1.println("<script>alert('등록이 완료되었습니다.'); location.href='" + site + "';</script>");
		        out1.flush();
				break;
				
			case "/BoardInfo" : //게시판 상세보기
				site = board.boardInfo(request, response);
				if(site == "") {
					response.setContentType("text/html;charset=UTF-8");
			        PrintWriter out2 = response.getWriter();
			        out2.println("<script>alert('존재하지 않는 게시글입니다.'); location.href='boardPage';</script>");
			        out2.flush();
				}
				break;
				
			case "/deleteBoard" : //글삭제
//				site = board.deleteBoard(request, response);
				String resList = gson.toJson(board.deleteBoard(request, response));
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(resList);
				break;
				
			// 댓글
			case "/insAns" : //댓글등록
				ans.insAns(img.fileUpload(request), response);
//				img.delImg();
				return;
			
			case "/listAns" : //댓글 가져오기 (로딩, 댓글등록)
		        String resList2 = gson.toJson(ans.listAns(img.fileUpload(request), response));
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(resList2);
		        return;
	        
			case "/delAns" : //댓글 삭제
				ans.delAns(request, response);
				return;
			
			case "/updAns" : //댓글 수정
				ans.updAns(img.fileUpload(request), response);
				return;
 		}
		
		if (site != null && !response.isCommitted()) {
		    RequestDispatcher dispatcher = request.getRequestDispatcher(site);
		    dispatcher.forward(request, response);
		}
	}
}
