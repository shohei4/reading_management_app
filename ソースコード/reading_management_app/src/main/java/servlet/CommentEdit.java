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

import logic.CommentLogic;
import model.CommentModel;
import settings.PageSettings;
import validation.CommentValidation;

/**
 * Servlet implementation class CommentEdit
 */
@WebServlet("/CommentEdit")
public class CommentEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentEdit() {
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

			//コメント編集画面で表示するためにコメント情報を1件取得
			CommentLogic logic = new CommentLogic();

			CommentModel model = logic.findOne(id);

			request.setAttribute("commentModel", model);

			//コメント編集画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/commentEdit.jsp");
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
		String comment = request.getParameter("comment");

		try {

			//バリデーションチェック
			CommentValidation validate = new CommentValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);
				
				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> commentModel = new HashMap<String, String>();

				commentModel.put("id", String.valueOf(id));
				commentModel.put("comment", comment);

				request.setAttribute("commentModel", commentModel);

				//コメント編集画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/commentEdit.jsp");
				dispatcher.forward(request, response);

				return;
			}

			//リクエストパラメータをモデルに設定
			CommentModel commentModel = new CommentModel();
			commentModel.setId(id);
			commentModel.setComment(comment);
			CommentLogic logic = new CommentLogic();

			if (!logic.edit(comment, id)) {
				//エラーがあった場合はcommentEditへフォワード
				request.setAttribute("commentModel", commentModel);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/commentEdit.jsp");
				dispatcher.forward(request, response);
			}

			//CommentListへリダイレクト
			response.sendRedirect(request.getContextPath() + "/CommentList");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;

		}
	}

}
