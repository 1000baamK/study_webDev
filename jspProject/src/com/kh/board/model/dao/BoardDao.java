package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;

public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() {
		String filePath = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//총 게시글 개수 구하는 메소드
	public int selectListCount(Connection conn) {
		
		int listCount = 0;
		ResultSet rset = null;
		Statement stmt = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			stmt = conn.createStatement();

			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		
		ArrayList<Board> list = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			/*
			 * boardLimit(한페이지에 보여져야할 게시글개수)가 10일때
			 * currentPage : 1	-	시작값 : 1	- 끝값:10
			 * currentPage : 2	-	시작값: 11 - 끝값:20
			 * 
			 * 시작값은(currentPage-1)*boardLimit+1
			 * 끝값 (시작값+boardLijmit)-1
			 */
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = (startRow+pi.getBoardLimit())-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("BOARD_NO")
								  ,rset.getString("CATEGORY_NAME")
								  ,rset.getString("BOARD_TITLE")
								  ,rset.getString("USER_ID")
								  ,rset.getInt("COUNT")
								  ,rset.getDate("CREATE_DATE")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	public ArrayList<Category> categoryList(Connection conn){
		ArrayList<Category> list = new ArrayList<>();
		ResultSet rset = null;
		Statement stmt = null;
		
		String sql = prop.getProperty("categoryList");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Category(rset.getInt("CATEGORY_NO"),
									  rset.getString("CATEGORY_NAME")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}
	
	public int insertBoard(Connection conn, Board b) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public int insertAttachment(Connection conn, Attachment at) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	//조회수 증가메소드
	public int increaseCount(Connection conn, int boardNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}

		
		return result;
	}
	
	//게시글 조회
	public Board selectBoard(Connection conn, int boardNo) {
		
		Board b = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getInt("BOARD_NO")
							 ,rset.getString("CATEGORY_NAME")
							 ,rset.getString("BOARD_TITLE")
							 ,rset.getString("BOARD_CONTENT")
							 ,rset.getString("USER_ID")
							 ,rset.getDate("CREATE_DATE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return b;
	}

	//첨부파일 조회
	public Attachment selectAt(Connection conn, int boardNo) {
		
		Attachment at = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("selectAt");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Attachment(rset.getInt("FILE_NO"),
									rset.getString("ORIGIN_NAME"),
									rset.getString("CHANGE_NAME"),
									rset.getString("FILE_PATH"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return at;
	}
	
	//첨부파일 삭제 메소드 (내풀이)
//	public int deleteAt(Connection conn, int fno) {
//		
//		int result = 0;
//		PreparedStatement pstmt = null;
//		
//		String sql = prop.getProperty("deleteAt");
//		
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, fno);
//			
//			result = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	//게시글 수정 메소드
	public int updateBoard(Connection conn, Board b) {

		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}

		
		return result;
	}
	
	//기존 파일 정보 수정하기
	public int updateAttachment(Connection conn, Attachment at) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getFileNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}

		
		return result;
	}

	//새로운 파일 정보 넣기
	public int newInsertAttachment(Connection conn, Attachment at) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("newInsertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefBno());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}
	
	//게시글 삭제 메소드
	public int deleteBoard(Connection conn, int boardNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	//사진 게시판 게시글내용 입력 메소드
	public int insertPhotoBoard(Connection conn, Board b) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertPhotoBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setInt(3, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	//사진게시판 첨부파일 입력 메소드
	public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {
		
		int result = 1;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertAttachmentList");
		
		try {
			for(Attachment at : list) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePath());
				pstmt.setInt(4,  at.getFileLevel());
				
				result *= pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			//처음부터 예외발생하여 result가 1인채로 넘어가는 것을 방지하기
			result=0;
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	//사진게시판 게시글 조회
//	public ArrayList<Board> selectPhotoBoard(Connection conn) {
//		
//		ArrayList<Board> list = new ArrayList<>();
//		ResultSet rset = null;
//		Statement stmt = null;
//		
//		String sql = prop.getProperty("selectPhotoBoard");
//		
//		try {
//			stmt = conn.createStatement();
//			
//			rset = stmt.executeQuery(sql);
//			
//			while(rset.next()) {
//				list.add(new Board(rset.getInt("BOARD_NO"),
//								   rset.getString("BOARD_TITLE"),
//								   rset.getInt("COUNT")));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(rset);
//			JDBCTemplate.close(stmt);
//		}
//		return list;
//	}
	
	//나
//	//첨부파일 리스트 조회
//	public ArrayList<Attachment> selectAtList(Connection conn) {
//		
//		ArrayList<Attachment> atList = new ArrayList<>();
//		ResultSet rset = null;
//		Statement stmt = null;
//		
//		String sql = prop.getProperty("selectAtList");
//		
//		try {
//			stmt = conn.createStatement();
//			
//			rset = stmt.executeQuery(sql);
//			
//			while(rset.next()) {
//				atList.add(new Attachment(rset.getInt("FILE_NO"),
//								
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			JDBCTemplate.close(rset);
//			JDBCTemplate.close(stmt);
//		}
//		return list;
//	}
	
	
	//사진게시판 목록 조회메소드
	public ArrayList<Board> selectAttachmentList(Connection conn) {

		ArrayList<Board> list = new ArrayList<>();
		
		ResultSet rset = null;
		Statement stmt = null;
		
		String sql = prop.getProperty("selectAttachmentList");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("BOARD_NO"),
								   rset.getString("BOARD_TITLE"),
								   rset.getInt("COUNT"),
								   rset.getString("TITLE_IMG")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		
		return list;
	}
	
	//사진게시글 상세조회 첨부파일 메소드
	public ArrayList<Attachment> selectAttachmentList(Connection conn, int boardNo) {
		
		ArrayList<Attachment> list = new ArrayList<>();
		
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("selectAt");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Attachment(rset.getInt("FILE_NO"),
										rset.getString("ORIGIN_NAME"),
										rset.getString("CHANGE_NAME"),
										rset.getString("FILE_PATH")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		
		return list;
	}

	//댓글 삽입
	public int insertReply(Connection conn, Reply rp) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, rp.getReplyContent());
			pstmt.setInt(2, rp.getRefBno());
			pstmt.setInt(3, Integer.parseInt(rp.getReplyWriter()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}

	//댓글 리스트 조회
	public ArrayList<Reply> selectReply(Connection conn, int boardNo) {
		
		ArrayList<Reply> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Reply(rset.getInt("REPLY_NO"),
								   rset.getString("REPLY_CONTENT"),
								   rset.getInt("REF_BNO"),
								   rset.getString("USER_ID"),
								   rset.getString("CREATE_DATE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		
		
		
		return list;
	}
	
	
}
