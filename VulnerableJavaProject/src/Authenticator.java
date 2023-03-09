// Description of error codes. 
// 1 - authentication successful
// 2 - account inactive 
// 3 - failed login attempts reached max (5)
// 4 - userID or password incorrect
// 5 - password expired, grace period valid
// 6 - password expired, grace period ended
// 7 - password or userID missing/ blank

import java.sql.*;
 
public class Authenticator 
{
	public int login(String userID, String password)
	{
		//PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?characterEncoding=utf8","root","AppSec"); 

			if (userID.equals("") || password.equals(""))
				return 7;
			
			/*
			 * The following lines are secure version of the code. It does not have SQL injection
			 * Parameterized queries are solution to SQL injection
			 */
			
			//stmt = conn.prepareStatement("select * from users where userID=? and password=?");
			//stmt.setString(1, userID);
			//stmt.setString(2, password);

			//rs = stmt.executeQuery();

			
			//The following two lines are vulnerable to SQL injection attack
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from users where userID='" + userID +"' and password='"+ password + "'");
			
			
			
			if(rs.next())
			{
				String accountStatus = rs.getString("accountStatus");
				int failedAttempts = rs.getInt("failedAttempts");
				int remainingPasswordLife = rs.getInt("remainingPasswordLife");
				int remainingGracePeriod = rs.getInt("remainingGracePeriod");
				
				if (accountStatus.equals("active") && failedAttempts<5 && remainingPasswordLife>0)
					return 1;
				
				else if (accountStatus.equals("inactive"))
					return 2;
				
				else if (failedAttempts>=5)
					return 3;
				
				else if (remainingPasswordLife == 0 && remainingGracePeriod>0)
					return 5;
	
				else if (remainingPasswordLife == 0 && remainingGracePeriod == 0)
					return 6;
			}
			
			else
			{
				return 4; 
			}
			
			stmt.close();
			conn.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return 0;
	}
}
