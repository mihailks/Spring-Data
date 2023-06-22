package L04_ExerciseJavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P07PrintAllMinionNames {

    public static final String SELECT_MINIONS = "SELECT name FROM minions";


    public static void main(String[] args) throws SQLException {
        Connection connection = P00_UtilConnection.getSqlConnection();

        List<String> minions = new ArrayList<>();

        PreparedStatement selectAllMinions = connection.prepareStatement(SELECT_MINIONS);
        ResultSet rsMinions = selectAllMinions.executeQuery();

        while (rsMinions.next()){
            minions.add(rsMinions.getString("name"));
        }

        for (int i = 0; i < minions.size()/2; i++) {
            System.out.println(minions.get(i));
            System.out.println(minions.get(minions.size()-i-1));
        }
        connection.close();
    }
}
