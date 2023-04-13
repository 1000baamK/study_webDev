package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class PhotoDetailController
 */
@WebServlet("/detail.ph")
public class PhotoDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
//		System.out.println(boardNo);
		
		//조회수 증가메소든 일반 게시판에서 작성했음 사용가능
		int result = new BoardService().increaseCount(boardNo);
		
		if(result>0) { //조회수 증가 성공시 조회해오기
			//게시글 정보, 첨부파일 정보 상세조회 - 기존에 있었던 게시글 조회 쿼리문을 left조인을 이용해 조회해오기
			Board b = new BoardService().selectBoard(boardNo);
			
			ArrayList<Attachment> list = new BoardService().selectAttachmentList(boardNo);
			
			request.setAttribute("board", b);
			request.setAttribute("atList", list);
			
//			System.out.println(b);
//			System.out.println(at);
			request.getRequestDispatcher("views/board/photoDetailView.jsp").forward(request, response);
		}else {
			request.setAttribute("errorMsg", "게시글 열람 실패");
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
