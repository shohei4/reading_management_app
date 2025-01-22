package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UserModel;
import settings.DatabaseSettings;

public class UserDAO {

	/**
	 * emailとパスワードから特定のユーザーを1件権取得
	 * 
	 * @param connection
	 * @param email
	 * @param password
	 * @return UserModel 
	 */
	public UserModel find(Connection connection, String email, String password) {
		//レコードを格納するUserModelのインスタンスを生成
		UserModel model = new UserModel();
		try {
			//SQL文の設定
			String sql = "SELECT * FROM users WHERE email=? and password=?";

			//SQL文の実行準備
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, email);
				stmt.setString(2, password);

				//SQL文の実行
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						model.setId(rs.getInt("id"));
						model.setEmail(rs.getString("email"));
						model.setPassword(rs.getString("password"));
						model.setName(rs.getString("name"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));
					} else {
						model = null;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return model;

	}

	public UserModel findOne(Connection connection, int id) {
		UserModel model = new UserModel();
		try {
			//SQL文の設定
			String sql = "SELECT * FROM users WHERE id=?";

			//SQL文の実行準備
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, id);
				//SQL文の実行
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						model.setId(rs.getInt("id"));
						model.setEmail(rs.getString("email"));
						model.setPassword(rs.getString("password"));
						model.setName(rs.getString("name"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("update_at"));
					} else {
						model = null;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return model;

	}

	/**
	 * ユーザー情報を１件追加する
	 * 
	 * @param connection
	 * @param model
	 * @return　実行結果 1:成功、その他:エラーコード
	 */
	public int create(Connection connection, UserModel model) {
		try {
			//SQL文の設定
			String sql = "INSERT INTO users(email,password,name) "
					+ "VALUES ("
					+ "?,"
					+ "?,"
					+ "?)";

			//SQL文の実行準備
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				//パラメータの設定
				stmt.setString(1, model.getEmail());
				stmt.setString(2, model.getPassword());
				stmt.setString(3, model.getName());

				//SQL文の実行
				stmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return e.getErrorCode();
		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}

	/**
	 * ユーザー情報を1件更新する
	 * 
	 * @param connection
	 * @param model
	 * @return　実行結果 1:成功、その他:エラーコード
	 */
	public int update(Connection connection, UserModel model) {

		try {
			//SQL文の設定
			String sql = "UPDATE users SET email=?, password=?, name=? WHERE id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				//パラメータの設定
				stmt.setString(1, model.getEmail());
				stmt.setString(2, model.getPassword());
				stmt.setString(3, model.getName());
				stmt.setInt(4, model.getId());

				//SQL文の実行
				stmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return e.getErrorCode();
		}
		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}
}
