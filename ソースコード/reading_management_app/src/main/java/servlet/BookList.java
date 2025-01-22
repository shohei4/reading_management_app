package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.BookItemLogic;
import model.BookItemModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class PersonalBookList
 */
@WebServlet("/BookList")
public class BookList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			//セションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("user");

			int userId = user.getId();

			BookItemLogic logic = new BookItemLogic();

			List<BookItemModel> items = null;

			/*
			 * keyあり；キーワード検索
			 * keyなし；全権取得
			 */
			if (request.getParameter("key") != null) {

				items = logic.findByKeyWordPersonal(user.getId(), request.getParameter("key"));

				//リクエストスコープに保存
				request.setAttribute("key", request.getParameter("key"));

			} else {

				items = logic.findAllPersonal(userId);

			}

			//リクエストスコープに保存
			request.setAttribute("items", items);

			//個人用画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/bookList.jsp");
			dispatcher.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
