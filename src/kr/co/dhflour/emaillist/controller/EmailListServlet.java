package kr.co.dhflour.emaillist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.dhflour.emaillist.dao.EmailListDao;
import kr.co.dhflour.emaillist.vo.EmailListVo;

@WebServlet("/el")
public class EmailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "utf-8" );
		
		String actionName = request.getParameter( "a" );
		
		if( "add".equals(actionName) == true ) {
			String firstName = request.getParameter( "fn" );
			String lastName = request.getParameter( "ln" );
			String email = request.getParameter( "email" );
			
//			System.out.println( firstName );
//			System.out.println( lastName );
//			System.out.println( email );
			
			EmailListVo vo = new EmailListVo();
			vo.setFirstName(firstName);
			vo.setLastName(lastName);
			vo.setEmail(email);
			
			new EmailListDao().insert(vo);
			
			response.sendRedirect( "/emaillist2/el" );
			
		} else if( "form".equals(actionName) == true ) {
			// 포워딩
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/views/form.jsp" );
			rd.forward(request, response);			
		} else {
			/* default 요청(list 처리) */
			EmailListDao dao = new EmailListDao();
			List<EmailListVo> list = dao.fetchList();
			
			// 데이터 전달
			request.setAttribute( "l", list );
			
			// 포워딩
			RequestDispatcher rd = request.getRequestDispatcher( "/WEB-INF/views/list.jsp" );
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
