package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.BookItemLogic;
import model.BookItemModel;
import settings.PageSettings;
import validation.BookItemValidation;

/**
 * Servlet implementation class BookEdit
 */
@WebServlet("/BookEdit")
public class BookEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookEdit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメータを取得
		int id = Integer.parseInt(request.getParameter("id"));

		try {

			//本所法編集画面で表示するために本情報を1件取得
			BookItemLogic logic = new BookItemLogic();
			BookItemModel model = logic.findOne(id);

			//リクエストスコープに保存
			request.setAttribute("bookItem", model);

			//本情報編集画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookEdit.jsp");
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

		//リクエストパラメータを取得
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String authorName = request.getParameter("authorName");
		String thoughts = request.getParameter("thoughts");
		String memo = request.getParameter("memo");

		try {

			//バリデーションチェック
			BookItemValidation validate = new BookItemValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);

				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> bookItem = new HashMap<String, String>();
				bookItem.put("id", String.valueOf(id));
				bookItem.put("title", title);
				bookItem.put("authorName", authorName);
				bookItem.put("thoughts", thoughts);
				bookItem.put("memo", memo);

				request.setAttribute("bookItem", bookItem);

				//本情報編集画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookEdit.jsp");
				dispatcher.forward(request, response);

				return;

			}

			//モデルにパラメータを設定
			BookItemModel bookItem = new BookItemModel();
			bookItem.setId(id);
			bookItem.setTitle(title);
			bookItem.setAuthorName(authorName);
			bookItem.setThoughts(thoughts);
			bookItem.setMemo(memo);

			//本情報の更新
			BookItemLogic logic = new BookItemLogic();

			if (!logic.edit(bookItem, id)) {

				//リクエストスコープに保存
				request.setAttribute("bookItem", bookItem);
				//エラーがあった場合はbookEditへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookEdit.jsp");
				dispatcher.forward(request, response);

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
