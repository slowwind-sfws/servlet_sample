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
 */
@WebFilter(urlPatterns = { "*.action" })
public class CommonFiler implements Filter {

	/**
	 * Default constructor.
	 */
	public CommonFiler() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// place your code here
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// User
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
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
