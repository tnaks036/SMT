package Model.Image;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Model.DataBase;
import VO.BoardAVO;
import VO.BoardQVO;

public class Image {
	
	DataBase db = new DataBase();
	String query = "";
	
	File directory;
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String savePath = "C:\\Users\\SMT\\Desktop\\git\\SMT\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\SMT\\file";
	// 배포할땐 배포 주소로 바꿔야함 
	
	public MultipartRequest fileUpload(HttpServletRequest request){
        
		MultipartRequest multi = null;
		// 저장할 경로
//		String savePath = request.getSession().getServletContext().getRealPath("/file");
//      String savePath = "C:\\Users\\SMT\\Desktop\\git\\SMT\\SMT\\src\\main\\webapp\\file";
        
        // 파일 최대 크기
        int size = 100 * 1024 * 1024;

        try {
        	multi = new MultipartRequest(request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			e.printStackTrace();
		}

        return multi;
    }
	
    public String getFileDataFromDatabase(String boardID) { // 게시글
    	query = "SELECT File_Name FROM CS_Ques "
    			+ " WHERE Board_ID = " + boardID;
    	
    	BoardQVO bqo = new BoardQVO();
    	
    	try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bqo.setFile_Name(rs.getString("File_Name"));
				return bqo.getFile_Name();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }
    
    
    public String getFileDataFromDatabase(String AnsID, String reply) { // 댓글
    	query = "SELECT File_Name FROM CS_Ans "
    			+ " WHERE Ans_ID = " + AnsID;
    	
    	BoardAVO bao = new BoardAVO();
    	
    	try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {
				bao.setFile_Name(rs.getString("File_Name"));
				return bao.getFile_Name();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }
    
    
    public void delfile(String file_Name) { 
//    	String filePath = "C:\\Users\\SMT\\Desktop\\git\\SMT\\SMT\\src\\main\\webapp\\file";
    	
    	if(file_Name != null) {
    		directory = new File(savePath + "/" + file_Name);
    	}

        if (directory != null && directory.exists()) {
        	directory.delete();
        }
    }
}
