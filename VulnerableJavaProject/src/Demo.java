import java.sql.*;
import java.io.*;

public class Demo {

	public static void main(String arg[])
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/demo?characterEncoding=utf8","root","AppSec"); 
			
			
			System.out.print("Enter employee ID: ");
			
			BufferedReader r = new BufferedReader (new InputStreamReader(System.in));
			String empID = r.readLine();
			
			if (empID != null)
			if (!empID.contains("emp"))
				System.out.println("Employee ID begins with emp e.g., emp01.");
			
			
			PreparedStatement stmt = conn.prepareStatement("select * from employees where empID=?");
			stmt.setInt(1, Integer.parseInt(empID));
			ResultSet rs = stmt.executeQuery();
			
			//Statement stmt = conn.createStatement();
			//ResultSet rs = stmt.executeQuery("select * from employees where empID='" + empID +"'");

			while(rs.next())
			{
				System.out.println(rs.getString("empID") + " " + rs.getString("empName") + 
						" " + rs.getString("dept"));
			}
			
			stmt.close();
			conn.close();

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
