package L03_DBAppsIntroduction;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DataRetrievalApplication {

    private static final String SELECT_NUMBER_OF_GAMES_BY_USER_NAME =
            "select first_name, last_name, COUNT(ug.game_id)" +
                    " from users as u" +
                    " left join users_games as ug on u.id = ug.user_id" +
                    " where user_name = ?" +
                    " group by first_name, last_name";

    private static final String SELECT_USER_COUNT_BY_USERNAME =
            "SELECT COUNT(*) FROM users WHERE user_name = ?";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();

        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        String input = scanner.nextLine();

        boolean usernameExists = getUsernameExists(connection, input);


        PreparedStatement statement = connection.prepareStatement(SELECT_NUMBER_OF_GAMES_BY_USER_NAME);
        statement.setString(1, input);

        ResultSet result = statement.executeQuery();

        if (usernameExists) {
            result.next();

            System.out.println("User: " + input);
            System.out.printf("%s %s has played %d games",
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getInt(3));
        } else {
            System.out.println("No such user exists");
        }
        connection.close();
    }

    private static boolean getUsernameExists(Connection connection, String input) throws SQLException {

        PreparedStatement existsStatement = connection.prepareStatement(SELECT_USER_COUNT_BY_USERNAME);
        existsStatement.setString(1, input);

        ResultSet existsResult = existsStatement.executeQuery();
        existsResult.next();

        int rowCount = existsResult.getInt(1);

        return rowCount > 0;
    }
}
