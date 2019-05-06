package user;
import java.sql.Connection;
import java.sql.DriverManager;
//회원정보를 불러오거나 저장한다.
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {
	//ctrl shift o
	private Connection conn; //데이터베이스에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs; //정보를 저장
	
	public UserDAO() { //my sel에 접속하는 부분
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
	
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID= ?";
		try {
			pstmt = conn.prepareStatement(SQL); //sql 실횅
			pstmt.setString(1,userID); //보안하기 위해 사용
			rs = pstmt.executeQuery();
			if (rs.next()) { //아이디 있는지 확인
				if(rs.getString(1).contentEquals(userPassword)) {
					return 1; //로그인 ㅅ성공
				}
				else {
					return 0; //비밀번호 불일치
				}
			}
			return -1; //아디디가 없음
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES ( ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserID());
			pstmt.setString(2,  user.getUserPassword());
			pstmt.setString(3,  user.getUserName());
			pstmt.setString(4,  user.getUserGender());
			pstmt.setString(5,  user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
