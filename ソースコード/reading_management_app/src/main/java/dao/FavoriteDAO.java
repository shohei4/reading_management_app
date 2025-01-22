package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.FavoriteModel;

public class FavoriteDAO {

	/**
	 * 	コメントのいいねを１件登録
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗
	 */
	public boolean createC(Connection connection, FavoriteModel model) {

		try {
			String sql = "INSERT INTO favorite_c ("
					+ "user_id,"
					+ "comment_id"
					+ ") VALUES ("
					+ "?,"
					+ "?"
					+ ")";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getUserId());
				stmt.setInt(2, model.getCommentId());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 返信のいいねを１件登録
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗　
	 */
	public boolean createR(Connection connection, FavoriteModel model) {

		try {
			String sql = "INSERT INTO favorite_r ("
					+ "user_id,"
					+ "reply_id "
					+ ") VALUES ("
					+ "?,"
					+ "?"
					+ ")";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getUserId());
				stmt.setInt(2, model.getReplyId());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 	コメントのいいねを１件削除
	 * @param connection
	 * @param model
	 * @return true：成功　false：失敗
	 */
	public boolean deleteC(Connection connection, FavoriteModel model) {

		try {
			String sql = "DELETE FROM favorite_c "
					+ "WHERE "
					+ "comment_id=? "
					+ "AND "
					+ "user_id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getCommentId());
				stmt.setInt(2, model.getUserId());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;

	}

	/**
	 * 返信のいいねを１件削除
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗
	 */
	public boolean deleteR(Connection connection, FavoriteModel model) {

		try {
			String sql = "DELETE FROM favorite_r "
					+ "WHERE "
					+ "reply_id=? "
					+ "AND "
					+ "user_id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getReplyId());
				stmt.setInt(2, model.getUserId());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;

	}

}
