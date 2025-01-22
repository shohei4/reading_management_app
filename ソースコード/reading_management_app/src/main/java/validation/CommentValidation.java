package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;
import util.Validation;
import util.ValidationUtil;

/**
 * コメントのバリデーションクラス
 */
public class CommentValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request　リクエストオブジェクト
	 */
	public CommentValidation(HttpServletRequest request) {

		super(request);

	}

	/**
	 * バリデーションチェック
	 */
	public Map<String, String> validate() {

		//コメントのバリデーションチェック
		if (!ValidationUtil.isMaxLength(this.request.getParameter("comment"), 256)) {
			this.errors.put("comment", String.format(MessageSettings.MSG_LENGTH_LONG, "コメント", 256));
		}

		if (!ValidationUtil.isMinLength(this.request.getParameter("comment"), 1)) {
			this.errors.put("comment", String.format(MessageSettings.MSG_LENGTH_SHORT, "コメント", 1));
		}

		return errors;

	}

}
