package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.TemplateEngineUtil;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet(urlPatterns={"*.action"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try {
			String path = req.getServletPath().substring(1);
			path = path.substring(0, 1).toUpperCase() + path.substring(1); //
			String name = path.replace(".a", "A").replace("/", ".");
			Action action = (Action)Class.forName("web.action."+name).getDeclaredConstructor().newInstance();
			String url = action.execute(req, resp);
			TemplateEngineUtil.render(url, req, resp);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setAttribute("recipient", "World");
//		TemplateEngineUtil.render("index", req, resp);
		doPost(req, resp);
	}

}
