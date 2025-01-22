package logic;

import java.sql.Connection;
import java.sql.SQLException;

import dao.FavoriteDAO;
import database.DBConnection;
import model.FavoriteModel;

public class FavoriteLogic {

	/**
	 * コメントのいいねを１件登録
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean createC(FavoriteModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			FavoriteDAO dao = new FavoriteDAO();

			return dao.createC(conn, model);
		}

	}

	/**
	 * 返信のいいねを１件登録
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean createR(FavoriteModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			FavoriteDAO dao = new FavoriteDAO();

			return dao.createR(conn, model);
		}

	}

	/**
	 * コメントのいいねを１件削除
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean deleteC(FavoriteModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			FavoriteDAO dao = new FavoriteDAO();

			return dao.deleteC(conn, model);
		}
	}

	/**
	 * 返信のいいねを１件削除
	 * @param model
	 * @return true：成功　false：失敗
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean deleteR(FavoriteModel model) throws ClassNotFoundException, SQLException {

		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();

			FavoriteDAO dao = new FavoriteDAO();

			return dao.deleteR(conn, model);
		}
	}

}
