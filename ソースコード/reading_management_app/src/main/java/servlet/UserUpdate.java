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
import javax.servlet.http.HttpSession;

import logic.UserLogic;
import model.UserModel;
import settings.DatabaseSettings;
import settings.MessageSettings;
import settings.PageSettings;
import validation.UserValidation;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ユーザー情報更新ページへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
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
		String name = request.getParameter("name");

		try {
			//セッションスコープからユーザー情報を取得
			HttpSession session = request.getSession();
			UserModel userModel = (UserModel) session.getAttribute("user");

			// バリデーションチェックを行う。
			UserValidation validate = new UserValidation(request);
			Map<String, String> errors = validate.validate();

			// バリデーションエラーがあった時。
			if (validate.hasErrors()) {
				request.setAttribute("errors", errors);
				// JSPのinputタグのvalue値の表示に使うためにリクエストパラメータをMapに保存する。
				Map<String, String> user = new HashMap<String, String>();
				user.put("email", email);
				user.put("password", password);
				user.put("name", name);
				request.setAttribute("user", user);

				// ユーザー更新ページへフォワードして終了する。
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//リクエストパラメータをモデルに格納
			UserModel user = new UserModel();
			user.setEmail(email);
			user.setPassword(password);
			user.setName(name);
			user.setId(userModel.getId());

			UserLogic logic = new UserLogic();
			int ret = logic.update(user);

			switch (ret) {
			case DatabaseSettings.DB_EXECUTION_SUCCESS:
				//操作成功
				session.removeAttribute("user");
				response.sendRedirect(request.getContextPath() + "/Login");
				return;

			case DatabaseSettings.DB_EXECUTION_FAILURE_ERR_DUP_KEYNAME:
				// ユニークKEYの重複  一意制約違反が起きた場合
				request.setAttribute("db_error", MessageSettings.MSG_ER_DUP_KEYNAME);

			default:
				//その他
				request.setAttribute("db_error", MessageSettings.MSG_ERROR_OCCURRED);
				break;
			}
			request.setAttribute("user", user);

			// ユーザー更新ページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
			dispatcher.forward(request, response);

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
