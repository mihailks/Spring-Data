package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

enum P00_UtilConnection {
    ;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static Connection getSqlConnection() throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", USERNAME);
        props.setProperty("password", PASSWORD);

        return DriverManager
                .getConnection(CONNECTION_STRING + DB_NAME, props);
    }

}
