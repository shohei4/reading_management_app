package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;
import util.Validation;
import util.ValidationUtil;

/**
 * ユーザー情報のバリデーションクラス
 */
public class UserValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request　リクエストオブジェクト
	 */
	public UserValidation(HttpServletRequest request) {
		super(request);
	}
	
	/**
	 * バリデーションチェック
	 */
	public Map<String, String> validate() {

		//メールアドレスバリデーション
		if (!ValidationUtil.isEmail(this.request.getParameter("email"))) {
			this.errors.put("email", MessageSettings.MSG_EMAIL_FAILURE);
		}

		//パスワードバリデーション
		if (!ValidationUtil.isPassword(this.request.getParameter("password"))) {
			this.errors.put("password", MessageSettings.MSG_PASSWORD_FAILURE);
		}

		// ニックネームのバリデーション
		if (!ValidationUtil.isMinLength(this.request.getParameter("name"), 1)) {
			this.errors.put("name", String.format(MessageSettings.MSG_REQUIRED, "ニックネーム"));
		}

		if (!ValidationUtil.isMaxLength(this.request.getParameter("name"), 50)) {
			this.errors.put("name", String.format(MessageSettings.MSG_LENGTH_LONG, "ニックネーム", 50));
		}

		return errors;

	}

}
