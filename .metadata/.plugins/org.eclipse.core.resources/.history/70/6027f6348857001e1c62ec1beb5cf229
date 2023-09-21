package Model.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import Model.DataBase;
import VO.BoardAVO;
import VO.BoardQVO;

public class Image {
	
	DataBase db = new DataBase();
	String query = "";
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public MultipartRequest uploadTest(HttpServletRequest request){
        
		MultipartRequest multi = null;
		// 저장할 경로
        String savePath = "C:\\Users\\SMT\\Desktop\\git";
        // 파일 최대 크기
        int size = 50 * 1024 * 1024;

        try {
        	multi = new MultipartRequest(request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			e.printStackTrace();
		}

        return multi;
    }
	
	public void downloadImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		int board_ID = Integer.parseInt(request.getParameter("board_ID")); 
		
		byte[] fileData;
		
		if(request.getParameter("reply") != null) {
			fileData = getFileDataFromDatabase(board_ID, "reply");
		}else {
			fileData = getFileDataFromDatabase(board_ID);
		}
		
        if (fileData == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }
        
        String extension = getExtensionFromMagicNumber(fileData);

        // 응답 설정
        response.setHeader("Content-Disposition", "attachment; filename=SMTFile" + request.getParameter("board_ID") + "." + extension);
        response.setContentType("application/octet-stream");

        // 파일 다운로드
        try (InputStream inputStream = new ByteArrayInputStream(fileData);
                OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }


	public static String getExtensionFromMagicNumber(byte[] data) {
	    if (data.length >= 2) {
	    	//확장자  추가
	    	if (data.length >= 4) {
	            if (data[0] == (byte) 0xFF && data[1] == (byte) 0xD8) {
	                return "jpg";
	            } else if (data[0] == (byte) 0x89 && data[1] == (byte) 0x50 && data[2] == (byte) 0x4E && data[3] == (byte) 0x47) {
	                return "png";
	            } else if (data[0] == (byte) 0x47 && data[1] == (byte) 0x49 && data[2] == (byte) 0x46 && data[3] == (byte) 0x38) {
	                return "gif";
	            } else if (data[0] == (byte) 0x42 && data[1] == (byte) 0x4D) {
	                return "bmp";
	            } else if (data[0] == (byte) 0xD0 && data[1] == (byte) 0xCF && data[2] == (byte) 0x11 && data[3] == (byte) 0xE0) {
	                return "doc";
	            } else if (data[0] == (byte) 0xFF && data[1] == (byte) 0xDB) {
	                return "jpge";
	            } else if (data[0] == (byte) 0x25 && data[1] == (byte) 0x50 && data[2] == (byte) 0x44 && data[3] == (byte) 0x46) {
	                return "pdf";
	            } else if (data[0] == (byte) 0x50 && data[1] == (byte) 0x4B && data[2] == (byte) 0x03 && data[3] == (byte) 0x04) {
	                if (data[34] == (byte) 0x06 && data[35] == (byte) 0x00 && data[36] == (byte) 0x00 && data[37] == (byte) 0x00) {
	                    return "pptx";
	                } else if (data[34] == (byte) 0x07 && data[35] == (byte) 0x08) {
	                    return "ppt";
	                }
	            }
	    	}
	    }
	    return null;
	}
	    
    private byte[] getFileDataFromDatabase(int boardID) { // 게시글
        // DB에서 파일 데이터를 가져오는 로직을 여기에 작성
        // 예: SELECT FileData FROM CS_Ques WHERE File_Name = ?
        // 위 쿼리를 실행하여 파일 데이터를 byte[] 형태로 반환
    	query = "SELECT File_Name FROM CS_Ques "
    			+ " WHERE Board_ID = " + boardID;
    	
    	BoardQVO bqo = new BoardQVO();
    	
    	try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bqo.setFile_Name(rs.getBytes("File_Name"));
				return bqo.getFile_Name();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }
    
    
    private byte[] getFileDataFromDatabase(int boardID, String reply) { // 댓글
        // DB에서 파일 데이터를 가져오는 로직을 여기에 작성
        // 예: SELECT FileData FROM CS_Ques WHERE File_Name = ?
        // 위 쿼리를 실행하여 파일 데이터를 byte[] 형태로 반환
    	query = "SELECT File_Name FROM CS_Ans "
    			+ " WHERE Board_ID = " + boardID;
    	
    	BoardAVO bao = new BoardAVO();
    	
    	try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {
				bao.setFile_Name(rs.getBytes("File_Name"));
				return bao.getFile_Name();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
        return null;
    }
    
    
    public void delImg() {
//    	String directoryPath = "C:/Users/SMT/Desktop/git";
    	String directoryPath = "C:\\Users\\SMT\\Desktop\\git\\smtv_board\\SMT\\src\\main\\webapp\\img";

        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
            	if (file.isFile()) {
            			file.delete();
            	}
            }
        }
    
        }
    }
}
