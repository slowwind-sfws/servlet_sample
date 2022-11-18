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
		List<Bean> list = new ArrayList<Bean>();
		Connection db = this.getConnection();
		try (PreparedStatement ps = db.prepareStatement("SELECT * FROM studenttbl")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new StudentBean(
					rs.getInt("id"), rs.getString("serial"), rs.getString("name"), rs.getString("furi"),
					rs.getString("birth"), rs.getBoolean("isMale"), rs.getString("address")
				));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
		return list;
	}

	@Override
	public Bean findById(int id) throws DataNotFoundException {
		Connection db = this.getConnection();
		String sql = "SELECT * FROM studenttbl WHERE id=?";
		ResultSet rs = null;
		StudentBean bean = null;
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new StudentBean(
						rs.getInt("id"), rs.getString("serial"), rs.getString("name"), rs.getString("furi"),
						rs.getString("birth"), rs.getBoolean("isMale"), rs.getString("address"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
		return bean;
	}

	@Override
	public boolean register(Bean bean) {
		Connection db = this.getConnection();
		StudentBean st = (StudentBean) bean;
		boolean result = false;
		String sql = "INSERT INTO studenttbl(grade, class, serial, name, furi, birth, isMale, address)"
				+" VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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
		Connection db = this.getConnection();
		String sql = "UPDATE studenttbl SET serial=?, name=?, furi=?, birth=?, address=? WHERE id=?";
		try (PreparedStatement ps = db.prepareStatement(sql)) {
			ps.setString(1, ((StudentBean)bean).getSerial());
			ps.setString(2, ((StudentBean)bean).getName());
			ps.setString(3, ((StudentBean)bean).getFuri());
			ps.setString(4, ((StudentBean)bean).getBirth());
			ps.setString(5, ((StudentBean)bean).getAddress());
			ps.setInt(6, ((StudentBean)bean).getId());
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
		String sql = "DELETE FROM studenttbl WHERE id=?";
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
