package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ReplyDAO;
import database.DBConnection;
import model.ReplyModel;

public class ReplyLogic {

	/**
	 * 返信を全件取得
	 * @param commentId
	 * @return ReplyModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ReplyModel> findAll(int userId, int commentId) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.findAll(conn, userId, commentId);
		}
	}

	/**
	 * 返信を１件取得
	 * @param id
	 * @return ReplyModel
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ReplyModel findOne(int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.findOne(conn, id);
		}
	}

	/**
	 * 返信をキーワード検索
	 * @param commentId
	 * @param key
	 * @return ReplyModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ReplyModel> findByKeyWord(int userId, int commentId, String key)
			throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.findByKeyWord(conn, userId, commentId, key);
		}
	}

	/**
	 * 返信を１件登録
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean create(ReplyModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * 返信を１件更新
	 * @param isDeleted
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean update(int isDeleted, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.update(conn, isDeleted, id);
		}
	}

	/**
	 * 返信を１件編集
	 * @param reply
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean edit(String reply, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			ReplyDAO dao = new ReplyDAO();

			return dao.edit(conn, reply, id);
		}
	}

}
