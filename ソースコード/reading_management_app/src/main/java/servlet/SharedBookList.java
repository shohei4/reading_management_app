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

import logic.BookItemLogic;
import model.BookItemModel;
import settings.PageSettings;

/**
 * Servlet implementation class SharedBookList
 */
@WebServlet("/SharedBookList")
public class SharedBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SharedBookList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			List<BookItemModel> items = null;

			BookItemLogic logic = new BookItemLogic();

			/*
			 * keyがnullでない：キーワード検索
			 * keyがnull：全権取得
			 */
			if (request.getParameter("key") != null) {

				items = logic.findByKeyWordShared(request.getParameter("key"));

				//キーワードをリクエストスコープに保存

				request.setAttribute("key", request.getParameter("key"));

			} else {

				items = logic.findAllShared();

			}

			//取得した本情報をリクエストスコープに保存
			request.setAttribute("items", items);

			//共有画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sharedBookList.jsp");
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
