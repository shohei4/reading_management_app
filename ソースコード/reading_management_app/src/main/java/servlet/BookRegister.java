package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import validation.BookItemValidation;

/**
 * Servlet implementation class BookRegisters
 */
@WebServlet("/BookRegister")
public class BookRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookRegister.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメータを取得
		String title = request.getParameter("title");
		String authorName = request.getParameter("authorName");
		String thoughts = request.getParameter("thoughts");
		String memo = request.getParameter("memo");

		try {

			//今日の日付を取得する
			//現在時刻のインスタンス
			Date today = new Date();
			//時刻の書式設定
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = simpleDateFormat.format(today);
			//PreparedStatmentに登録
			java.sql.Date registrationDate = java.sql.Date.valueOf(formattedDate);

			//バリデーションチェック
			BookItemValidation validate = new BookItemValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);
				
				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> bookItem = new HashMap<String, String>();
				bookItem.put("title", title);
				bookItem.put("authorName", authorName);
				bookItem.put("thoughts", thoughts);
				bookItem.put("memo", memo);

				request.setAttribute("bookItem", bookItem);

				//本情報登録画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookRegister.jsp");
				dispatcher.forward(request, response);

			}

			//セッションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("user");

			//モデルにパラメータを設定
			BookItemModel bookItem = new BookItemModel();
			bookItem.setUserId(user.getId());
			bookItem.setRegistrationDate(registrationDate);
			bookItem.setTitle(title);
			bookItem.setAuthorName(authorName);
			bookItem.setThoughts(thoughts);
			bookItem.setMemo(memo);

			//テーブルに登録
			BookItemLogic logic = new BookItemLogic();
			if (!logic.create(bookItem)) {
				// エラーがあったときは、bookRegister.jspへフォワードする。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bookRegister.jsp");
				//リクエストスコープに保存
				request.setAttribute("bookItem", bookItem);
				dispatcher.forward(request, response);

				return;
			}

			//BookListへリダイレクト
			response.sendRedirect(request.getContextPath() + "/BookList");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;

		}

	}

}
