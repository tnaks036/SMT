package VO;

public class BoardQVO {
	
	private int board_ID;
	private String comment_ID;
	private String title;
	private String contents;
	private String file_Name; //출력용
	private String file_Extension; //파일 확장자
	private String ins_Date_Time;
	private String upd_Date_Time;
	private String Del_Date_Time;
	private String Del_Yn;
	
	private int answerCnt;
	
	
	public String getFile_Name() {
		return file_Name;
	}
	public void setFile_Name(String file_Name) {
		this.file_Name = file_Name;
	}
	public int getAnswerCnt() {
		return answerCnt;
	}
	public void setAnswerCnt(int answerCnt) {
		this.answerCnt = answerCnt;
	}
	public int getBoard_ID() {
		return board_ID;
	}
	public void setBoard_ID(int board_ID) {
		this.board_ID = board_ID;
	}
	public String getComment_ID() {
		return comment_ID;
	}
	public void setComment_ID(String comment_ID) {
		this.comment_ID = comment_ID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getFile_Extension() {
		return file_Extension;
	}
	public void setFile_Extension(String file_Extension) {
		this.file_Extension = file_Extension;
	}
	public String getIns_Date_Time() {
		return ins_Date_Time;
	}
	public void setIns_Date_Time(String ins_Date_Time) {
		this.ins_Date_Time = ins_Date_Time;
	}
	public String getUpd_Date_Time() {
		return upd_Date_Time;
	}
	public void setUpd_Date_Time(String upd_Date_Time) {
		this.upd_Date_Time = upd_Date_Time;
	}
	public String getDel_Date_Time() {
		return Del_Date_Time;
	}
	public void setDel_Date_Time(String del_Date_Time) {
		Del_Date_Time = del_Date_Time;
	}
	public String getDel_Yn() {
		return Del_Yn;
	}
	public void setDel_Yn(String del_Yn) {
		Del_Yn = del_Yn;
	}
}
