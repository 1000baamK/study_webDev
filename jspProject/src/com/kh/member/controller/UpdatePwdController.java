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
 * Servlet implementation class UpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 강사님 방법
		 * - 세션을 이용하여 userId 꺼내오기
		 * HttpSession session = request.getSession();
		 * 
		 * String userId = ((Member)session.getAttribute("loginUser")).getUserId()
		 * 
		 */
		
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String updatePwd = request.getParameter("updatePwd");
		
		int result = new MemberService().updatePwd(userId,updatePwd);
		
		if(result>0) {
			HttpSession session = request.getSession();
			
			session.setAttribute("alertMsg", "비밀번호 변경이 완료되었습니다.");
			
			response.sendRedirect(request.getContextPath());
		}else {
			request.setAttribute("errorMsg", "비번 변경실패!#!#");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
		
	
	}

}
