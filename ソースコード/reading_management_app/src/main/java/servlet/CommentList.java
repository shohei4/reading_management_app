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

import logic.CommentLogic;
import model.CommentModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class CommentList
 */
@WebServlet("/CommentList")
public class CommentList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentList() {
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

			List<CommentModel> commentItems = null;

			CommentLogic logic = new CommentLogic();

			/*
			 * keyがあるとき：キーワード検索
			 * keyがないとき：全権取得
			 */
			if (request.getParameter("key") != null) {

				commentItems = logic.findByKeyWord(userId, request.getParameter("key"));

				//リクエストスコープにキーワードを保存
				request.setAttribute("key", request.getParameter("key"));

			} else {

				commentItems = logic.findAll(userId);

			}

			//リクエストスコープに保存
			request.setAttribute("commentItems", commentItems);

			//コメント一覧へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/commentList.jsp");
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
