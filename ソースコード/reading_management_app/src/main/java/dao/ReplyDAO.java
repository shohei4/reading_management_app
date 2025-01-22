package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ReplyModel;

public class ReplyDAO {

	/**
	 * 基本となるSQL文
	 */
	private String BASE_SQL = "SELECT DISTINCT "
			+ "r.id,"
			+ "r.user_id,"
			+ "name,"
			+ "r.comment_id,"
			+ "r.registration_date,"
			+ "r.reply,"
			+ "r.is_deleted,"
			+ " (SELECT user_Id FROM favorite_r WHERE reply_id=r.id AND user_id=?) AS favorite,"
			+ "(SELECT COUNT(user_id) FROM favorite_r WHERE reply_id=r.id) AS favorite_count,"
			+ "r.created_at,"
			+ "r.updated_at "
			+ "FROM reply r "
			+ "LEFT JOIN users u "
			+ "ON r.user_id=u.id "
			+ "LEFT JOIN favorite_r f "
			+ "ON r.id=f.reply_id ";

	/**
	 * 返信を全件取得
	 * @param connection
	 * @param commentId
	 * @return　ReplyModelのArrayList
	 */
	public List<ReplyModel> findAll(Connection connection, int userId, int commentId) {

		List<ReplyModel> list = new ArrayList<ReplyModel>();

		try {

			String sql = BASE_SQL
					+ "WHERE r.is_deleted=0 "
					+ "AND r.comment_id=? "
					+ "ORDER BY r.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, userId);
				stmt.setInt(2, commentId);

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ReplyModel model = new ReplyModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setCommentId(rs.getInt("comment_id"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setReply(rs.getString("reply"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setFavorite(rs.getInt("favorite"));
						model.setFavoriteCount(rs.getInt("favorite_count"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

						list.add(model);
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}
		return list;
	}

	/**
	 * 返信をキーワード検索
	 * @param connection
	 * @param commentId
	 * @param key
	 * @return　ReplyModelのArrayList
	 */
	public List<ReplyModel> findByKeyWord(Connection connection, int userId, int commentId, String key) {

		List<ReplyModel> list = new ArrayList<ReplyModel>();

		try {

			String sql = BASE_SQL
					+ "WEHRE r.is_deleted=0 "
					+ "AND r.comment_id=? "
					+ "LIKE ? "
					+ "ORDER BY r.id desc";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, userId);
				stmt.setInt(2, commentId);
				stmt.setString(3, "%" + key + "%");

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						ReplyModel model = new ReplyModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setCommentId(rs.getInt("comment_id"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setReply(rs.getString("reply"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setFavorite(rs.getInt("favorite"));
						model.setFavoriteCount(rs.getInt("favorite_count"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

						list.add(model);
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}
		return list;
	}

	/**
	 * 返信を１件取得
	 * @param connection
	 * @param id
	 * @return　 ReplyModel
	 */
	public ReplyModel findOne(Connection connection, int id) {

		ReplyModel model = new ReplyModel();

		try {
			String sql = "SELECT id,comment_id,reply "
					+ "FROM reply "
					+ "WHERE id=? "
					+ "AND is_deleted=0";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, id);

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {

						model.setId(rs.getInt("id"));
						model.setCommentId(rs.getInt("comment_id"));
						model.setReply(rs.getString("reply"));

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
	 * 返信を１件登録
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗
	 */
	public boolean create(Connection connection, ReplyModel model) {

		try {
			String sql = "INSERT INTO reply ("
					+ "user_id,"
					+ "comment_id,"
					+ "registration_date,"
					+ "reply,"
					+ "is_deleted"
					+ ") VALUES ("
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?)";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getUserId());
				stmt.setInt(2, model.getCommentId());
				stmt.setDate(3, model.getRegistrationDate());
				stmt.setString(4, model.getReply());
				stmt.setInt(5, model.getIsDeleted());
				stmt.executeUpdate();
			}

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 返信を１件更新
	 * @param connection
	 * @param isDeleted
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean update(Connection connection, int isDeleted, int id) {

		try {
			String sql = "UPDATE reply SET "
					+ "is_deleted=? "
					+ "WHERE id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, isDeleted);
				stmt.setInt(2, id);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;

	}

	/**
	 * 返信を１件編集
	 * @param connection
	 * @param reply
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean edit(Connection connection, String reply, int id) {

		try {
			String sql = "UPDATE reply SET "
					+ "reply=? "
					+ "WHERE id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, reply);
				stmt.setInt(2, id);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;

	}

}
