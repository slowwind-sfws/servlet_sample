package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.domain.UserBean;
import web.Action;

public class UserAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (req.getParameter("login") != null) { // ログインの場合
			if (!user.isAuth()) { // !!重要
				String id = req.getParameter("userId").toString();
				String pass = req.getParameter("userPass").toString();
				if (user.login(id, pass)) { // ログインに成功した場合
					session.setAttribute("user", user);
				}
			}
			resp.sendRedirect(req.getHeader("Referer"));
		} else if (req.getParameter("logout") != null) { // ログアウトの場合
			user.logout();
			resp.sendRedirect(req.getHeader("Referer"));
		} /*else if (req.getParameter("register") != null) { // 新規登録画面からの遷移
			req.getRequestDispatcher("WEB-INF/jsp/User/register.jsp").forward(req, resp);
		} else if (req.getParameter("confirm") != null) { // 確認画面から遷移
			req.getRequestDispatcher("WEB-INF/jsp/home.jsp").forward(req, resp);
		}*/
		return "index";
	}

}
