package VO;

public class PageNum {
	
	int nowNum; //현재페이지
	int startNum; //시작페이지
	int endNum; //종료페이지
	
	int listNum; //목록 전체 갯수
	int totalNum; //전체 페이지 수 

	int pageSize; //한 페이지에 보일 게시글 수 
	int blockSize; //한 줄에 보일 페이지 수(ex) 1~5, 1~10)
	
	int dbNum; //DB에 설정할 인덱스 시작번호
	
	
	public int getDbNum() {
		return dbNum;
	}
	public void setDbNum(int dbNum) {
		this.dbNum = dbNum;
	}
	public int getNowNum() {
		return nowNum;
	}
	public void setNowNum(int nowNum) {
		this.nowNum = nowNum;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	
	@Override
	public String toString() {
		return "PageNum [nowNum=" + nowNum + ", startNum=" + startNum + ", endNum=" + endNum + ", listNum=" + listNum
				+ ", totalNum=" + totalNum + ", pageSize=" + pageSize + ", blockSize=" + blockSize + ", dbNum=" + dbNum
				+ "]";
	}
	
}
