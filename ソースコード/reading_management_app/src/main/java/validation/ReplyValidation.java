package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import settings.MessageSettings;
import util.Validation;
import util.ValidationUtil;

/**
 * 返信のバリデーションクラス
 */
public class ReplyValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request　リクエストオブジェクト
	 */
	public ReplyValidation(HttpServletRequest request) {

		super(request);

	}

	//バリデーションを行う
	public Map<String, String> validate() {

		//返信のバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("reply"), 256)) {

			this.errors.put("reply", String.format(MessageSettings.MSG_LENGTH_LONG, "返信", 256));
		}

		if (!ValidationUtil.isMinLength(this.request.getParameter("reply"), 1)) {

			this.errors.put("reply", String.format(MessageSettings.MSG_LENGTH_SHORT, "返信", 1));
		}

		return errors;
	}

}
