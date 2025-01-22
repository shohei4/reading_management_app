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
import logic.CommentLogic;
import model.BookItemModel;
import model.CommentModel;
import model.UserModel;
import settings.PageSettings;
import validation.CommentValidation;

/**
 * Servlet implementation class CommentRegister
 */
@WebServlet("/CommentRegister")
public class CommentRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			int bookId = Integer.parseInt(request.getParameter("bookId"));

			BookItemLogic itemlogic = new BookItemLogic();

			//コメント投稿画面で表示するために本情報を1件取得
			BookItemModel bookItem = itemlogic.findOne(bookId);

			request.setAttribute("bookItem", bookItem);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/commentRegister.jsp");
			dispatcher.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
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
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String comment = request.getParameter("comment");

		try {

			//セションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("user");

			//日付の取得
			Date today = new Date();
			request.setAttribute("today", today);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = simpleDateFormat.format(today);
			java.sql.Date registrationDate = java.sql.Date.valueOf(formattedDate);

			//バリデーションチェック
			CommentValidation validate = new CommentValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);
				
				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> commentModel = new HashMap<String, String>();

				commentModel.put("comment", comment);

				BookItemLogic itemLogic = new BookItemLogic();
				BookItemModel bookItem = itemLogic.findOne(bookId);

				request.setAttribute("bookItem", bookItem);

				request.setAttribute("commentModel", commentModel);

				//コメント投稿画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/commentRegister.jsp");
				dispatcher.forward(request, response);

			}

			//リクエストパラメータをモデルに設定
			CommentModel commentModel = new CommentModel();
			commentModel.setUserId(user.getId());
			commentModel.setBookId(bookId);
			commentModel.setRegistrationDate(registrationDate);
			commentModel.setComment(comment);

			CommentLogic logic = new CommentLogic();

			//コメントを1件追加
			if (!logic.create(commentModel)) {

				//エラーがあった場合再度本所法を表示するために1件取得
				int id = Integer.parseInt(request.getParameter("bookId"));
				BookItemLogic itemLogic = new BookItemLogic();
				BookItemModel bookItem = itemLogic.findOne(id);

				request.setAttribute("bookItem", bookItem);

				request.setAttribute("commentModel", commentModel);

				//コメント投稿画面にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/commentRegister.jsp");
				dispatcher.forward(request, response);

			}

			//CommentListへリダイレクト
			response.sendRedirect(request.getContextPath() + "/CommentList");

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
