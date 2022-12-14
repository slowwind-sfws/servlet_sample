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

/*
 * 今回、実験的にテンプレートエンジンをJSPから「Thymeleaf」に変更しています。
 * 「Thymeleaf」はSpringFrameworkで採用されているエンジンで、スクリプト部分を
 * HTMLタグ内の「th:」で始まる属性（Attribute）に内包できるところが特徴です。
 * Webデザイナーと分業するときに喜ばれます(笑)
 * ※今回の実装では、代償としてリダイレクトができなくなっております汗
 */
public class TemplateEngineUtil {
	private static TemplateEngine engine = null;

	private TemplateEngineUtil() {}

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
		// WebContextを宣言することで、セッション情報を含むすべての変数を持ち出します
		WebContext context = new WebContext(req, resp, req.getServletContext());
		// Thymeleafエンジンにレンダリング処理を委譲します
		engine.process(template, context, resp.getWriter());
	}
}
