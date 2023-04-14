package com.kh.board.model.vo;

import java.sql.Date;

public class Reply {
//	REPLY_NO	NUMBER
//	REPLY_CONTENT	VARCHAR2(400 BYTE)
//	REF_BNO	NUMBER
//	REPLY_WRITER	NUMBER
//	CREATE_DATE	DATE
//	STATUS	VARCHAR2(1 BYTE)
	private int replyNo;
	private String replyContent;
	private int refBno;
	private String replyWriter;
	private String createDate;
	private String status;
	
	
	
	public Reply() {
		super();
	}
	
	public Reply(int replyNo, String replyContent, int refBno, String replyWriter, String createDate, String status) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.refBno = refBno;
		this.replyWriter = replyWriter;
		this.createDate = createDate;
		this.status = status;
	}
	
	
	
	public Reply(int replyNo, String replyContent, int refBno, String replyWriter, String createDate) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.refBno = refBno;
		this.replyWriter = replyWriter;
		this.createDate = createDate;
	}

	public int getReplyNo() {
		return replyNo;
	}
	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public int getRefBno() {
		return refBno;
	}
	public void setRefBno(int refBno) {
		this.refBno = refBno;
	}
	public String getReplyWriter() {
		return replyWriter;
	}
	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	@Override
	public String toString() {
		return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", refBno=" + refBno + ", replyWriter="
				+ replyWriter + ", createDate=" + createDate + ", status=" + status + "]";
	}
	
	
}
