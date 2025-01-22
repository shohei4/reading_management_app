package util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//バリデーション基底クラス（抽象クラス）
public abstract class Validation {

	/**
	 * リクエストオブジェクト
	 */
	protected HttpServletRequest request;

	/**
	 * エラーが発生した項目名とエラー内容を格納するMAP
	 */
	protected Map<String, String> errors;

	/**
	 * コンストラクタ
	 * @param request
	 */
	public Validation(HttpServletRequest request) {
		this.request = request;
		this.errors = new HashMap<String, String>();
	}

	/**
	 * バリデーションの有無を判定
	 * @return true:エラーがある、false:エラーはない
	 */
	public boolean hasErrors() {
		if (this.errors.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * バリデーション実行
	 * 実装は派生先で行う
	 * 
	 * @return　バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public abstract Map<String, String> validate();

}
