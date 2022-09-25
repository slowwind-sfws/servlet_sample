package biz.domain;

import biz.Bean;
import biz.service.UserService;
import lombok.Data;

@Data
public class UserBean implements Bean {
	private String userId = null;
	private String realName = null;
	private String pass = null;
	private boolean isAuth = false;

	public UserBean() {
	}

	public UserBean(String name, String id, String pass) {
		this.setRealName(name);
		this.setUserId(id);
		this.setPass(pass);
	}

	public boolean login(String id, String pass) {
		UserService service = UserService.getInstance();
		String realName = null;
		if ((realName = service.search(id, pass)) != null) {
			this.setRealName(realName);
			this.setUserId(id);
			this.setPass(pass);
			this.isAuth = true;
		}
		return this.isAuth();
	}

	public void logout() {
		this.isAuth = false;
		this.userId = null;
		this.pass = null;
		this.realName = null;
	}
}
