import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {


    public static void main(String[] args) throws SQLException {
        String url="jdbc:postgresql://localhost:5432/iplproject";
        Connection connection=DriverManager.getConnection(url,"prrasanv","prrasanv");
        Statement statement=connection.createStatement();
        getMatchesPLayedPerYear(connection,statement);
        getTotalMatchesPlayedByTeams(connection,statement);
        getExtraRunsScoredIn2016(connection,statement);
        getTopEconomicalBowler2015(connection,statement);

        statement.close();
        connection.close();
    }

    static void getMatchesPLayedPerYear(Connection connection,Statement statement) throws SQLException {
        ResultSet resultSet=statement.executeQuery("select season,count(*)as years_count from matches group by season;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String name1 = rsmd.getColumnName(1);
        String name2 = rsmd.getColumnLabel(2);
        System.out.println("Number of matches played per year of all the years in IPL");
        System.out.println(name1+" "+name2);
        while(resultSet.next()){
            String st1=resultSet.getString("season");
            String st2= String.valueOf(resultSet.getInt("years_count"));
            System.out.println(st1+"   "+st2);
        }
        resultSet.close();
    }

    static void getTotalMatchesPlayedByTeams(Connection connection,Statement statement) throws SQLException{
        ResultSet resultSet=statement.executeQuery("select winner as team,count(*)as no_of_matches_won from matches where winner!='' group by winner;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String name1 = rsmd.getColumnName(1);
        String name2 = rsmd.getColumnLabel(2);
        System.out.println("\nNumber of matches won of all teams over all the years of IPL\n");
        System.out.println(name1+"\t\t\t"+name2);
        while(resultSet.next()){
            String st1=resultSet.getString("team");
            String st2= String.valueOf(resultSet.getInt("no_of_matches_won"));
            System.out.println(st1+"   "+st2);
        }
        resultSet.close();
    }

    static void getExtraRunsScoredIn2016(Connection connection,Statement statement) throws SQLException{
        ResultSet resultSet=statement.executeQuery("select bowling_team as team ,sum(extra_runs)as extra_runs from deliveries join matches on deliveries.match_id = matches.id where season =  2016 group by bowling_team");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String name1 = rsmd.getColumnName(1);
        String name2 = rsmd.getColumnLabel(2);
        System.out.println("\nFor the year 2016 get the extra runs conceded per team\n");
        System.out.println(name1+"    "+name2);
        while(resultSet.next()){
            String st1=resultSet.getString("team");
            String st2= String.valueOf(resultSet.getInt("extra_runs"));
            System.out.println(st1+"    "+st2);
        }
        resultSet.close();
    }

    static void getTopEconomicalBowler2015(Connection connection,Statement statement) throws SQLException{
        ResultSet resultSet=statement.executeQuery("select bowler,sum(total_runs)/((count(bowler)/6) +((mod(count(bowler),6))/6)) as economy from deliveries join matches on deliveries.match_id = matches.id where matches.season =2015 group by bowler order by economy limit 10;");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String name1 = rsmd.getColumnName(1);
        String name2 = rsmd.getColumnLabel(2);
        System.out.println("\nFor the year 2015 get the top economical bowlers\n");
        System.out.println(name1+"    "+name2);
        while(resultSet.next()){
            String st1=resultSet.getString("bowler");
            String st2= String.valueOf(resultSet.getInt("economy"));
            System.out.println(st1+"   "+st2);
        }
        resultSet.close();
    }
}