package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.me")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//로그아웃 처리
		//로그인되어있는 loginUser객체를 session에서 삭제 시키기
		HttpSession session = request.getSession();
		
		//로그인된 회원정보 객체 삭제
		session.removeAttribute("loginUser");
		
		//만약 세션을 초기화 하고싶다면?
//		session.invalidate(); //세션만료(초기화)
		
		//재요청 방식으로 메인페이지로 돌아가기
//		response.sendRedirect(request.getContextPath()); // /jsp로 돌아감
		request.getSession().setAttribute("alertMsg", "로그아웃 완료");
		String before = request.getHeader("Referer");
		response.sendRedirect(before);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
