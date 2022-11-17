package biz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.domain.ExamBean;
import biz.domain.StudentBean;
import biz.service.ExamService;
import biz.service.StudentService;

/**
 * Application Lifecycle Listener implementation class ExamTestListener
 *
 */
@WebListener
public class AppTestListener implements ServletContextListener {
	StudentService s_service = null;
	ExamService e_service = null;

	/**
	 * Default constructor.
	 */
	public AppTestListener() {
		s_service = StudentService.getInstance();
		e_service = ExamService.getInstance();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		s_service.execSQL("DROP TABLE IF EXISTS studenttbl");
		e_service.execSQL("DROP TABLE IF EXISTS examtbl");
		s_service = null;
		e_service = null;
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		s_service.execSQL("DROP TABLE IF EXISTS studenttbl");
		e_service.execSQL("DROP TABLE IF EXISTS examtbl");
		if (System.getenv("DATABASE_URL") != null) {
			s_service.execSQL("CREATE TABLE IF NOT EXISTS studenttbl"
					+ " (id SERIAL, grade INTEGER, class VARCHAR(16), serial VARCHAR(16), name VARCHAR(64), furi VARCHAR(64),"
					+ " birth VARCHAR(16), isMale boolean, address VARCHAR(64), PRIMARY KEY (id))");
			e_service.execSQL("CREATE TABLE IF NOT EXISTS examtbl"
					+ " (id SERIAL, studentId INTEGER, subjectName VARCHAR(64), point INTEGER, PRIMARY KEY (id))");
		} else {
			if (s_service.execSQL("CREATE TABLE IF NOT EXISTS studenttbl"
					+ " (id IDENTITY, grade INTEGER, class VARCHAR(16), serial VARCHAR(16), name VARCHAR(64), furi VARCHAR(64),"
					+ " birth VARCHAR(16), isMale boolean, address VARCHAR(64))")) {
				s_service.register(new StudentBean("21-70001", "hoge", "hoge", "20001010", true, "hoge"));
				s_service.register(new StudentBean("21-70002", "piyo", "piyo", "20001224", false, "piyo"));
				if (e_service.execSQL("CREATE TABLE IF NOT EXISTS examtbl"
						+ "(id IDENTITY, studentId INTEGER, subjectName VARCHAR(64), point INTEGER)")) {
					//System.out.println("ここまで");
					try {
						e_service.register(new ExamBean((StudentBean)s_service.findById(1),"数学", 80));
						e_service.register(new ExamBean((StudentBean)s_service.findById(1),"国語", 70));
						e_service.register(new ExamBean((StudentBean)s_service.findById(2),"数学", 60));
						e_service.register(new ExamBean((StudentBean)s_service.findById(2),"国語", 90));
					} catch (DataNotFoundException e) {
						e.printStackTrace();
					}
					//System.out.println("ここまで");
				}
				//System.out.println("TestUserDB is READY.");
			} else {
				//System.out.println("TestUserDB is NOT READY.");
			}
		}
	}
}
