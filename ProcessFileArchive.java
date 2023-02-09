[8:37 PM, 2/9/2023] Divya US (For Freelancing Work): import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BatchProcess {
    private static final int DAY_LIMIT = 180;
    private static final int JOB_LIMIT = 10;

    public static void main(String[] args) {
        // Connect to the database
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://<server>:<port>;databaseName=<database>",
                    "<username>", "<password>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
   …
[8:37 PM, 2/9/2023] Divya US (For Freelancing Work): import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BatchProcess {
  private static final int DAY_LIMIT = 180;
  private static final int JOB_LIMIT = 10;

  public static void main(String[] args) {
    List<Integer> jobIds = getOldJobIds();
    if (jobIds.isEmpty()) {
      System.out.println("No jobs found that are older than " + DAY_LIMIT + " days.");
      return;
    }
    int count = Math.min(jobIds.size(), JOB_LIMIT);
    System.out.println("Moving data for " + count + " jobs to the DESTINATION table.");
    moveDataToDestinationTable(jobIds.…
[8:38 PM, 2/9/2023] Divya US (For Freelancing Work): Step 1: Connect to the SQL Server database and retrieve the data from T_PROC_FILE_STG

import java.sql.*;

public class BatchProcess {
  public static void main(String[] args) {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Load the driver class
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

      // Establish the connection to the database
      con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=MyDB", "user", "password");

      // Create a statement
      stmt = con.createStatement();

      // Execute the query to retrieve data from T_PROC_FILE_STG
	  
	  rs = stmt.executeQuery("SELECT * FROM T_PROC_FILE_STG WHERE DATEDIFF(day, creation_date, GETDATE()) > 180 ORDER BY creation_date ASC");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

Step 2: Copy the data for the first 10 jobs to the DESTINATION table.

import java.sql.*;

public class BatchProcess {
  public static void main(String[] args) {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Load the driver class
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

      // Establish the connection to the database
      con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=MyDB", "user", "password");

      // Create a statement
      stmt = con.createStatement();

      // Execute the query to retrieve data from T_PROC_FILE_STG
      rs = stmt.executeQuery("SELECT * FROM T_PROC_FILE_STG WHERE DATEDIFF(day, creation_date, GETDATE()) > 180 ORDER BY creation_date ASC");

      int count = 0;
      while (rs.next() && count < JOB_LIMIT) {
        int id = rs.getInt("id");
        String file_name = rs.getString("file_name");
        Timestamp creation_date = rs.getTimestamp("creation_date");

        // Copy the data to the DESTINATION table
        stmt.executeUpdate("INSERT INTO DESTINATION (id, file_name, creation_date) VALUES (" + id + ", '" + file_name + "', '" + creation_date + "')");
        count++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


Step 3: Delete the data from T_PROC_FILE_STG for the copied jobs.
import java.sql.*;

public class BatchProcess {
  public static void main(String[] args) {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Load the driver class
      Class.forName("com
