package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AddHeaderFilter
 */
@WebFilter(filterName = "AddHeaderFilter")
public class AddHeaderFilter implements Filter {

	/**
	 * 追加のヘッダ情報を設定します。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 文字コードをUTF-8に設定する。
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// ServletResponseクラスではsetHeaderメソッドが未定義のため、HttpServletResponseクラスオブジェクトににキャストする。
		HttpServletResponse res = (HttpServletResponse) response;

		// キャッシュされないようにヘッダに情報を埋め込む。
		res.setHeader("Cache-Control", "no-cache, private");

		// キャッシュの有効期限に過去の日時を設定する。
		res.setHeader("Expires", "Thu, 20 Jan 2022 00:00:00 GMT");

		chain.doFilter(request, response);
	}
}