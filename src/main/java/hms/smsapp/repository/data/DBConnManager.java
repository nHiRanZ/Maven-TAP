/*
 * Copyright (c) 2015. By Nimila Hiranya.
 */

package hms.smsapp.repository.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by nimila on 7/21/15.
 * Type: Class
 * Description: Handles the database functionalities
 */
public class DBConnManager {
    private Connection connection = null; //Declaration of the Connection
    private static Statement statement = null; //Declaration of the Statement
    private static final String DB_NAME = "quotes"; //Declaration and initialization of the Database Name
    private static final String TABLE_NAME = "quote"; //Declaration and initialization of the Table Name

    /**
     * Type: Method
     * Input: String, String
     * Return Type: Connection
     * Description: Creates the MySQL database connection and returns it
     */
    public static Connection getMySQLConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection connection = null; //Declaration of the connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "123admin"); //MySQL Database Credentials
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";"); //Creates the database if it's not available

            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME + "", "root", "123admin"); //Selects the Database
            statement = connection.createStatement();

            String tableCreation = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "  (quoteID           VARCHAR(14) PRIMARY KEY,"
                    + "   quoteText            VARCHAR(1000),"
                    + "   quoteAuthor          VARCHAR(50),"
                    + "   senderNo           VARCHAR(20))";

            statement.executeUpdate(tableCreation); //Creates the Table if it's not available
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
