package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BookItemModel;

public class BookItemDAO {

	/**基本になるSQL**/
	private final String BASE_SQL = "SELECT "
			+ "b.id,"
			+ "b.user_id,"
			+ "u.name,"
			+ "b.registration_date,"
			+ "b.title,"
			+ "b.author_name,"
			+ "b.thoughts,"
			+ "b.memo,"
			+ "b.is_finished,"
			+ "b.is_shared,"
			+ "b.is_deleted,"
			+ "b.created_at,"
			+ "b.updated_at "
			+ "FROM book_items b "
			+ "LEFT JOIN users u "
			+ "ON user_id=u.id ";

	/**
	 * ログイン中のユーザーが登録している本情報を全件取得
	 * 
	 * @param connection
	 * @param userId
	 * @return　BookItemModel型のArrayList
	 */
	public List<BookItemModel> findAllPersonal(Connection connection, int userId) {
		List<BookItemModel> list = new ArrayList<BookItemModel>();

		try {
			String sql = BASE_SQL
					+ "WHERE is_deleted=0 "
					+ "AND u.id=? "
					+ "ORDER BY b.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, userId);

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						BookItemModel model = new BookItemModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setThoughts(rs.getString("thoughts"));
						model.setMemo(rs.getString("memo"));
						model.setIsFinished(rs.getInt("is_finished"));
						model.setIsShared(rs.getInt("is_shared"));
						model.setIsDeleted(rs.getInt("is_deleted"));
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
	 * キーワード検索したログイン中のユーザーが登録した本情報を全件取得
	 * 
	 * @param connection
	 * @param userId
	 * @param key
	 * @return　BookItemModelのArrayList
	 */
	public List<BookItemModel> findByKeyWordPersonal(Connection connection, int userId, String key) {
		List<BookItemModel> list = new ArrayList<BookItemModel>();

		try {
			String sql = BASE_SQL
					+ "WHERE is_deleted=0 "
					+ "AND u.id=? "
					+ "LIKE ? "
					+ "ORDER BY b.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setInt(1, userId);
				stmt.setString(2, "%" + key + "%");
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						BookItemModel model = new BookItemModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("users.name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setThoughts(rs.getString("thoughts"));
						model.setMemo(rs.getString("memo"));
						model.setIsFinished(rs.getInt("is_finished"));
						model.setIsShared(rs.getInt("is_shared"));
						model.setIsDeleted(rs.getInt("is_deleted"));
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
	 * 本情報を1件取得
	 * @param connection
	 * @param id
	 * @return　BookItemModel
	 */
	public BookItemModel findOne(Connection connection, int id) {

		BookItemModel model = new BookItemModel();

		try {

			String sql = BASE_SQL
					+ "WHERE is_deleted=0 "
					+ "AND b.id=? "
					+ "ORDER BY b.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, id);

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setThoughts(rs.getString("thoughts"));
						model.setMemo(rs.getString("memo"));
						model.setIsFinished(rs.getInt("is_finished"));
						model.setIsShared(rs.getInt("is_shared"));
						model.setIsDeleted(rs.getInt("is_deleted"));
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

	/**
	 * 共有された本情報を全件取得
	 * @param connection
	 * @return　BookItemModelのArrayList
	 */
	public List<BookItemModel> findAllShared(Connection connection) {

		List<BookItemModel> list = new ArrayList<BookItemModel>();

		try {

			String sql = BASE_SQL
					+ "WHERE is_shared=1 "
					+ "AND is_deleted=0 "
					+ "ORDER BY b.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						BookItemModel model = new BookItemModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setThoughts(rs.getString("thoughts"));
						model.setIsFinished(rs.getInt("is_finished"));
						model.setIsShared(rs.getInt("is_shared"));
						model.setIsDeleted(rs.getInt("is_deleted"));
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
	 *キーワード検索した 共有された本情報を全件取得
	 * @param connection
	 * @param key
	 * @return　BookItemModelのArrayList
	 */
	public List<BookItemModel> findByKeyWordShared(Connection connection, String key) {
		List<BookItemModel> list = new ArrayList<BookItemModel>();

		try {
			String sql = BASE_SQL
					+ "WHERE is_deleted=0 "
					+ "LIKE ? "
					+ "ORDER BY b.id desc";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				stmt.setString(1, "%" + key + "%");
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						BookItemModel model = new BookItemModel();

						model.setId(rs.getInt("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setUserName(rs.getString("name"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setTitle(rs.getString("title"));
						model.setAuthorName(rs.getString("author_name"));
						model.setThoughts(rs.getString("thoughts"));
						model.setIsFinished(rs.getInt("is_finished"));
						model.setIsShared(rs.getInt("is_shared"));
						model.setIsDeleted(rs.getInt("is_deleted"));
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
	 * 本情報を登録
	 * @param connection
	 * @param model
	 * @return　true：成功　false：失敗
	 */
	public boolean create(Connection connection, BookItemModel model) {

		try {

			String sql = "INSERT INTO book_items ("
					+ "user_id,"
					+ "registration_date,"
					+ "title,"
					+ "author_name,"
					+ "thoughts,"
					+ "memo,"
					+ "is_finished,"
					+ "is_shared,"
					+ "is_deleted) "
					+ "VALUES ("
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?"
					+ ")";

			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setInt(1, model.getUserId());
				stmt.setDate(2, model.getRegistrationDate());
				stmt.setString(3, model.getTitle());
				stmt.setString(4, model.getAuthorName());
				stmt.setString(5, model.getThoughts());
				stmt.setString(6, model.getMemo());
				stmt.setInt(7, model.getIsFinished());
				stmt.setInt(8, model.getIsShared());
				stmt.setInt(9, model.getIsDeleted());

				stmt.executeUpdate();

			}

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 本情報を更新
	 * @param connection
	 * @param model
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean update(Connection connection, BookItemModel model, int id) {

		String sql = "UPDATE book_items SET "
				+ "is_finished=?,"
				+ "is_shared=?,"
				+ "is_deleted=? "
				+ "WHERE id=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, model.getIsFinished());
			stmt.setInt(2, model.getIsShared());
			stmt.setInt(3, model.getIsDeleted());
			stmt.setInt(4, id);
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;

	}

	/**
	 * 本情報の編集
	 * @param connection
	 * @param model
	 * @param id
	 * @return　true：成功　false：失敗
	 */
	public boolean edit(Connection connection, BookItemModel model, int id) {

		String sql = "UPDATE book_items SET "
				+ "title=?,"
				+ "author_name=?,"
				+ "thoughts=?,"
				+ "memo=? "
				+ "WHERE id=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, model.getTitle());
			stmt.setString(2, model.getAuthorName());
			stmt.setString(3, model.getThoughts());
			stmt.setString(4, model.getMemo());
			stmt.setInt(5, id);
			stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;

	}

}
