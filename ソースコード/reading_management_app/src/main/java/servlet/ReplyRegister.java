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

import logic.CommentLogic;
import logic.ReplyLogic;
import model.CommentModel;
import model.ReplyModel;
import model.UserModel;
import settings.PageSettings;
import validation.ReplyValidation;

/**
 * Servlet implementation class ReplyRegister
 */
@WebServlet("/ReplyRegister")
public class ReplyRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		try {

			CommentLogic logic = new CommentLogic();
			CommentModel model = logic.findOne(id);

			request.setAttribute("model", model);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/replyRegister.jsp");
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
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		String reply = request.getParameter("reply");

		try {

			//セションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("user");

			//日付を取得
			Date today = new Date();
			request.setAttribute("today", today);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = simpleDateFormat.format(today);
			java.sql.Date registrationDate = java.sql.Date.valueOf(formattedDate);

			//バリデーションチェック
			ReplyValidation validate = new ReplyValidation(request);
			Map<String, String> errors = validate.validate();

			if (validate.hasErrors()) {

				request.setAttribute("errors", errors);

				//jsp側で表示するためにリクエストパラメータをMapに入れる
				Map<String, String> replyModel = new HashMap<String, String>();

				replyModel.put("reply", reply);

				//返信投稿画面でコメントを再表示するためにコメントを１件取得しリクエストスコープに保存
				CommentLogic logic = new CommentLogic();
				CommentModel model = logic.findOne(commentId);

				request.setAttribute("model", model);

				request.setAttribute("replyModel", replyModel);

				//返信投稿画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/replyRegister.jsp");
				dispatcher.forward(request, response);

				return;

			}

			//モデルにリクエストパラメータを設定
			ReplyModel replyModel = new ReplyModel();

			replyModel.setUserId(user.getId());
			replyModel.setCommentId(commentId);
			replyModel.setRegistrationDate(registrationDate);
			replyModel.setReply(reply);

			ReplyLogic replyLogic = new ReplyLogic();

			//返信を投稿する
			if (!replyLogic.create(replyModel)) {

				//エラーがあった場合返信投稿画面で表示するためにコメント情報を1件取得
				int id = Integer.parseInt(request.getParameter("commentId"));
				CommentLogic logic = new CommentLogic();
				CommentModel model = logic.findOne(id);

				request.setAttribute("model", model);

				request.setAttribute("replyModel", replyModel);

				//返信投稿画面へフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/replyRegister.jsp");
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
