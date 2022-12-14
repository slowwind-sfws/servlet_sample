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

public class CreateAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("POST")) {
			StudentBean bean =
					(StudentBean) StudentService.getInstance().findById(Integer.parseInt(req.getParameter("studentId")));
			ExamService.getInstance().register(
				new ExamBean(bean, req.getParameter("subjectName"),	Float.parseFloat(req.getParameter("point")))
			);
			List<Bean> list = ExamService.getInstance().findAll();
			req.setAttribute("examlist", list);
			return "exam/list";
		} else {
			int id = Integer.parseInt(req.getParameter("id"));
			StudentBean bean = (StudentBean) StudentService.getInstance().findById(id);
			ExamBean exam = new ExamBean(bean, null, 0);
			req.setAttribute("exam", exam);
			return "exam/create";
		}
	}

}
