package biz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import biz.Bean;
import biz.DAO;
import biz.DataNotFoundException;
import biz.Service;
import biz.domain.StudentBean;

public class StudentService extends DAO implements Service {
	// Singletonパターン（GoFデザインパターン）
	// 用途：一つのインスタンスを共有する、複数のインスタンス生成を認めない
	private static StudentService service = null; // 唯一のインスタンス

	private StudentService() {} // privateにすることで、外部からアクセスできなくなる

	public static StudentService getInstance() { // staticメソッドでインスタンス（へのポインタ）を得る
		if (service == null) {
			service = new StudentService();
		}
		return service;
	}

	@Override
	public List<Bean> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Bean findById(int id) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean register(Bean bean) {
		Connection db = this.getConnection();
		StudentBean st = (StudentBean) bean;
		boolean result = false;
		String sql = "INSERT INTO studenttbl(grade, class, serial, name, furi, birth, isMale, address)"
				+" VALUES(?, ?, ?, ?, ?, ?, ?. ?)";
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setInt(1, st.getGrade());
			ps.setString(2, st.getClassName());
			ps.setString(3, st.getSerial());
			ps.setString(4, st.getName());
			ps.setString(5, st.getFuri());
			ps.setString(6, st.getBirth());
			ps.setBoolean(7, st.isMALE());
			ps.setString(8, st.getAddress());
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
