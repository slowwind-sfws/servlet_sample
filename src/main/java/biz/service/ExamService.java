package biz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import biz.Bean;
import biz.DAO;
import biz.DataNotFoundException;
import biz.Service;
import biz.domain.ExamBean;

public class ExamService extends DAO implements Service {
	// Singletonパターン（GoFデザインパターン）
	// 用途：一つのインスタンスを共有する、複数のインスタンス生成を認めない
	private static ExamService service = null; // 唯一のインスタンス

	private ExamService() {} // privateにすることで、外部からアクセスできなくなる

	public static ExamService getInstance() { // staticメソッドでインスタンス（へのポインタ）を得る
		if (service == null) {
			service = new ExamService();
		}
		return service;
	}

	@Override
	public List<Bean> findAll() {
		List<Bean> list = new ArrayList<Bean>();
		Connection db = this.getConnection();
		try (PreparedStatement ps = db.prepareStatement("SELECT * FROM examtbl")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new ExamBean(rs.getInt("studentId"), rs.getString("subjectName"), rs.getInt("point")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
//		list.add(new ExamBean(0, "算数", 80));
//		list.add(new ExamBean(0, "理科", 70));
//		list.add(new ExamBean(1, "算数", 60));
//		list.add(new ExamBean(1, "理科", 90));

		return list;
	}

	@Override
	public Bean findById(int id) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean register(Bean bean) {
		Connection db = this.getConnection();
		ExamBean exam = (ExamBean) bean;
		boolean result = false;
		try (PreparedStatement ps = db.prepareStatement("INSERT INTO examtbl(studentId, subjectName, point) VALUES(?, ?, ?)")) {
			ps.setInt(1, exam.getStudentId());
			ps.setString(2, exam.getSubjectName());
			ps.setFloat(3, exam.getPoint());
			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
		return result;
	}

	@Override
	public void update(Bean bean) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(int id) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
