package util;

import java.io.IOException;
import java.util.Enumeration;

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
		//engine.setTemplateResolver(templateResolver(servletContext));
		return engine;
	}
	/*
	private static ITemplateResolver templateResolver(ServletContext servletContext) {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		//resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	*/

	public static void render(String template, HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
		WebContext context = new WebContext(req, resp, req.getServletContext());
		Enumeration<String> names = req.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object value = req.getAttribute(name);
            context.setVariable(name, value);
        }

		engine.process(template, context, resp.getWriter());
	}
}
