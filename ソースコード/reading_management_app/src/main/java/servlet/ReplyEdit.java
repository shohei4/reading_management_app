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

import logic.ReplyLogic;
import model.ReplyModel;
import settings.PageSettings;
import validation.ReplyValidation;

/**
 * Servlet implementation class ReplyEdit
 */
@WebServlet("/ReplyEdit")
public class ReplyEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyEdit() {
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

			ReplyLogic logic = new ReplyLogic();

			//返信編集画面で表示するために返信コメントを1件取得
			ReplyModel replyModel = logic.findOne(id);

			request.setAttribute("replyModel", replyModel);

			//返信編集画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/replyEdit.jsp");
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
		String reply = request.getParameter("reply");

		try {

			//バリデーションチェック
			ReplyValidation validate = new ReplyValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);
				
				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> replyModel = new HashMap<String, String>();

				replyModel.put("id", String.valueOf(id));
				replyModel.put("reply", reply);

				request.setAttribute("replyModel", replyModel);

				//返信編集画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/replyEdit.jsp");
				dispatcher.forward(request, response);

				return;

			}

			ReplyLogic logic = new ReplyLogic();

			//リクエストパラメータをモデルに設定
			ReplyModel replyModel = new ReplyModel();
			replyModel.setId(id);
			replyModel.setReply(reply);

			//返信編集する
			if (!logic.edit(reply, id)) {

				request.setAttribute("replyModel", replyModel);

				//エラーがあった場合コメント編集画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/replyEdit.jsp");
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
