package Model.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DataBase;
import VO.PageNum;

public class Page {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String query;
	
	int nowNum; //현재페이지
	int startNum; //시작페이지
	int endNum; //종료페이지
	
	int listNum; //목록 전체 갯수
	int totalNum; //전체 페이지 수 

	int pageSize; //한 페이지에 보일 게시글 수 
	int blockSize; //한 줄에 보일 페이지 수(ex) 1~5,
	int dbNum; //DB에 설정할 인덱스 시작번호
	
	private String condition = "";
	private String searchKeyword = "";
	
	DataBase db = new DataBase();
	
	public PageNum setPage(PageNum pn) {
		PageNum pInfo = new PageNum();
		
		listNum = pn.getListNum();
		pageSize = 10; //한 페이지에 노출될 목록 갯수
		blockSize = 5; //한 라인에 보일 페이지 수
		nowNum = pn.getNowNum();
		
		//전체 페이지 수 구하기 정하기(totalNum)
		if(listNum % pageSize != 0) {
			totalNum = (listNum / pageSize)+1;
		}else {
			totalNum = (listNum / pageSize);
		}
		
		//시작 페이지 구하기
		if(nowNum == 0) {
			nowNum = 1;
		}
		
		//db 
		dbNum = nowNum - 1;
		if(dbNum > 0) {
			dbNum = (pageSize * dbNum);
		}
		
		//시작페이지 ,종료페이지 
		if(totalNum <= blockSize) {
			startNum = 1;
			endNum = totalNum;
		}else {
			int a = nowNum / blockSize;
			
			if((nowNum % blockSize) == 0) {
				a = a-1;
				startNum = (a * blockSize) + 1;
				endNum = (a + 1) * blockSize;
			}else {
				startNum = (a * blockSize) + 1;
				endNum = (a + 1) * blockSize;
			}
			
			if(endNum > totalNum) {
				endNum = totalNum;
			}
		}
		
		pInfo.setNowNum(nowNum);
		pInfo.setStartNum(startNum);
		pInfo.setEndNum(endNum);
		pInfo.setListNum(listNum);
		pInfo.setTotalNum(totalNum);
		pInfo.setPageSize(pageSize);
		pInfo.setDbNum(dbNum);
		
		return pInfo;
	}
	
	//목록 갯수 구하기
	public int getPageNum(HttpServletRequest request, HttpServletResponse response) {
		int pageNum = 0;
		condition = request.getParameter("condition");
		searchKeyword = request.getParameter("searchKeyword");
		
		query = " SELECT COUNT(*) AS pNum "
				+ " FROM CS_QUES "
				+ " WHERE (Del_Yn <> 'Y' OR Del_Yn IS NULL) "
				;
		
		if(searchKeyword != "" && condition != null) {
			switch(condition) {
				case "title" :
					query += " AND title LIKE '%" + searchKeyword +"%' ";
				break;
				
				case "contents" :
					query += " AND contents LIKE '%" + searchKeyword +"%' ";
				break;
				
				case "comment_ID" :
					query += " AND comment_ID LIKE '%" + searchKeyword +"%' ";
				break;
			}
		}
		
		try {
			con = db.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pageNum = rs.getInt("pNum");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			db.close(rs);
			db.close(ps);
			db.close(con);
		}
		
		return pageNum;
	}
}
