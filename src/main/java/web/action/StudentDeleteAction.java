package web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.Bean;
import biz.service.StudentService;
import web.Action;

public class StudentDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		StudentService.getInstance().delete(Integer.parseInt(req.getParameter("id")));
		List<Bean> list = StudentService.getInstance().findAll();
		req.setAttribute("stulist", list);
		return "student/list";
	}

}
