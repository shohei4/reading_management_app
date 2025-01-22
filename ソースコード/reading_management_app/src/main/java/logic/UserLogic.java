package logic;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import database.DBConnection;
import model.UserModel;

public class UserLogic {

	/**
	 * emailとパスワードから特定のユーザー情報を1件取得
	 * 
	 * @param email
	 * @param password
	 * @return UserModel
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public UserModel find(String email, String password) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.find(conn, email, password);

		}
	}

	/**
	 * ユーザー情報を１件追加する
	 * 
	 * @param model
	 * @return　実行結果 1:成功、その他:エラーコード
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int create(UserModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * ユーザー情報を1件更新
	 * 
	 * @param model
	 * @return　実行結果 1:成功、その他:エラーコード
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int update(UserModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.update(conn, model);
		}

	}

}
