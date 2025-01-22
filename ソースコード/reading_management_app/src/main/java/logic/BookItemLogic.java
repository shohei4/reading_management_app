package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookItemDAO;
import database.DBConnection;
import model.BookItemModel;

public class BookItemLogic {

	/**
	 * ログイン中のユーザーが登録した本情報を全件取得
	 * @param userId
	 * @return BookItemModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<BookItemModel> findAllPersonal(int userId) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.findAllPersonal(conn, userId);
		}
	}

	/**
	 * ログイン中のユーザーが登録した本情報をキーワード検索
	 * @param userId
	 * @param key
	 * @return BookItemModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<BookItemModel> findByKeyWordPersonal(int userId, String key)
			throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.findByKeyWordPersonal(conn, userId, key);
		}
	}

	/**
	 * 本情報を１件取得
	 * @param id
	 * @return BookItemModel
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public BookItemModel findOne(int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.findOne(conn, id);

		}

	}

	/**
	 * 共有された本情報を全件取得
	 * @return BookItemModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<BookItemModel> findAllShared() throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.findAllShared(conn);
		}
	}

	/**
	 * 共有された本情報をキーワード検索
	 * @param key
	 * @return UserModelのArrayList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<BookItemModel> findByKeyWordShared(String key) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.findByKeyWordShared(conn, key);
		}
	}

	/**
	 * 本情報を１件登録
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean create(BookItemModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * 本情報を１件更新
	 * @param model
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean update(BookItemModel model, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.update(conn, model, id);
		}
	}

	/**
	 * 本情報を１件編集
	 * @param model
	 * @param id
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean edit(BookItemModel model, int id) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			BookItemDAO dao = new BookItemDAO();

			return dao.edit(conn, model, id);
		}
	}

}
