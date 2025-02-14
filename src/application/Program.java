package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {

    public static void main(String[] args) {

        Connection connection = DB.getConnection();
        if (connection != null) {
            System.out.println("Success!");
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        } else {
            System.out.println("Unexpected error!");
        }
    }
}
