package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.*;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {
    private static final String SELECT_MINION_BY_ID = "select name, age from minions where id = ?";
    private static final String PRINT_FORMAT = "%s %d";
    public static void main(String[] args) throws SQLException {
        Connection connection = P00_UtilConnection.getSqlConnection();

        Scanner scanner = new Scanner(System.in);
        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement callableStatement = connection.prepareCall("CALL `usp_get_older`(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.executeUpdate();

        PreparedStatement selectMinion = connection.prepareStatement(SELECT_MINION_BY_ID);
        selectMinion.setInt(1,minionId);
        ResultSet minionToPrint = selectMinion.executeQuery();
        minionToPrint.next();
        System.out.printf(PRINT_FORMAT, minionToPrint.getString("name"), minionToPrint.getInt("age"));

        connection.close();
    }
}


//    DELIMITER $$
//    CREATE PROCEDURE `usp_get_older`(`minion_id` INT)
//        BEGIN
//        UPDATE `minions`
//        SET `age` = `age` + 1
//        WHERE `id` = `minion_id`;
//        END $$
//        DELIMITER ;
