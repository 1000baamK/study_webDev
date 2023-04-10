package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;

/**
 * Servlet implementation class AttachmentUpdateController
 */
@WebServlet("/atUpdate.bo")
public class AttachmentUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttachmentUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fno = Integer.parseInt(request.getParameter("fno"));
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		
		int result = new BoardService().deleteAt(fno);
		
		
		if(result>0) {
//			request.getRequestDispatcher(request.getContextPath()+"/update.bo?bno="+bno).forward(request, response);
			response.sendRedirect(request.getContextPath()+"/update.bo?bno="+bno);
		}else {
			request.setAttribute("alertMsg", "첨부파일 삭제 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
