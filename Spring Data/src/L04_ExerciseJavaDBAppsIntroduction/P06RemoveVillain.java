package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06RemoveVillain {
    private static final String SELECT_VILLAIN_NAME_BY_ID = "SELECT `name` FROM `villains` WHERE `id` = ?;";
    private static final String DELETE_MINIONS_VILLAINS = "DELETE FROM `minions_villains` WHERE `villain_id` = ?;";
    private static final String DELETE_VILLAIN_BY_ID = "DELETE FROM `villains` WHERE `id` = ?;";
    private static final String SELECT_MINIONS_BY_VILLAIN_ID = "SELECT COUNT(`minion_id`) AS `count` FROM `minions_villains` WHERE `villain_id` = ?;";
    private static final String PRINT_NO_FOUND_MESSAGE = "No such villain was found";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = P00_UtilConnection.getSqlConnection();
        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement selectVillain = connection.prepareStatement(SELECT_VILLAIN_NAME_BY_ID);
        selectVillain.setInt(1, villainId);
        ResultSet rsVillainName = selectVillain.executeQuery();

        if (!rsVillainName.next()) {
            System.out.println(PRINT_NO_FOUND_MESSAGE);
            return;
        }

        String villainName = rsVillainName.getString("name");

        connection.setAutoCommit(false);

        PreparedStatement selectMinionsByVillainId = connection.prepareStatement(SELECT_MINIONS_BY_VILLAIN_ID);
        selectMinionsByVillainId.setInt(1, villainId);
        ResultSet rsMinionsByVillainId = selectMinionsByVillainId.executeQuery();
        rsMinionsByVillainId.next();
        int minionsDeleteCounter = rsMinionsByVillainId.getInt("count");
        
        try {
            PreparedStatement deleteMinionsVillains = connection.prepareStatement(DELETE_MINIONS_VILLAINS);
            deleteMinionsVillains.setInt(1, villainId);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement(DELETE_VILLAIN_BY_ID);
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.printf("%s was deleted%n" +
                    "%d minions released", villainName, minionsDeleteCounter);

        } catch (SQLException e) {
            connection.rollback();
        }

        connection.close();
    }
}
