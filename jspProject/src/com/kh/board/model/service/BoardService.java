package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;

public class BoardService {
	
	//총 게시글 개수 구하는 메소드임당
	public int selectListCount() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//처리된 행수가 아닌 총 게시글의 개수를 조회 해온것.
		int listCount = new BoardDao().selectListCount(conn);
		
		JDBCTemplate.close(conn);
		
		return listCount;
	}

	//게시글 리스트 조회
	public ArrayList<Board> selectList(PageInfo pi) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	//카테고리리스트
	public ArrayList<Category> categoryList(){
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Category> list = new BoardDao().categoryList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	//게시글작성
	public int insertBoard(Board b, Attachment at) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		//게시글이 작성될 때 첨부파일이 있거나 또는 없는 경우도 생각하여 작업하기
		int result = new BoardDao().insertBoard(conn,b);
		
		//첨부파일이 있는 경우에 Attachment 테이블에 insert 작업하기
		//첨부파일이 없어도 게시글 커밋은 해야하니까 해당 조건에 부합하게 1로 초기화해놓기
		int result2 = 1;
		if(at != null) {
			result2 = new BoardDao().insertAttachment(conn,at);
		}
		
		if(result>0 && result2>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result*result2; //둘중 하나라도 0이 나오면 0을 반환하게 작성하기
	}
	
	//조회수 증가메소드
	public int increaseCount(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().increaseCount(conn, boardNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//게시글정보 조회
	public Board selectBoard(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Board b = new BoardDao().selectBoard(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return b;
	}

	//첨부파일조회
	public Attachment selectAt(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Attachment at = new BoardDao().selectAt(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return at;
	}

	//첨부파일 삭제 메소드 (내풀이)
//	public int deleteAt(int fno) {
//		
//		Connection conn = JDBCTemplate.getConnection();
//		
//		int result = new BoardDao().deleteAt(conn, fno);
//		
//		if(result>0) {
//			JDBCTemplate.commit(conn);
//		}else {
//			JDBCTemplate.rollback(conn);			
//		}
//		
//		JDBCTemplate.close(conn);
//		
//		return result;
//	}
	
	//게시글 수정 메소드
	public int updateBoard(Board b, Attachment at) {
		
		Connection conn = JDBCTemplate.getConnection();
		//새로운 첨부파일 없고 기존첨부파일이 없는 경우 -> board-update
		//새로운 첨부파일 있고 기존 첨부파일 없는 경우 -> board -update / attachment-insert
		//새로운 첨부파일 있고 기존 첨부파일 있는 경우 -> board-update / attachment-update
		
		int result = new BoardDao().updateBoard(conn, b);
		
		int result2 = 1;
		
		if(at != null) { //새롭게 추가된 첨부파일이 있는 경우
			
			if(at.getFileNo() != 0) { //기존에 첨부파일이 있엇을 경우(변경)
				result2 = new BoardDao().updateAttachment(conn, at);
			}else { //기존의 첨부파일이 없었을 경우(추가)
				result2 = new BoardDao().newInsertAttachment(conn, at);
			}
		}
		
		if(result>0 && result2>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		
		return result*result2;
	}
	
	//보드 삭제 메소드
	public int deleteBoard(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().deleteBoard(conn, boardNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//사진 게시판 게시글 작성 메소드
	public int insertPhotoBoard(Board b, ArrayList<Attachment> list) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertPhotoBoard(conn, b);
		
		int result2 = new BoardDao().insertAttachmentList(conn, list);
		
		if(result>0 && result2>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);			
		}
		
		//둘중 하나라도 0이라면 0돌려주기
		return result*result2;
	}

	//나
//	//사진 게시판 조회
//	public ArrayList<Board> selectPhotoBoard() {
//		
//		Connection conn = JDBCTemplate.getConnection();
//		
//		ArrayList<Board> list = new BoardDao().selectPhotoBoard(conn);
//		
//		JDBCTemplate.close(conn);
//		
//		return list;
//	}
//	
//	//첨부파일리스트 조회
//	public ArrayList<Attachment> selectAtList() {
//		
//		Connection conn = JDBCTemplate.getConnection();
//		
//		ArrayList<Attachment> atList = new BoardDao().selectAtList(conn);
//		
//		JDBCTemplate.close(conn);
//		
//		return atList;
//	}

	public ArrayList<Board> selectAttachmentList() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectAttachmentList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	//사진게시글 상세조회 첨부파일 메소드
	public ArrayList<Attachment> selectAttachmentList(int boardNo) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn, boardNo);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	

}
