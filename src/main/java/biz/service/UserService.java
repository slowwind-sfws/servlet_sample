package biz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import biz.Bean;
import biz.DAO;
import biz.DataNotFoundException;
import biz.Service;
import biz.domain.UserBean;

//StudentServiceを参照
public class UserService extends DAO implements Service {
	// Singletonパターン（GoFデザインパターン）
	// 用途：一つのインスタンスを共有する、複数のインスタンス生成を認めない
	private static UserService service = null; // 唯一のインスタンス

	private UserService() {} // privateにすることで、外部からアクセスできなくなる

	public static UserService getInstance() { // staticメソッドでインスタンス（へのポインタ）を得る
		if (service == null) {
			service = new UserService();
		}
		return service;
	}

	@Override
	public List<Bean> findAll() {
		return null;
	}

	@Override
	public Bean findById(int id) throws DataNotFoundException {
		return null;
	}

	@Override
	public boolean register(Bean bean) {
		Connection db = this.getConnection();
		UserBean user = (UserBean) bean;
		boolean result = false;
		try (PreparedStatement ps = db.prepareStatement("INSERT INTO usertbl(realName, userID, passwd) VALUES(?, ?, ?)")) {
			ps.setString(1, user.getRealName());
			ps.setString(2, user.getUserId());
			ps.setString(3, user.getPass());
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
	}

	@Override
	public void delete(int id) throws DataNotFoundException {
	}

	public String search(String userId, String pass) {
		Connection db = this.getConnection();
		String result = null;
		try (PreparedStatement ps = db.prepareStatement("SELECT * FROM usertbl WHERE userID=? AND passwd=?")) {
			ps.setString(1, userId);
			ps.setString(2, pass);
			ResultSet rst = ps.executeQuery();
			if (rst.next()) {
				result = rst.getString("realName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}

		return result;
	}

}
