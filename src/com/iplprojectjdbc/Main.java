package com.iplprojectjdbc;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/iplproject";
        Connection connection = DriverManager.getConnection(url, "prrasanv", "prrasanv");
        Statement statement = connection.createStatement();
        findMatchesPlayedPerYear(statement);
        findTotalMatchesPlayedByTeams(statement);
        findExtraRunsScoredIn2016(statement);
        findTopEconomicalBowler2015(statement);
        statement.close();
        connection.close();
    }

    static void findMatchesPlayedPerYear(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select season,count(*)as years_count " +
                "from matches group by season;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String column1 = rsmd.getColumnName(1);
        String column2 = rsmd.getColumnLabel(2);
        System.out.println("Number of matches played per year of all the years in IPL");
        System.out.println(column1 + " " + column2);
        while (resultSet.next()) {
            String years = resultSet.getString("season");
            String yearsCount = String.valueOf(resultSet.getInt("years_count"));
            System.out.println(years + "   " + yearsCount);
        }
        resultSet.close();
    }

    static void findTotalMatchesPlayedByTeams(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select winner as team,count(*)as no_of_matches_won " +
                "from matches where winner!='' group by winner;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String column1 = rsmd.getColumnName(1);
        String column2 = rsmd.getColumnLabel(2);
        System.out.println("\nNumber of matches won of all teams over all the years of IPL\n");
        System.out.println(column1 + "\t\t\t" + column2);
        while (resultSet.next()) {
            String team = resultSet.getString("team");
            String matchesCount = String.valueOf(resultSet.getInt("no_of_matches_won"));
            System.out.println(team + "   " + matchesCount);
        }
        resultSet.close();
    }

    static void findExtraRunsScoredIn2016(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select bowling_team as team ,sum(extra_runs)as extra_runs " +
                "from deliveries join matches on deliveries.match_id = matches.id " +
                "where season =  2016 group by bowling_team");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String columnName1 = rsmd.getColumnName(1);
        String columnName2 = rsmd.getColumnLabel(2);
        System.out.println("\nFor the year 2016 get the extra runs conceded per team\n");
        System.out.println(columnName1 + "    " + columnName2);
        while (resultSet.next()) {
            String team = resultSet.getString("team");
            String extra_runs = String.valueOf(resultSet.getInt("extra_runs"));
            System.out.println(team + "    " + extra_runs);
        }
        resultSet.close();
    }

    static void findTopEconomicalBowler2015(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select bowler," +
                "sum(total_runs)/((count(bowler)/6) +((mod(count(bowler),6))/6)) as economy " +
                "from deliveries join matches on deliveries.match_id = matches.id " +
                "where matches.season =2015 group by bowler order by economy limit 10;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String columnName1 = rsmd.getColumnName(1);
        String columnName2 = rsmd.getColumnLabel(2);
        System.out.println("\nFor the year 2015 get the top economical bowlers\n");
        System.out.println(columnName1 + "    " + columnName2);
        while (resultSet.next()) {
            String bowler = resultSet.getString("bowler");
            String economy = String.valueOf(resultSet.getInt("economy"));
            System.out.println(bowler + "   " + economy);
        }
        resultSet.close();
    }
}