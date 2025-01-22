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

import logic.UserLogic;
import model.UserModel;
import settings.MessageSettings;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.LOGIN_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//リクエストパラメータを取得
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			//リクエストパラメータを元にユーザー情報を検索
			UserLogic logic = new UserLogic();
			//左辺のユーザーモデル型の値を変数user代入しているだけなので新たにインスタンスを生成する必要がない
			UserModel user = logic.find(email, password);

			//特定のユーザーが見つからなかったとき
			if (user == null) {
				// ユーザーが見つからなかったときは、エラーメッセージをリクエストスコープに設定する。
				request.setAttribute("error", MessageSettings.MSG_LOGIN_FAILURE);

				// ログインに使用した情報を再表示するために、リクエストスコープに保存する。
				user = new UserModel();
				user.setEmail(request.getParameter("email"));
				user.setPassword(request.getParameter("password"));
				request.setAttribute("user", user);

				// ログインページへフォワードする。
				RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.LOGIN_JSP);
				dispatcher.forward(request, response);

				return;

			}

			//検索結果をセッションスコープに保存
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			// リダイレクトする。
			response.sendRedirect(request.getContextPath() + "/BookList");

			return;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}

	}

}
