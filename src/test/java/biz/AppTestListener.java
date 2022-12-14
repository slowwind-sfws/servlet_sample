package biz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import biz.domain.ExamBean;
import biz.domain.StudentBean;
import biz.domain.UserBean;
import biz.service.ExamService;
import biz.service.StudentService;
import biz.service.UserService;

/**
 * Application Lifecycle Listener implementation class AppTestListener
 *
 * 研修ではListenerについて触れていませんでした。
 * 今回Listenerクラスを使用す目的は次の通りです。
 * ①開発テスト段階でのテストDBを毎回初期化したい
 * ②開発段階でのDB起動を自動化し、「確実に」メモリ常駐させたい
 * ③開発からリリースに移行する段階で、テストDBを「確実に」切り離したい
 * ServletContextListnerから派生したListenerクラスは、@WebListenerアノテーションを
 * 付加することでシステム起動時（EclipseではTomcat起動時）直後に生成され、
 * システム終了時（同Tomcat終了時）に破棄されます。
 * 最終的にデプロイメント候補から「src/test/java」を削除することで、リリース時には
 * 簡単に切り離すことができます※context.xmlは書き換える必要があります
 */
@WebListener
public class AppTestListener implements ServletContextListener {
	// システムで使用するService（DAO）を登録
	UserService u_service = null;
	StudentService s_service = null;
	ExamService e_service = null;

	/**
	 * Default constructor.
	 */
	public AppTestListener() {
		// 起動時にシングルトン生成することで、各インスタンスが唯ひとつずつ常駐します
		u_service = UserService.getInstance();
		s_service = StudentService.getInstance();
		e_service = ExamService.getInstance();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// 破棄時に各サービスの参照を切るため、確実にメモリから同時解放できます
		u_service.execSQL("DROP TABLE IF EXISTS usertbl");
		s_service.execSQL("DROP TABLE IF EXISTS studenttbl");
		e_service.execSQL("DROP TABLE IF EXISTS examtbl");
		u_service = null;
		s_service = null;
		e_service = null;
		System.out.println("SystemStop..");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// Listener生成時に、テーブルを初期化します
		System.out.println("SystemStart..");
		u_service.execSQL("DROP TABLE IF EXISTS usertbl");
		s_service.execSQL("DROP TABLE IF EXISTS studenttbl");
		e_service.execSQL("DROP TABLE IF EXISTS examtbl");

		if (System.getenv("DATABASE_URL") != null) { // Heroku等にリリースすることを想定
			u_service.execSQL("CREATE TABLE IF NOT EXISTS usertbl"
					+ " (id SERIAL, realName VARCHAR(64), userID VARCHAR(64), passwd VARCHAR(64), PRIMARY KEY (id))");
			s_service.execSQL("CREATE TABLE IF NOT EXISTS studenttbl"
					+ " (id SERIAL, grade INTEGER, class VARCHAR(16), serial VARCHAR(16), name VARCHAR(64), furi VARCHAR(64),"
					+ " birth VARCHAR(16), isMale boolean, address VARCHAR(64), PRIMARY KEY (id))");
			e_service.execSQL("CREATE TABLE IF NOT EXISTS examtbl"
					+ " (id SERIAL, studentId INTEGER, subjectName VARCHAR(64), point INTEGER, PRIMARY KEY (id))");
		} else { // 開発テスト時はこちら
			if (u_service.execSQL("CREATE TABLE IF NOT EXISTS usertbl"
					+ " (id IDENTITY, realName VARCHAR(64), userID VARCHAR(64), passwd VARCHAR(64))")) {

				u_service.register(new UserBean("管理者", "admin", "adminpass"));
				u_service.register(new UserBean("hogehoge", "hoge", "hogepass"));
				u_service.register(new UserBean("piyopiyo", "piyo", "piyopass"));
				System.out.println("TestUserDB is READY.");
			} else {
				System.out.println("TestUserDB is NOT READY.");
			}
			if (s_service.execSQL("CREATE TABLE IF NOT EXISTS studenttbl"
					+ " (id IDENTITY, grade INTEGER, class VARCHAR(16), serial VARCHAR(16), name VARCHAR(64), furi VARCHAR(64),"
					+ " birth VARCHAR(16), isMale boolean, address VARCHAR(64))")) {
				s_service.register(new StudentBean("21-70001", "hoge", "hoge", "20001010", true, "hoge"));
				s_service.register(new StudentBean("21-70002", "piyo", "piyo", "20001224", false, "piyo"));
				if (e_service.execSQL("CREATE TABLE IF NOT EXISTS examtbl"
						+ "(id IDENTITY, studentId INTEGER, subjectName VARCHAR(64), point INTEGER)")) {
					try {
						e_service.register(new ExamBean((StudentBean)s_service.findById(1),"数学", 80));
						e_service.register(new ExamBean((StudentBean)s_service.findById(1),"国語", 70));
						e_service.register(new ExamBean((StudentBean)s_service.findById(2),"数学", 60));
						e_service.register(new ExamBean((StudentBean)s_service.findById(2),"国語", 90));
					} catch (DataNotFoundException e) {
						e.printStackTrace();
					}
				}
				System.out.println("TestAppDB is READY.");
			} else {
				System.out.println("TestAppDB is NOT READY.");
			}
		}
	}
}
