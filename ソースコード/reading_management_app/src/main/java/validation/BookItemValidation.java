package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;
import util.Validation;
import util.ValidationUtil;

/**
 * 本情報のバリデーションクラス
 */
public class BookItemValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request　リクエストオブジェクト
	 */
	public BookItemValidation(HttpServletRequest request) {

		super(request);

	}
	
	/**
	 * バリデーションチェック
	 */
	public Map<String, String> validate() {
		
		// 題名のバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("title"), 50)) {
			this.errors.put("title", String.format(MessageSettings.MSG_LENGTH_LONG, "題名", 50));
		}

		if (!ValidationUtil.isMinLength(this.request.getParameter("title"), 1)) {
			this.errors.put("title", String.format(MessageSettings.MSG_LENGTH_SHORT, "題名", 1));
		}
		
		//著者名のバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("authorName"), 50)) {
			this.errors.put("authorName", String.format(MessageSettings.MSG_LENGTH_LONG, "著者名", 50));
		}

		if (!ValidationUtil.isMinLength(this.request.getParameter("authorName"), 1)) {
			this.errors.put("authorName", String.format(MessageSettings.MSG_LENGTH_SHORT, "著者名", 1));
		}
		
		//　感想のバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("thoughts"), 256)) {
			this.errors.put("thoughts", String.format(MessageSettings.MSG_LENGTH_LONG, "感想", 256));
		}
		
		// メモのバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("memo"), 256)) {
			this.errors.put("memo", String.format(MessageSettings.MSG_LENGTH_LONG, "題名", 256));
		}

		
		return errors;
	}
}
