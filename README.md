#### 1. Create DB, permissions etc.

----->creating a database

!!!Note: A new database can be created from an existing database user only. Here created the database iplproject from postgres root user.!!!

postgres=# create database iplproject;
CREATE DATABASE

 
--->creating a new user
!!!Note: A new user can be created from an existing database only. Here created the user prrasanthi inside iplproject database.!!!

postgres=# \c iplproject
You are now connected to database "iplproject" as user "postgres".
dummydb=# create user prrasanthi with password 'prrasanthi';


--->Making the user owner of the DB

dummydb=# ALTER DATABASE iplproject OWNER TO prrasanthi;
ALTER DATABASE

dummydb=# \l


#### 2. Load CSV

-->Provide the columns schema to the tables

iplproject=#create table matches (id int,
                     season int,
                     city varchar(50),
                     date date,
                     team1 varchar(50),
                     team2 varchar(50),
                     toss_winner varchar(50),
                     toss_decision varchar(50),
                     result varchar(50),
                     dl_applied int,
                     winner varchar(50),
                     win_by_runs int,
                     win_wickets int,
                     player_of_match varchar(50),
                     venue varchar(100),
                     umpire1 varchar(50),
                     umpire2 varchar(50),
                     umpire3 varchar(50));
                     
iplproject=#create table deliveries (match_id int,
                        inning int,
                        batting_team varchar(200),
                        bowling_team varchar(200),
                        over int,
                        ball int,
                        batsman varchar(200),
                        non_striker varchar(200),
                        bowler varchar(200),
                        is_super_over int,
                        wide_runs int,
                        bye_runs int,
                        legbye_runs int,
                        noball_runs int,
                        penalty_runs int,
                        batsman_runs int,
                        extra_runs int,
                        total_runs int,
                        player_dismissed varchar(200),
                        dismissal_kind varchar(200),
                        fielder varchar(200));


-->Upload the csv data file to the table

iplproject=# COPY matches FROM '/home/prasanthi/Desktop/IPL_Project/matches.csv' DELIMITER ',' CSV HEADER;
iplproject=# COPY deliveries FROM '/home/prasanthi/Desktop/IPL_Project/deliveries.csv' DELIMITER ',' CSV HEADER;


#### 3. Solving the IPL problems

##### 1. Number of matches played per year of all the years in IPL. 
-->select season,count(*)as years_count from matches group by season;

##### 2. Number of matches won of all teams over all the years of IPL.
-->select winner as team,count(*)as no_of_matches_won from matches where winner!='' group by winner;

##### 3. For the year 2016 get the extra runs conceded per team.
-->select bowling_team as team ,sum(extra_runs)as extra_runs from deliveries join matches on deliveries.match_id = matches.id where season =  2016 group by bowling_team;
##### 4. For the year 2015 get the top economical bowlers.
--->select bowler,sum(total_runs)/((count(bowler)/6) +((mod(count(bowler),6))/6)) as economy from deliveries join matches on deliveries.match_id = matches.id where matches.season =2015 group by bowler order by economy limit 10;

**Note:** the result in this case is not a graph but SQL Result Set.
