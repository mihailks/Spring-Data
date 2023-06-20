package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P02GetVillainsNames {

    private static final String GET_VILLAINS_NAMES =
            "SELECT `v`.`name`, COUNT(DISTINCT `mv`.`minion_id`)" +
                    " FROM `villains` AS `v`" +
                    " JOIN `minions_villains` AS `mv` ON `v`.`id` = `mv`.`villain_id`" +
                    " GROUP BY `villain_id`" +
                    " HAVING COUNT(`mv`.`minion_id`) > ?" +
                    " ORDER BY COUNT(`mv`.`minion_id`) DESC";

    private static final int NUMBER_OF_MINIONS = 15;

    public static void main(String[] args) throws SQLException {

        Connection connection = utils.getSqlConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(GET_VILLAINS_NAMES);
        preparedStatement.setInt(1, NUMBER_OF_MINIONS);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("name"));
        }



    }
}
