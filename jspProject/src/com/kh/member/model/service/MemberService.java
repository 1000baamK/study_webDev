package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	public Member loginMember(String userId, String userPwd) {
		
		//커넥션 객체로 디비에 접속
		Connection conn = JDBCTemplate.getConnection();
		
		Member m = new MemberDao().loginMember(conn,userId,userPwd);
		
		JDBCTemplate.close(conn);
		
		return m;
	}

	public int insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn,m);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//정보수정 메소드
	public Member updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn,m);
		//변경된 회원 정보를 세션에 담아야 하기 때문에 다시 조회해오기
		Member updateMem = null; //변경된 회원 정보 담을 변수
		
		
		if(result>0) {
			JDBCTemplate.commit(conn);
			//갱신된 정보 조회해오기
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;
	}


	public int updatePwd(String userId, String updatePwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updatePwd(conn,userId,updatePwd);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteMember(String userId) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(conn,userId);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
}
