package web.action.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.Bean;
import biz.service.ExamService;
import web.Action;

public class ListAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Bean> list = ExamService.getInstance().findAll();
		req.setAttribute("examlist", list);
		return "exam/list";
	}

}
