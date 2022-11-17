package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.domain.StudentBean;
import biz.service.StudentService;
import web.Action;

public class StudentDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		StudentBean student = (StudentBean) StudentService.getInstance().findById(id);
		req.setAttribute("student", student);
		return "student/detail";
	}

}
