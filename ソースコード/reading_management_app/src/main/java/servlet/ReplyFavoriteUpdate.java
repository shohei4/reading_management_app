package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.FavoriteLogic;
import model.FavoriteModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class ReplyFavoriteServlet
 */
@WebServlet("/ReplyFavoriteUpdate")
public class ReplyFavoriteUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyFavoriteUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメータを取得
		String replyIdStr = request.getParameter("replyId");

		String flagStr = request.getParameter("flag");

		try {

			//セションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel user = (UserModel) session.getAttribute("user");
			int userId = user.getId();

			FavoriteModel favoriteModel = new FavoriteModel();

			favoriteModel.setUserId(userId);

			FavoriteLogic logic = new FavoriteLogic();

			//リクエストパラメータのnullチェック
			int replyId = 0;

			if (replyIdStr != null) {

				replyId = Integer.parseInt(replyIdStr);

				favoriteModel.setReplyId(replyId);

			}

			//処理の分岐に使用する値flagのnullチェック
			int flag = 0;

			if (flagStr != null) {

				flag = Integer.parseInt(flagStr);
			}

			/*
			 * flag = 1：返信をいいねに登録
			 * flag = 2：いいねから返信を削除
			 */
			if (flag == 1) {

				if (!logic.createR(favoriteModel)) {

					//エラーがあった場合CommentListへリダイレクト
					response.sendRedirect(request.getContextPath() + "/CommentList");

				}

			} else if (flag == 2) {

				if (!logic.deleteR(favoriteModel)) {

					//エラーがあった場合CommentListへリダイレクト
					response.sendRedirect(request.getContextPath() + "/CommentList");

				}

			}

			//リクエストスコープに保存
			request.setAttribute("favoriteModel", favoriteModel);

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
