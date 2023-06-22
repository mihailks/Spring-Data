package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P02GetVillainsNames {

    private static final String GET_VILLAINS_NAMES =
            "SELECT `v`.`name`, COUNT(DISTINCT `mv`.`minion_id`) `count_of_minions`" +
                    " FROM `villains` AS `v`" +
                    " JOIN `minions_villains` AS `mv` ON `v`.`id` = `mv`.`villain_id`" +
                    " GROUP BY `v`.`id`" +
                    " HAVING `count_of_minions` > ?" +
                    " ORDER BY `count_of_minions` DESC";

    private static final int NUMBER_OF_MINIONS = 15;

    public static void main(String[] args) throws SQLException {

        Connection connection = P00_UtilConnection.getSqlConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(GET_VILLAINS_NAMES);
        preparedStatement.setInt(1, NUMBER_OF_MINIONS);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("name") + " " + rs.getString("count_of_minions"));
        }
    }
}
