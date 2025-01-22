package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CommentModel;
import model.ReplyModel;

public class CommentDAO {

	/**
	 * 基本となるSQL文
	 */
	private String BASE_SQL = "SELECT DISTINCT "
			+ "c.id,"
			+ "c.user_id,"
			+ "c.book_id,"
			+ "b.title,"
			+ "b.author_name,"
			+ "u.name,"
			+ "c.registration_date,"
			+ "c.comment,"
			+ "c.is_deleted,"
			+ "(SELECT COUNT(user_id) FROM favorite_c WHERE comment_id=c.id AND user_id=?) AS favorite,"
			+ "(SELECT COUNT(user_id) FROM favorite_c WHERE comment_id=c.id) AS favorite_count,"
			+ "c.created_at,"
			+ "c.updated_at "
			+ "FROM comment c "
			+ "LEFT JOIN users u "
			+ "ON c.user_id=u.id "
			+ "LEFT JOIN book_items b "
			+ "ON c.book_id=b.id "
			+ "LEFT JOIN reply r "
			+ "ON c.id=r.comment_id "
			+ "LEFT JOIN favorite_c f "
			+ "ON c.id=f.comment_id ";

	/**
	 * コメントを全件取得
	 * @param connection
	 * @return　CommentModelのArrayList
	 */
	public List<CommentModel> findAll(Connection connection, int userId) {

		List<CommentModel> list = new ArrayList<CommentModel>();

		try {

			String sql = BASE_SQL
					+ "WHERE c.is_deleted=0 "
					+ "ORDER BY c.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, userId);

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						CommentModel model = new CommentModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setBookId(rs.getInt("book_id"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setComment(rs.getString("comment"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setFavorite(rs.getInt("favorite"));
						model.setFavoriteCount(rs.getInt("favorite_count"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

						//返信コメント一覧を議論モデルに保存
						List<ReplyModel> replyItems = new ArrayList<ReplyModel>();
						ReplyDAO rdao = new ReplyDAO();
						replyItems = rdao.findAll(connection, userId, rs.getInt("id"));
						model.setReplyItems(replyItems);

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
	 * キーワード検索したコメントを全件取得
	 * @param connection
	 * @param key
	 * @return　CommentModelのArrayList
	 */
	public List<CommentModel> findByKeyWord(Connection connection, int uesrId, String key) {

		List<CommentModel> list = new ArrayList<CommentModel>();

		try {

			String sql = BASE_SQL
					+ "WEHRE is_deleted=0 "
					+ "LIKE ? "
					+ "ORDER BY c.id desc";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, uesrId);
				stmt.setString(2, "%" + key + "%");

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						CommentModel model = new CommentModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("u.name"));
						model.setBookId(rs.getInt("book_id"));
						model.setTitle(rs.getString("b.title"));
						model.setAuthorName(rs.getString("b.author_name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setComment(rs.getString("comment"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setFavorite(rs.getInt("favorite"));
						model.setFavoriteCount(rs.getInt("favorite_count"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("udpated_at"));
						;

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
	 * コメントを1件取得
	 * @param connection
	 * @param id
	 * @return　CommentModel
	 */
	public CommentModel findOne(Connection connection, int id) {

		CommentModel model = new CommentModel();

		try {
			String sql = "SELECT id,comment "
					+ "FROM comment "
					+ "WHERE id=? "
					+ "AND is_deleted=0";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, id);

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {

						model.setId(rs.getInt("id"));
						model.setComment(rs.getString("comment"));

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
	 * コメントを１件追加する
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗
	 */
	public boolean create(Connection connection, CommentModel model) {

		try {
			String sql = "INSERT INTO comment ("
					+ "user_id,"
					+ "book_id,"
					+ "registration_date,"
					+ "comment,"
					+ "is_deleted"
					+ ") VALUES ("
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?)";
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, model.getUserId());
				stmt.setInt(2, model.getBookId());
				stmt.setDate(3, model.getRegistrationDate());
				stmt.setString(4, model.getComment());
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
	 * コメントを１件更新する
	 * @param connection
	 * @param isDeleted
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean update(Connection connection, int isDeleted, int id) {

		try {
			String sql = "UPDATE comment SET "
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
	 * コメントを１件編集
	 * @param connection
	 * @param comment
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean edit(Connection connection, String comment, int id) {

		try {
			String sql = "UPDATE comment SET "
					+ "comment=? "
					+ "WHERE id=?";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, comment);
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
