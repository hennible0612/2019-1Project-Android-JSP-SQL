package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	private Connection conn; //데이터베이스에 접근하게 해주는 객체
	private ResultSet rs; //정보를 저장
	
	public BbsDAO() { //my sel에 접속하는 부분
		try{
			String dbURL = "jdbc:mysql://localhost:3306/BBS"; //데이터베이스에 접속
			String dbID = "root";
			String dbPassword = "a12345";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return "";
	}
	
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; //다음 게시판 목록
			}
			return 1; //첫번째 게시물인 경우
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return -1;
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) { //write 함수 게시판 작성
		String SQL = "INSERT INTO BBS VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
