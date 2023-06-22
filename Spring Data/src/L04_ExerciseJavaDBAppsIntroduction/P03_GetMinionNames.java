package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03_GetMinionNames {
    private static final String MINIONS_BY_VILLAIN_ID =
            "SELECT `v`.`name`, `m`.`name`, `m`.`age` FROM `villains` AS `v`" +
                    " JOIN `minions_villains` AS `mv` ON `v`.`id` = `mv`.`villain_id`" +
                    " JOIN `minions` AS `m` ON `mv`.`minion_id` = `m`.`id`" +
                    " WHERE `v`.`id` = ?";
    private static final String VILLAIN_NAME_BY_ID = "SELECT `name` FROM `villains` WHERE `id` = ?";

    private static final String VILLAIN_NAME_FORMAT = "Villain: %s%n";

    private static final String MINION_NAME_FORMAT = "%d. %s %d%n";

    private static final String NO_VILLAIN_FOUND_FORMAT = "No villain with ID%d exists in the database.%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = P00_UtilConnection.getSqlConnection();

        int input_id = Integer.parseInt(scanner.nextLine());

        PreparedStatement preparedStatementVillain = connection.prepareStatement(VILLAIN_NAME_BY_ID);
        preparedStatementVillain.setInt(1, input_id);

        ResultSet rsVillain = preparedStatementVillain.executeQuery();

        if (!rsVillain.next()) {
            System.out.printf(NO_VILLAIN_FOUND_FORMAT, input_id);
            connection.close();
            return;
        } else {
            String villain_name = rsVillain.getString("name");
            System.out.printf(VILLAIN_NAME_FORMAT, villain_name);
        }

        PreparedStatement preparedStatementMinion = connection.prepareStatement(MINIONS_BY_VILLAIN_ID);
        preparedStatementMinion.setInt(1, input_id);
        ResultSet rsMinions = preparedStatementMinion.executeQuery();

        int printCounter = 1;
        while (rsMinions.next()) {

            String currentName = rsMinions.getString("m.name");
            int currentAge = rsMinions.getInt("age");

            System.out.printf(MINION_NAME_FORMAT, printCounter, currentName, currentAge);
            printCounter++;
        }
        connection.close();
    }
}
