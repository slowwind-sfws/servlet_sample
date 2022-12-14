package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import biz.domain.UserBean;

/**
 * Servlet Filter implementation class CommonFiler
 *
 * 研修で学習したFilterクラスを利用します。
 * ログイン・ログアウトやログ収集など、「開発者が意識したくない」項目はFilterに集めるのが良策です。
 * 「システムで解決したい機能ではないコードがあちこちに散在する」ことを「横断的関心事」と呼び、
 * 機能実装チームと基盤開発チームに分業した時の後者の役割となります。
 * 詳細は「Aspect-OrientedProgramming」で検索してみてください。
 */
@WebFilter(urlPatterns = { "*.action" })
public class CommonFiler implements Filter {

	/**
	 * Default constructor.
	 */
	public CommonFiler() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 毎回文字エンコードを変換するのは煩わしいですね(笑)
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// ユーザのログイン状態は、「セッションに存在するかどうか」で判断することが多いですが、
		// 今回はSessionに常駐させ、「認証したかどうか」で判断します。
		// SpringSecurityっぽいアイデアです。
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		UserBean user = (UserBean) httpRequest.getSession().getAttribute("user");

		if (user == null) {
			user = new UserBean();
		}
		httpRequest.getSession().setAttribute("user", user);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}
}
