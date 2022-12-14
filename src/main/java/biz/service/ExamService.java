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
import biz.domain.StudentBean;

// StudentServiceを参照
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
				// 学生beanに従属しております汗
				int stuId = rs.getInt("studentId");
				StudentBean bean = ((StudentBean)StudentService.getInstance().findById(stuId));
				list.add(new ExamBean(rs.getInt("id"),bean, rs.getString("subjectName"), rs.getInt("point")));
			}
			rs.close();
		} catch (SQLException | DataNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
		return list;
	}

	@Override
	public Bean findById(int id) throws DataNotFoundException {
		Connection db = this.getConnection();
		String sql = "SELECT * FROM examtbl WHERE id=?";
		ResultSet rs = null;
		ExamBean exam = null;
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// 学生beanに従属しております汗
				int stuId = rs.getInt("studentId");
				StudentBean bean = ((StudentBean)StudentService.getInstance().findById(stuId));
				exam = new ExamBean(
						rs.getInt("id"), bean, rs.getString("subjectName"), rs.getInt("point"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
		return exam;
	}

	@Override
	public boolean register(Bean bean) {
		Connection db = this.getConnection();
		ExamBean exam = (ExamBean) bean;
		boolean result = false;
		try (PreparedStatement ps = db.prepareStatement("INSERT INTO examtbl(studentId, subjectName, point) VALUES(?, ?, ?)")) {
			ps.setInt(1, exam.getStudent().getId());
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
		Connection db = this.getConnection();
		String sql = "UPDATE examtbl SET studentId=?, subjectName=?, point=? WHERE id=?";
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setInt(1, ((ExamBean)bean).getStudent().getId());
			ps.setString(2, ((ExamBean)bean).getSubjectName());
			ps.setFloat(3, ((ExamBean)bean).getPoint());
			ps.setInt(4, ((ExamBean)bean).getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
	}

	@Override
	public void delete(int id) throws DataNotFoundException {
		Connection db = this.getConnection();
		String sql = "DELETE FROM examtbl WHERE id=?";
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
	}

}
