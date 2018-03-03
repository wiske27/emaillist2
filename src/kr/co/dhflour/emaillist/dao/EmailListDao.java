package kr.co.dhflour.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.co.dhflour.emaillist.vo.EmailListVo;

public class EmailListDao {
	
	public boolean insert(EmailListVo vo) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. JDBC 드라이브 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			//System.out.println("연결 성공!");
			
			//3. Statement 준비
			String sql = 
					"INSERT INTO EMAILLIST" +
			        "		VALUES (SEQ_EMILLIST.NEXTVAL, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			//4. binding
			pstmt.setString(1, vo.getLastName());;
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());
			
			//5. sql문 실행
			int count = pstmt.executeUpdate();
			
			
			//6. 성공 유무
			if (count == 1) {
				result = true;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이브 로딩 실패!");
			
		} catch (SQLException e) {
			System.out.println("연결: " + e);
			
		} finally {
			//7. 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	
	public ArrayList<EmailListVo> fetchList() {
		ArrayList<EmailListVo> list = new ArrayList<EmailListVo>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC 드라이브 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			//System.out.println("연결 성공!");
			
			//3. sql 
			String sql = "SELECT NO, LAST_NAME, FIRST_NAME, EMAIL FROM EMAILLIST order by NO";
			
			//4. statement 준비
			stmt = conn.createStatement();
			
			//5. binding
			//stmt.setLong(1, n);
			
			rs = stmt.executeQuery(sql);
			
			while ( rs.next()) { 
				long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firsName = rs.getString(3);
				String email = rs.getString(4);
				
				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setLastName(lastName);
				vo.setFirstName(firsName);
				vo.setEmail(email);
				
				list.add(vo);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이브 로딩 실패!");
			
		} catch (SQLException e) {
			System.out.println("연결: " + e);
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	public EmailListVo fetch(long n) {
		
		EmailListVo vo = null; 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC 드라이브 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			
			//2. Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결 성공!");
			
			//3. sql 
			String sql = "SELECT NO, LAST_NAME, FIRST_NAME, EMAIL FROM EMAILLIST WHERE NO = ?";
			
			//4. statement 준비
			pstmt = conn.prepareStatement(sql);
			
			//5. binding
			pstmt.setLong(1, n);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next()) { 
				long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firsName = rs.getString(3);
				String email = rs.getString(4);
				
				vo = new EmailListVo();
				vo.setNo(no);
				vo.setLastName(lastName);
				vo.setFirstName(firsName);
				vo.setEmail(email);
				
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이브 로딩 실패!");
			
		} catch (SQLException e) {
			System.out.println("연결: " + e);
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	
}
