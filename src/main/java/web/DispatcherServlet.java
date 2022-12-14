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
 *
 * テキストでも紹介されていた、「FrontController」パターンを利用します。
 * 特にチームで開発する際、機能実装チームがどのようなクラス命名するかは事後的にしか
 * わからないため、基盤開発チームは「あらかじめ想定して」規則を作っておきます。
 * 詳細は「設定より規約」で検索してください。
 * 名前のわからないクラスを操作したりする手法を「リフレクションプログラミング」といいます。
 */
@WebServlet(urlPatterns = { "*.action" })
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try {
			// 「http://xxx/yyy/zzz」のRESTUFULな構造にしたくて、少しアレンジを入れています。
			// ※不十分なことはわかっています！！(笑)
			String path = req.getServletPath().substring(1);
			String[] elements = path.split("/");
			if (elements.length == 1) {
				path = path.substring(0, 1).toUpperCase() + path.substring(1);
			} else {
				path  = elements[0].toLowerCase() + "/";
				path += elements[1].substring(0, 1).toUpperCase() + elements[1].substring(1);
			}
			String name = path.replace(".a", "A").replace("/", ".");
			Action action = (Action) Class.forName("web.action." + name).getDeclaredConstructor().newInstance();
			String url = action.execute(req, resp);
			TemplateEngineUtil.render(url, req, resp);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
