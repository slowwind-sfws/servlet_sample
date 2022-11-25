package web.action.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.Bean;
import biz.domain.ExamBean;
import biz.domain.StudentBean;
import biz.service.ExamService;
import biz.service.StudentService;
import web.Action;

public class UpdateAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("POST")) {
			StudentBean bean =
				(StudentBean) StudentService.getInstance().findById(Integer.parseInt(req.getParameter("studentId")));
			ExamService.getInstance().update(
				new ExamBean(Integer.parseInt(req.getParameter("id")), bean,
					req.getParameter("subjectName"), Float.parseFloat(req.getParameter("point"))
				)
			);
			List<Bean> list = ExamService.getInstance().findAll();
			req.setAttribute("examlist", list);
			return "exam/list";
		} else {
			int id = Integer.parseInt(req.getParameter("id"));
			ExamBean exam = (ExamBean) ExamService.getInstance().findById(id);
			req.setAttribute("exam", exam);
			return "exam/update";
		}
	}

}
