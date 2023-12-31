package Model.Ans;

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
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import Model.DataBase;
import Model.Image.Image;
import VO.BoardAVO;

public class Ans {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String query;
	
	private String ans_ID = "";
	private String comment_ID = "";
	private String Answer_ID = "";
	private String contents = "";
	private String file_Name;
	
	Image img = new Image();
	
	DataBase db = new DataBase();
	
	public void insAns(MultipartRequest multi, HttpServletResponse response) {
		query = "INSERT INTO CS_ANS (BOARD_ID, COMMENT_ID, ANSWER_ID, CONTENTS, FILE_NAME, INS_DATE_TIME) "
				+ " VALUES(?,?,?,?,?,GETDATE())";
		
		String board_ID = multi.getParameter("board_ID");
		comment_ID = multi.getParameter("comment_ID");
		Answer_ID = multi.getParameter("answerID");
		contents = multi.getParameter("ansContents");
		file_Name = multi.getFilesystemName("file_Name");

		try {
			con = db.getConnection();
			
			ps = con.prepareStatement(query);
			ps.setString(1, board_ID);
			ps.setString(2, comment_ID);
			ps.setString(3, Answer_ID);
			ps.setString(4, contents);
			ps.setString(5, file_Name);
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
	}
	
	public List<BoardAVO> listAns(MultipartRequest multi, HttpServletResponse response){
		ans_ID = multi.getParameter("ans_ID");
		
		query = "SELECT ANS_ID, "
				+ " COMMENT_ID, "
				+ " ANSWER_ID, "
				+ " CONTENTS, "
				+ " FILE_NAME, "
				+ " FORMAT(INS_DATE_TIME, 'yy-MM-dd HH:mm:ss') AS INS_DATE_TIME "
				+ " FROM CS_ANS "
				+ " WHERE Board_ID = " + multi.getParameter("board_ID")
				+ " AND (Del_Yn <> 'Y' OR Del_Yn IS NULL)  "
				+ " ORDER BY INS_DATE_TIME "
				;
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
		
			List<BoardAVO> list = new ArrayList<BoardAVO>();
			
			while(rs.next()) {
				BoardAVO avo = new BoardAVO();
				avo.setAns_ID(rs.getString("ANS_ID"));
				avo.setComment_ID(rs.getString("Comment_ID"));
				avo.setAnser_ID(rs.getString("Answer_ID"));
				avo.setContents(rs.getString("Contents"));
				avo.setFile_Name(rs.getString("File_Name"));
				avo.setIns_Date_Time(rs.getString("Ins_Date_Time"));
				
				// 이미지 데이터를 BufferedImage로 변환
				if(avo.getFile_Name() != null) {
					int DotIndex = avo.getFile_Name().lastIndexOf('.');
					if (DotIndex > 0) {
						String extension = avo.getFile_Name().substring(DotIndex + 1);
						avo.setFile_Extension(extension);
					}
				}
				list.add(avo);
			}
			
			db.close(rs);
			db.close(ps);
			db.close(con);
			return list;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void delAns(HttpServletRequest request, HttpServletResponse response) {
		query = "UPDATE CS_ANS SET DEL_DATE_TIME = GETDATE(), DEL_YN = 'Y' "
				+ " WHERE ANS_ID = ? ";
		
		ans_ID = request.getParameter("ans_ID");
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, ans_ID);
			
			ps.executeUpdate();
			
			img.delfile(img.getFileDataFromDatabase(ans_ID, "1"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
		
	}
	
	public void updAns(MultipartRequest multi, HttpServletResponse response) {
		file_Name = multi.getFilesystemName("file_Name");
		ans_ID = multi.getParameter("ans_ID");

		try {
			con = db.getConnection();
			query = "UPDATE CS_ANS SET Upd_DATE_TIME = GETDATE(), "
					+ " CONTENTS = ? ";

			if (file_Name != null) {
				query += " ,FILE_NAME = ? "
						+ " WHERE Ans_ID = ? ";
				ps = con.prepareStatement(query);
				ps.setString(1, multi.getParameter("content"));
				ps.setString(2, file_Name);
				ps.setString(3, multi.getParameter("ans_ID"));
			
			}else {
				query += " WHERE Ans_ID = ? ";
				ps = con.prepareStatement(query);
				ps.setString(1, multi.getParameter("content"));
				ps.setString(2, multi.getParameter("ans_ID"));
			}
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
		
	}
	
}
