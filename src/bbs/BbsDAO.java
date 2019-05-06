package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	private Connection conn; //�����ͺ��̽��� �����ϰ� ���ִ� ��ü
	private ResultSet rs; //������ ����
	
	public BbsDAO() { //my sel�� �����ϴ� �κ�
		try{
			String dbURL = "jdbc:mysql://localhost:3306/BBS"; //�����ͺ��̽��� ����
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
				return rs.getInt(1)+1; //���� �Խ��� ���
			}
			return 1; //ù��° �Խù��� ���
		}
		catch (Exception e) {
			e.printStackTrace();

		}
		return -1;
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) { //write �Լ� �Խ��� �ۼ�
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
