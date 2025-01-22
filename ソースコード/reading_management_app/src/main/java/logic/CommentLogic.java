package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CommentDAO;
import database.DBConnection;
import model.CommentModel;

public class CommentLogic {

	/**
	 * コメントを全件取得
	 * @return CommentModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<CommentModel> findAll(int userId) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.findAll(conn, userId);
		}
	}

	/**
	 * コメントを１件取得
	 * @param id
	 * @return CommentModel
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CommentModel findOne(int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.findOne(conn, id);
		}
	}

	/**
	 * コメントをキーワード検索
	 * @param key
	 * @return	CommentModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<CommentModel> findByKeyWord(int userId, String key) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.findByKeyWord(conn, userId, key);
		}
	}

	/**
	 * コメントを１件登録
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean create(CommentModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * コメントを１件更新
	 * @param isDeleted
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean update(int isDeleted, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.update(conn, isDeleted, id);
		}
	}

	/**
	 * コメントを１件編集
	 * @param comment
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean edit(String comment, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			CommentDAO dao = new CommentDAO();

			return dao.edit(conn, comment, id);
		}
	}
}
