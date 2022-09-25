package biz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.domain.UserBean;
import biz.service.UserService;

/**
 * Application Lifecycle Listener implementation class UserTestListener
 *
 */
@WebListener
public class UserTestListener implements ServletContextListener {
	UserService service = null;

	/**
	 * Default constructor.
	 */
	public UserTestListener() {
		service = UserService.getInstance();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		service.execSQL("DROP TABLE IF EXISTS usertbl");
		service = null;
		System.out.println("SystemStop..");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		service.execSQL("DROP TABLE IF EXISTS usertbl");
		if (System.getenv("DATABASE_URL") != null) {
			service.execSQL("CREATE TABLE IF NOT EXISTS usertbl"
					+ " (id SERIAL, realName VARCHAR(64), userID VARCHAR(64), passwd VARCHAR(64), PRIMARY KEY (id))");
		} else {
			if (service.execSQL("CREATE TABLE IF NOT EXISTS usertbl"
					+ " (id IDENTITY, realName VARCHAR(64), userID VARCHAR(64), passwd VARCHAR(64))")) {
				System.out.println("TestUserDB is READY.");
			} else {
				System.out.println("TestUserDB is NOT READY.");
			}
		}
		service.register(new UserBean("管理者", "admin", "adminpass"));
		service.register(new UserBean("hogehoge", "hoge", "hogepass"));
		service.register(new UserBean("piyopiyo", "piyo", "piyopass"));

		System.out.println("SystemStart..");
	}

}
