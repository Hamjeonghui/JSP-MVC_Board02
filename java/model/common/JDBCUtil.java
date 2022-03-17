package model.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JDBCUtil {
	static final String driverName="com.mysql.cj.jdbc.Driver";
	static final String url="jdbc:mysql://localhost:3306/hamdb"; //hamdb부분은 본인이 사용중인 db이름 입력
	static final String user="root";
	static final String pw="1234";
	public static Connection connect() {
		Connection conn=null;
		try {
			Class.forName(driverName);
			conn=DriverManager.getConnection(url,user,pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void disconnect(PreparedStatement pstmt,Connection conn) {
		try {
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
