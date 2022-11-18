package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		req.setAttribute("recipient", "World");
		UserBean user = (UserBean) req.getSession().getAttribute("user");

		if (user == null) {
			user = new UserBean();
		}
		req.getSession().setAttribute("user", user);
		TemplateEngineUtil.render("index", req, resp);*/
		resp.sendRedirect("/servlet_sample/home.action");
	}

}
