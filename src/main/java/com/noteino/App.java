package com.noteino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App implements ActionListener {
    static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASSWORD = "Matwyenko1_";

    JFrame frame;
    JTextField textField;
    JButton[] actionButtons = new JButton[2];

    static Connection connection;
    // create task | delete task | edit task | get single task | get all tasks
    // create note | delete note | edit note | get single note | get all tasks

    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    App() {
    }

    public void actionPerformed(ActionEvent e) 
    {

    }

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            PreparedStatement prepareStatement = connection.prepareStatement("truncate table notes;");
            prepareStatement.execute();

            prepareStatement = connection.prepareStatement("truncate table tasks;");
            prepareStatement.execute();

            System.out.println("Successfully connected to PostgreSQL database.");
            // Perform database operations here
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // ensure that all swing code runs in the same thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override

            public void run() 
            {
                MainWindow main = new MainWindow(connection);
                main.show();
            }
        });
    }
}