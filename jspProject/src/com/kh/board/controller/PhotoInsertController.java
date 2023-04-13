package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class PhotoInsertController
 */
@WebServlet("/insert.ph")
public class PhotoInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("views/board/photoEnrollForm.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			//전송용량 제한
			int maxSize = 10 * 1024 * 1024;
			
			//저장할 물리적인 경로 알아내기
			String savePath = request.getSession().getServletContext().getRealPath("/resources/photoBoard_files/");
			
			//전달된 파일명 수정작업 객체 포함시켜 업로드 작업
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			//DB에 저장해야할 데이터 추출하기
			Board b = new Board();
			b.setBoardWriter(multiRequest.getParameter("userNo"));
			b.setBoardTitle(multiRequest.getParameter("title"));
			b.setBoardContent(multiRequest.getParameter("content"));
			
			//Attachment에 들어가야할 데이터도 추출하기
			//여러개의 첨부파일이 있을수 있으니 list 담아서 가져가기
			//대표이미지는 필수입력사항이니 최소 1개는 담겨야한다.
			ArrayList<Attachment> list = new ArrayList<>();
			
			for(int i=0; i<4; i++) {
				String key = "file"+i;
				
				//첨부파일이 있다면 첨부파일 객체 생성 및 데이터 담기
				if(multiRequest.getOriginalFileName(key) != null) {
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("/resources/photoBoard_files/");

					//대표이미지와 상제이미지를 구분할 FILE_LEVEL 컬럼값 세팅하기
					if(i==1) { //대표이미지(file1을 키값으로 가진)
						at.setFileLevel(1);
					}else { //상세이미지(대표이미지가 아닌 경우)
						at.setFileLevel(2);
					}
					
					list.add(at);
				}
			}
			
			//추출한 게시글 정보와 첨부파일정보 넘겨주기
			int result = new BoardService().insertPhotoBoard(b, list);
			
			if(result>0) {
				
				request.getSession().setAttribute("alertMsg", "사진게시글 작성 성공");
				response.sendRedirect(request.getContextPath()+"/list.ph");
			}else {
				request.setAttribute("errorMsg", "사진게시글 작성실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
		}
		
	}

}
