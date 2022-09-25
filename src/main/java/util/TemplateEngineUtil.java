package util;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class TemplateEngineUtil {
	private static TemplateEngine engine = null;

	private TemplateEngineUtil() {

	}

	public static TemplateEngine getTemplateEngine(ServletContext servletContext) {
		if (engine == null) {
			engine = new TemplateEngine();
			ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
			resolver.setPrefix("/WEB-INF/views/");
			resolver.setSuffix(".html");
			resolver.setTemplateMode(TemplateMode.HTML);
			engine.setTemplateResolver(resolver);

		}
		return engine;
	}

	public static void render(String template, HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		WebContext context = new WebContext(req, resp, req.getServletContext());
//		Enumeration<String> names = req.getAttributeNames();
//        while (names.hasMoreElements()) {
//            String name = names.nextElement();
//            Object value = req.getAttribute(name);
//            context.setVariable(name, value);
//        }
//        names = req.getSession().getAttributeNames();
//        while (names.hasMoreElements()) {
//            String name = names.nextElement();
//            Object value = req.getSession().getAttribute(name);
//            context.getSession().setAttribute(name, value);//.setVariable(name, value);
//        }

		engine.process(template, context, resp.getWriter());
	}
}
