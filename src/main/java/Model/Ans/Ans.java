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
	
	private String comment_ID = "";
	private String Answer_ID = "";
	private String contents = "";
	
	DataBase db = new DataBase();
	
	public void insAns(MultipartRequest multi, HttpServletResponse response) {
		query = "INSERT INTO CS_ANS (BOARD_ID, COMMENT_ID, ANSWER_ID, CONTENTS, FILE_NAME, INS_DATE_TIME) "
				+ " VALUES(?,?,?,?,?,GETDATE())";
		
		String board_ID = multi.getParameter("board_ID");
		comment_ID = multi.getParameter("comment_ID");
		Answer_ID = multi.getParameter("answerID");
		contents = multi.getParameter("ansContents");
		try {
			con = db.getConnection();
			
			ps = con.prepareStatement(query);
			ps.setString(1, board_ID);
			ps.setString(2, comment_ID);
			ps.setString(3, Answer_ID);
			ps.setString(4, contents);

			File file = multi.getFile("fileNm");
			if (file != null && file.exists()) {
				byte[] fileData = Files.readAllBytes(file.toPath());
				ps.setBytes(5, fileData);
			}else {
				ps.setBytes(5, null);
			}
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
	}
	
//	public List<BoardAVO> listAns(HttpServletRequest request, HttpServletResponse response){
	public List<BoardAVO> listAns(MultipartRequest multi, HttpServletResponse response){
		query = "SELECT BOARD_ID, "
				+ " COMMENT_ID, "
				+ " ANSWER_ID, "
				+ " CONTENTS, "
				+ " FILE_NAME, "
				+ " FORMAT(INS_DATE_TIME, 'yy-MM-dd HH:mm:ss') AS INS_DATE_TIME "
				+ " FROM CS_ANS "
				+ " WHERE BOARD_ID = " + multi.getParameter("board_ID")
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
				avo.setBoard_ID(rs.getInt("Board_ID"));
				avo.setComment_ID(rs.getString("Comment_ID"));
				avo.setAnser_ID(rs.getString("Answer_ID"));
				avo.setContents(rs.getString("Contents"));
				avo.setFile_Name(rs.getBytes("File_Name"));
				avo.setIns_Date_Time(rs.getString("Ins_Date_Time"));
				
				// 이미지 데이터를 BufferedImage로 변환
	            byte[] fileData = rs.getBytes("File_Name");
	            String extension = "";
	            if(fileData != null) {
	            	extension = Image.getExtensionFromMagicNumber(fileData);
	            }
	            	
	            
	            // 첨부파일이 이미지 파일 일 때
	            if(extension != null && (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))) {
	            	
	            	ByteArrayInputStream bais = new ByteArrayInputStream(fileData);
	            	BufferedImage image = ImageIO.read(bais);
	            	byte[] newImageData = convertImageToFormat(image, "png");
	            	
	            	// 변환된 이미지 데이터를 Base64로 인코딩
	            	String encodedImageData = Base64.getEncoder().encodeToString(newImageData);
	            	avo.setFile_ViewName(encodedImageData);
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
				+ " WHERE BOARD_ID = ? "
				+ " AND ANSWER_ID = ? ";
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, request.getParameter("board_ID"));
			ps.setString(2, request.getParameter("answer_ID"));
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
		
	}
	
	public void updAns(MultipartRequest multi, HttpServletResponse response) {
		try {
			con = db.getConnection();
			query = "UPDATE CS_ANS SET Upd_DATE_TIME = GETDATE(), "
					+ " CONTENTS = ? ";

			File file = multi.getFile("fileNm");
			if (file != null && file.exists()) {
				System.out.println(" 13123");
				byte[] fileData = Files.readAllBytes(file.toPath());
				query += " ,FILE_NAME = ? "
						+ " WHERE BOARD_ID = ? "
						+ " AND ANSWER_ID = ? ";
				ps = con.prepareStatement(query);
				ps.setString(1, multi.getParameter("content"));
				ps.setBytes(2, fileData);
				ps.setString(3, multi.getParameter("board_ID"));
				ps.setString(4, multi.getParameter("answerID"));
			
			}else {
				query += " WHERE BOARD_ID = ? "
						+ " AND ANSWER_ID = ? ";
				ps = con.prepareStatement(query);
				ps.setString(1, multi.getParameter("content"));
				ps.setString(2, multi.getParameter("board_ID"));
				ps.setString(3, multi.getParameter("answerID"));
			}
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(ps);
			db.close(con);
		}
		
	}
	
	public static byte[] convertImageToFormat(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }
}
