package web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.Bean;
import biz.domain.StudentBean;
import biz.service.StudentService;
import web.Action;

public class StudentCreateAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("POST")) {
			StudentService.getInstance().register(
				new StudentBean(req.getParameter("serial"), req.getParameter("name"),
					req.getParameter("furi"), req.getParameter("birth"), true, req.getParameter("addr")
				)
			);
			List<Bean> list = StudentService.getInstance().findAll();
			req.setAttribute("stulist", list);
			return "student/list";
		} else {
			return "student/create";
		}
	}

}
