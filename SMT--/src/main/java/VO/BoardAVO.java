package VO;

import java.util.Arrays;

public class BoardAVO {

	private int board_ID;
	private String comment_ID;
	private String anser_ID;
	private String Contents;
	private byte[] file_Name;
	private String file_ViewName; //출력용
	private String ins_Date_Time;
	private String upd_Date_Time;
	private String Del_Date_Time;
	private String Del_Yn;
	
	
	
	public String getFile_ViewName() {
		return file_ViewName;
	}
	public void setFile_ViewName(String file_ViewName) {
		this.file_ViewName = file_ViewName;
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
	public String getAnser_ID() {
		return anser_ID;
	}
	public void setAnser_ID(String anser_ID) {
		this.anser_ID = anser_ID;
	}
	public String getContents() {
		return Contents;
	}
	public void setContents(String contents) {
		Contents = contents;
	}
	public byte[] getFile_Name() {
		return file_Name;
	}
	public void setFile_Name(byte[] file_Name) {
		this.file_Name = file_Name;
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
	
	@Override
	public String toString() {
		return "BoardAVO [board_ID=" + board_ID + ", comment_ID=" + comment_ID + ", anser_ID=" + anser_ID
				+ ", Contents=" + Contents + ", file_Name=" + Arrays.toString(file_Name) + ", file_ViewName="
				+ file_ViewName + ", ins_Date_Time=" + ins_Date_Time + ", upd_Date_Time=" + upd_Date_Time
				+ ", Del_Date_Time=" + Del_Date_Time + ", Del_Yn=" + Del_Yn + "]";
	}
}
