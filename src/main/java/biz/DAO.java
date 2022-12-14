package biz;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// 各実装DAOで共通で使用される機能を汎化します。
public class DAO {
	private static DataSource ds;

	// データベ－スへの接続を行う
	protected final Connection getConnection() {
		Connection con = null;
		if (System.getenv("DATABASE_URL") != null) {
			URI dbUri;
			try {
				dbUri = new URI(System.getenv("DATABASE_URL"));
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
				con = DriverManager.getConnection(dbUrl, username, password);
			} catch (URISyntaxException | SQLException e) {
				e.printStackTrace();
			}
		} else {

			Context context;
			try {
				context = new InitialContext();
				if (ds == null) {
					ds = (DataSource) context.lookup("java:comp/env/jdbc/samplesite");
				}
				con = ds.getConnection();
			} catch (NamingException | SQLException e) {
				e.printStackTrace();
			}

		}
		return con;
	}

	// データベースへの接続を終了する
	protected final void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			con = null;
			e.printStackTrace();
		}
	}

	// 特定のSQLを実行する※ハッキングっぽいので取扱注意
	public final boolean execSQL(String sql) {
		Connection con = this.getConnection();
		boolean result = false;
		if (!sql.startsWith("CREATE") && !sql.startsWith("DROP") && !sql.startsWith("INSERT")
				&& !sql.startsWith("UPDATE") && !sql.startsWith("DELETE")) {
			return result;
		}
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(con);
		}
		return result;
	}
}
