package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P05ChangeTownNamesCasing {

    public static final String SELECT_TOWNS_BY_COUNTRY = "SELECT * FROM `towns` WHERE `country` = ?;";

    public static final String test = "UPDATE `towns` SET `name` = ? WHERE `name`= ?;";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        Connection connection = P00_UtilConnection.getSqlConnection();

        PreparedStatement selectTowns = connection.prepareStatement(SELECT_TOWNS_BY_COUNTRY);
        selectTowns.setString(1, country);
        ResultSet towns = selectTowns.executeQuery();

        List<String> townsAsList = new ArrayList<>();

        while (towns.next()) {
            String currentTown = towns.getString("name");
            townsAsList.add(currentTown);
        }

        if (townsAsList.isEmpty()) {
            System.out.println("No town names were affected.");
            return;
        } else {

            for (String town : townsAsList) {
                PreparedStatement updateTown = connection.prepareStatement(test);
                updateTown.setString(1, town.toUpperCase());
                updateTown.setString(2, town);
                updateTown.executeUpdate();
            }
        }
        System.out.printf("%d town names were affected%n%s", townsAsList.size(), townsAsList);

        connection.close();
    }
}
