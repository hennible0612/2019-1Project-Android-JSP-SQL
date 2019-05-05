package user;
import java.sql.Connection;
import java.sql.DriverManager;
//ȸ�������� �ҷ����ų� �����Ѵ�.
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {
	//ctrl shift o
	private Connection conn; //�����ͺ��̽��� �����ϰ� ���ִ� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs; //������ ����
	
	public UserDAO() { //my sel�� �����ϴ� �κ�
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
	
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID= ?";
		try {
			pstmt = conn.prepareStatement(SQL); //sql ��ȷ
			pstmt.setString(1,userID); //�����ϱ� ���� ���
			rs = pstmt.executeQuery();
			if (rs.next()) { //���̵� �ִ��� Ȯ��
				if(rs.getString(1).contentEquals(userPassword)) {
					return 1; //�α��� ������
				}
				else {
					return 0; //��й�ȣ ����ġ
				}
			}
			return -1; //�Ƶ�� ����
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽� ����
	}
}
