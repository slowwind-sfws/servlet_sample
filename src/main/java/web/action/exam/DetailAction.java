package web.action.exam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.domain.ExamBean;
import biz.service.ExamService;
import web.Action;

public class DetailAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		ExamBean exam = (ExamBean) ExamService.getInstance().findById(id);
		req.setAttribute("exam", exam);
		return "exam/detail";
	}

}
