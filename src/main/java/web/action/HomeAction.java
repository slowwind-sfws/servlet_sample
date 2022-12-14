package web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.Bean;
import biz.service.ExamService;
import web.Action;

public class HomeAction implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Bean> list = ExamService.getInstance().findAll();
		req.setAttribute("examlist", list);
		return "index";
	}

}
