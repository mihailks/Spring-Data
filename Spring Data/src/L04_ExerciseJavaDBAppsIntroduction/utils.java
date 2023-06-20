package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class utils {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static Connection getSqlConnection() throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", USERNAME);
        props.setProperty("password", PASSWORD);

        return DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", props);
    }

}
