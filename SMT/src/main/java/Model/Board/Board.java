package Model.Board;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import Model.DataBase;
import Model.Image.Image;
import Model.Page.Page;
import VO.BoardQVO;
import VO.PageNum;

public class Board {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String query;
	
	private String board_ID = "";
	private String comment_ID = "";
	private String title = "";
	private String contents = "";
	private String file_Name;
	
	private String condition = "";
	private String searchKeyword = "";
	
	int nowNum;
	
	DataBase db = new DataBase();
	Page page = new Page();
	Image img = new Image();
	
//	public String goBoard(HttpServletRequest request, HttpServletResponse response) {
	public Map<String, Object> goBoard(HttpServletRequest request, HttpServletResponse response) {
		
		PageNum pn = new PageNum();
		if(request.getParameter("nowNum") != null) {
			nowNum = Integer.parseInt(request.getParameter("nowNum"));
		}

		pn.setNowNum(nowNum);
		pn.setListNum(page.getPageNum(request, response));
		
		
		PageNum allPn = page.setPage(pn);
		
		List<BoardQVO> list = new ArrayList<BoardQVO>();
		Map<String, Object> result = new HashMap<>();
		// 게시판으로 페이지 이동
		query = "SELECT "
				+ " cq.Board_ID as Board_ID, "
				+ " cq.Comment_ID as Comment_ID, "
				+ " cq.Title as Title, "
				+ " FORMAT(cq.Ins_Date_Time, 'yy-MM-dd') as Ins_Date_Time, "
				+ " count(ca.board_ID) as answerCnt"
				+ " FROM CS_Ques cq "
				+ " LEFT JOIN CS_ANS ca ON cq.Board_ID = ca.Board_ID "
				+ " WHERE (cq.Del_Yn <> 'Y' OR cq.Del_Yn IS NULL) ";
		
		condition = request.getParameter("condition");
		searchKeyword = request.getParameter("searchKeyword");
		if(searchKeyword != "" && condition != null) {
			switch(condition) {
			case "title" :
				query += " AND cq.title LIKE '%" + searchKeyword +"%' ";
				break;
				
			case "contents" :
				query += " AND cq.contents LIKE '%" + searchKeyword +"%' ";
				break;

			case "comment_ID" :
				query += " AND cq.comment_ID LIKE '%" + searchKeyword +"%' ";
				break;
			}
		}
			query += " GROUP BY cq.Board_ID, cq.Comment_ID,  cq.Title, cq.Ins_Date_Time "
					+ " ORDER BY cq.Ins_Date_Time DESC "
					+ " OFFSET "
					+ allPn.getDbNum()
					+ " ROWS FETCH NEXT "
					+ allPn.getPageSize()
					+ " ROWS ONLY ";

		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				BoardQVO qvo = new BoardQVO();
				qvo.setBoard_ID(rs.getInt("Board_ID"));
				qvo.setComment_ID(rs.getString("Comment_ID"));
				qvo.setTitle(rs.getString("Title"));
				qvo.setIns_Date_Time(rs.getString("Ins_Date_Time"));
				qvo.setAnswerCnt(rs.getInt("answerCnt"));
				
				list.add(qvo);
			}
			
			result.put("list", list);
			result.put("pageInfo", allPn);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(rs);
			db.close(ps);
			db.close(con);
		}
		
		return result;
	}

	public void insBoard(MultipartRequest multi, HttpServletResponse response){
		//DB에 글 등록
		query = "INSERT INTO CS_QUES (Comment_ID,Title,Contents,File_Name,Ins_Date_Time) "
				+ " VALUES(?,?,?,?,GETDATE()) ";
		
		comment_ID = multi.getParameter("comment_ID");
		title = multi.getParameter("title");
		contents = multi.getParameter("content");
		file_Name = multi.getFilesystemName("file_Name");
		try {
			con = db.getConnection();
			
			ps = con.prepareStatement(query);
			ps.setString(1, comment_ID);
			ps.setString(2, title);
			ps.setString(3, contents);
			ps.setString(4, file_Name);
			
			ps.executeUpdate();
			
			db.close(ps);
			db.close(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String updateFrm(HttpServletRequest request, HttpServletResponse response) {
		board_ID = request.getParameter("board_ID");
		
		// 게시판 수정폼으로 이동
		query = "SELECT "
				+ " Board_ID, "
				+ " Comment_ID, "
				+ " Title, "
				+ " contents, "
				+ " Ins_Date_Time "
				+ " FROM CS_Ques "
				+ " WHERE Board_ID = " + board_ID;
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			BoardQVO qvo = new BoardQVO();
			
			if(rs.next()) {
				qvo.setBoard_ID(rs.getInt("Board_ID"));
				qvo.setComment_ID(rs.getString("Comment_ID"));
				qvo.setTitle(rs.getString("Title"));
				qvo.setContents(rs.getString("Contents"));
				qvo.setIns_Date_Time(rs.getString("Ins_Date_Time"));
			}

			request.setAttribute("list", qvo);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(rs);
			db.close(ps);
			db.close(con);
		}
		
		return "/WEB-INF/Board/Board_Update.jsp";
	}	
	
	public void updBoard(MultipartRequest multi, HttpServletResponse response){
		//DB에 글 수정내역 업데이트
		query = "UPDATE CS_Ques "
				+ " SET Title = ? "
				+ " , Contents = ? ";
				
		board_ID = multi.getParameter("board_ID");
		title = multi.getParameter("title");
		contents = multi.getParameter("content");
		file_Name = multi.getFilesystemName("file_Name");
		try {
			con = db.getConnection();
			
			
		    if (file_Name != null) {
		        query += " , File_Name = ? "
						+ " , Upd_Date_Time = GETDATE()"
						+ " WHERE Board_ID = " + board_ID;

		        ps = con.prepareStatement(query);
		        ps.setString(1, title);
		        ps.setString(2, contents);
				ps.setString(3, file_Name);
			
				
			}else {
				query +=  " , Upd_Date_Time = GETDATE()"
					  + " WHERE Board_ID = " + multi.getParameter("board_ID");
				ps = con.prepareStatement(query);
		        ps.setString(1, title);
		        ps.setString(2, contents);
			}
			
		    String beforeFile = img.getFileDataFromDatabase(board_ID);
		    
			ps.executeUpdate();
			
			img.delfile(beforeFile);
			
			db.close(ps);
			db.close(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String, String> deleteBoard(HttpServletRequest request, HttpServletResponse response) {
		board_ID = request.getParameter("board_ID");
		
		query = " UPDATE CS_Ques"
				+ " SET Del_Yn = 'Y' "
				+ " , Del_Date_Time = GetDate() "
				+ " WHERE Board_ID = ? "
				+ " AND (Del_Yn <> 'Y' OR Del_Yn IS NULL)";
		
		Map<String, String> list = new HashMap<>();
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(board_ID));

			if(ps.executeUpdate() > 0) {
				list.put("result", "suc");
				img.delfile(img.getFileDataFromDatabase(board_ID));
			}else {
				list.put("result", "fail");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}

		return list;
	}

	public String boardInfo(HttpServletRequest request, HttpServletResponse response) {
		// 게시판 상세로 이동
		query = "SELECT * FROM ("
				+ " SELECT "
				+ " cq.Board_ID as Board_ID, "
				+ " cq.Comment_ID as Comment_ID, "
				+ " cq.Title as Title, "
				+ " cq.contents as contents, "
				+ " cq.File_Name as File_Name, "
				+ " FORMAT(cq.Ins_Date_Time, 'yy-MM-dd HH:mm') as Ins_Date_Time, "
				+ " count(ca.board_ID) as answerCnt, "
				+ " LAG(cq.Title, 1, '이전 행이 없습니다.') OVER(ORDER BY cq.Ins_Date_Time) as preTitle, "
				+ " LEAD(cq.Title, 1, '다음 행이 없습니다.') OVER(ORDER BY cq.Ins_Date_Time)as nextTitle, "
				+ " LAG(cq.Board_ID, 1) OVER(ORDER BY cq.Ins_Date_Time) as preBoard_ID, "
				+ " LEAD(cq.Board_ID, 1) OVER(ORDER BY cq.Ins_Date_Time)as nextBoard_ID "
				+ " FROM CS_Ques cq"
				+ " LEFT JOIN CS_ANS ca ON cq.Board_ID = ca.Board_ID "
				+ " WHERE cq.Del_Yn <> 'Y' OR cq.Del_Yn IS NULL "
				+ " GROUP BY cq.Board_ID, cq.Comment_ID, cq.contents, cq.File_Name, cq.Title, cq.Ins_Date_Time "
				+ " ) AS boardInfoTable "
				+ " WHERE Board_ID = "+ request.getParameter("board_ID");
		
//		System.out.println(query);
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			BoardQVO qvo = new BoardQVO();
			
			if(rs.next()) {
				qvo.setBoard_ID(rs.getInt("Board_ID"));
				qvo.setComment_ID(rs.getString("Comment_ID"));
				qvo.setTitle(rs.getString("Title"));
				qvo.setContents(rs.getString("contents").replace("\r\n", "<br>")); //엔터 바꾸기
				qvo.setFile_Name(rs.getString("File_Name"));
				qvo.setIns_Date_Time(rs.getString("Ins_Date_Time"));
				
				if(qvo.getFile_Name() != null) {
					int DotIndex = qvo.getFile_Name().lastIndexOf('.');
					if (DotIndex > 0) {
						String extension = qvo.getFile_Name().substring(DotIndex + 1);
						qvo.setFile_Extension(extension);
					}
				}
			}
			
			request.setAttribute("list", qvo);
			request.setAttribute("preTitle", rs.getString("preTitle"));
			request.setAttribute("nextTitle", rs.getString("nextTitle"));
			request.setAttribute("preBoard_ID", rs.getString("preBoard_ID"));
			request.setAttribute("nextBoard_ID", rs.getString("nextBoard_ID"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(rs);
			db.close(ps);
			db.close(con);
		}
		
		return "/WEB-INF/Board/Board_Info.jsp";
	}	
}
