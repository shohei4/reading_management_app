package servlet;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class BookUpdate
 */
@WebServlet("/BookUpdate")
public class BookUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメータを取得
		int id = Integer.parseInt(request.getParameter("id"));

		try {

			//本情報を1件取得
			BookItemLogic logic = new BookItemLogic();
			BookItemModel model = logic.findOne(id);

			int isFinished = model.getIsFinished();
			int isShared = model.getIsShared();
			int isDeleted = model.getIsDeleted();

			//リクエストパラメータのnullチェック
			String isFinishedStr = request.getParameter("isFinished");

			if (isFinishedStr != null) {
				isFinished = Integer.parseInt(isFinishedStr);
			}

			String isSharedStr = request.getParameter("isShared");

			if (isSharedStr != null) {
				isShared = Integer.parseInt(isSharedStr);
			}

			String isDeletedStr = request.getParameter("isDeleted");
			if (isDeletedStr != null) {
				isDeleted = Integer.parseInt(isDeletedStr);
			}

			BookItemModel bookItem = new BookItemModel();
			bookItem.setIsFinished(isFinished);
			bookItem.setIsShared(isShared);
			bookItem.setIsDeleted(isDeleted);

			if (!logic.update(bookItem, id)) {

				// エラーがあったときは、bookList.jspへフォワードする。
				//リクエストスコープに保存
				request.setAttribute("bookItem", bookItem);
				response.sendRedirect(request.getContextPath() + "/BookList");

				return;
			}

			//BookListへリダイレクト
			response.sendRedirect(request.getContextPath() + "/BookList");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}

	}

}