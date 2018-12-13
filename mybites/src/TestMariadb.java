import java.sql.*;

/**
 * Created by jlgaoyuan on 2018/12/13.
 *
 */
public class TestMariadb {
    public static void main( String[] args ) throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://192.168.0.160/", "root", "Linuxgy780504")) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                //execute query
                stmt.execute("use mysql");
                try (ResultSet rs = stmt.executeQuery("SELECT  * from user")) {
                    //position result to first
                    rs.first();
                    System.out.println(rs.getString(2)); //result is "Hello World!"
                }
            }
        }
    }
}
