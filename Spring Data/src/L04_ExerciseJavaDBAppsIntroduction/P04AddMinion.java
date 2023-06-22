package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P04AddMinion {
    private static final String PRINT_ADD_VILLAIN_TO_DB = "Villain %s was added to the database.%n";
    private static final String PRINT_ADD_TOWN_TO_DB = "Town %s was added to the database.%n";
    private static final String PRINT_ADD_MINION_TO_DB = "Successfully added %s to be minion of %s.%n";

    private static final String GET_VILLAIN_ID_BY_NAME = "SELECT `id` FROM `villains` WHERE `name` = ? ";
    private static final String GET_TOWN_ID_BY_NAME = "SELECT `id` FROM `towns` WHERE `name` = ? ";
    private static final String GET_MINION_ID_BY_NAME = "SELECT `id` FROM `minions` WHERE `name` = ? ";

    private static final String INSERT_VILLAIN_IN_DB = "INSERT INTO `villains`(`name`, `evilness_factor`) VALUE (?, ?);";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String INSERT_TOWN_IN_DB = "INSERT INTO `towns`(`name`) VALUES (?);";
    private static final String INSERT_MINION_IN_DB = "INSERT INTO `minions` (`name`, `age`, `town_id`) VALUES (?, ?, ?)";
    private static final String INSERT_IN_MINION_VILLAIN = "INSERT INTO `minions_villains` VALUES (?, ?);";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = P00_UtilConnection.getSqlConnection();

        String[] minionInput = scanner.nextLine().split(" ");
        String villainName = scanner.nextLine().split(" ")[1];

        String minionName = minionInput[1];
        int minionAge = Integer.parseInt(minionInput[2]);
        String minionTown = minionInput[3];

        //check if town is in
        PreparedStatement selectTown = connection.prepareStatement(GET_TOWN_ID_BY_NAME);
        selectTown.setString(1, minionTown);
        ResultSet rsTown = selectTown.executeQuery();

        //add town if is not
        int townId = insertTown(connection, minionTown, selectTown, rsTown);

        // check if villain is in
        PreparedStatement selectVillain = connection.prepareStatement(GET_VILLAIN_ID_BY_NAME);
        selectVillain.setString(1, villainName);
        ResultSet rsVillain = selectVillain.executeQuery();

        //add villain if is not
        int villainId = insertVillain(connection, villainName, selectVillain, rsVillain);

        //add minion
        insertMinion(connection, villainName, minionName, minionAge, townId, villainId);

        connection.close();
    }

    private static int insertTown(Connection connection, String minionTown, PreparedStatement selectTown, ResultSet rsTown) throws SQLException {
        int townId;
        if (!rsTown.next()) {
            PreparedStatement insertTown = connection.prepareStatement(INSERT_TOWN_IN_DB);
            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();

            System.out.printf(PRINT_ADD_TOWN_TO_DB, minionTown);

            ResultSet rsTownAfterNewInsert = selectTown.executeQuery();
            rsTownAfterNewInsert.next();
            townId = rsTownAfterNewInsert.getInt("id");

        } else {
            townId = rsTown.getInt("id");
        }
        return townId;
    }

    private static int insertVillain(Connection connection, String villainName, PreparedStatement selectVillain, ResultSet rsVillain) throws SQLException {
        int villainId;
        if (!rsVillain.next()) {
            PreparedStatement insertVillain = connection.prepareStatement(INSERT_VILLAIN_IN_DB);
            insertVillain.setString(1, villainName);
            insertVillain.setString(2, EVILNESS_FACTOR);
            insertVillain.executeUpdate();

            System.out.printf(PRINT_ADD_VILLAIN_TO_DB, villainName);

            ResultSet rsVillainAfterNewInsert = selectVillain.executeQuery();
            rsVillainAfterNewInsert.next();
            villainId = rsVillainAfterNewInsert.getInt("id");

        } else {
            villainId = rsVillain.getInt("id");
        }
        return villainId;
    }

    private static void insertMinion(Connection connection, String villainName, String minionName, int minionAge, int townId, int villainId) throws SQLException {
        PreparedStatement insertMinion = connection.prepareStatement(INSERT_MINION_IN_DB);
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);
        insertMinion.executeUpdate();

        //get new minion id

        PreparedStatement selectMinion = connection.prepareStatement(GET_MINION_ID_BY_NAME);
        selectMinion.setString(1, minionName);
        ResultSet rsMinion = selectMinion.executeQuery();
        rsMinion.next();
        int minionId = rsMinion.getInt("id");

        //insert in to mapping table

        PreparedStatement insertMinionVillain = connection.prepareStatement(INSERT_IN_MINION_VILLAIN);
        insertMinionVillain.setInt(1, minionId);
        insertMinionVillain.setInt(2, villainId);
        insertMinionVillain.executeUpdate();

        System.out.printf(PRINT_ADD_MINION_TO_DB, minionName, villainName);
    }
}
