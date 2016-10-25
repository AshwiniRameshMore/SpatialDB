import java.sql.*;

public class ConnectDatabase {
	private static Connection con = null;
	
	public static Connection getConnection(){
		try{
        	if(con == null)
        	{
        		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","nisarg");
        	}
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
	}
}