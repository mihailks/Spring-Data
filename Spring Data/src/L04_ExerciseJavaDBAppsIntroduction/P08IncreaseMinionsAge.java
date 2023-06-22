package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P08IncreaseMinionsAge {
    public static final String UPDATE_MINION_NAME_AGE_BY_ID = "UPDATE `minions` SET `age` = `age` + 1, `name` = LOWER(`name`) WHERE `id` = ?;";
    public static final String SELECT_ALL_MINIONS = "SELECT `name`, `age` FROM `minions`";
    public static final String PRINT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = P00_UtilConnection.getSqlConnection();

        Scanner scanner = new Scanner(System.in);
        String[] minionId = scanner.nextLine().split(" ");

        for (int i = 0; i < minionId.length; i++) {
            PreparedStatement selectMinionsById = connection.prepareStatement(UPDATE_MINION_NAME_AGE_BY_ID);
            selectMinionsById.setInt(1, Integer.parseInt(minionId[i]));
            selectMinionsById.executeUpdate();
        }

        PreparedStatement selectAllMinions = connection.prepareStatement(SELECT_ALL_MINIONS);
        ResultSet rsAllMinions = selectAllMinions.executeQuery();

        while (rsAllMinions.next()) {
            String currentMinionName = rsAllMinions.getString("name");
            int currentMinionAge = rsAllMinions.getInt("age");
            System.out.printf(PRINT_FORMAT, currentMinionName, currentMinionAge);
        }
        connection.close();
    }
}
