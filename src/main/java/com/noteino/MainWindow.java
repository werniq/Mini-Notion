package com.noteino;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionListener;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow {

    private JFrame window;
    private JButton createTask;
    private JButton createNote;

    static Connection connection;

    public List<Task> tasks;
    public List<Note> notes;

    public List<JTextArea> listTasks() {
        List<JTextArea> tasksCs = new ArrayList<>();

        try {
            this.tasks = getTasksFromDatabase();
        } catch (SQLException e) {
            // todo: handle
        }

        for (Task t : this.tasks) {
            JTextArea taskArea = new JTextArea(t.getTitle() + "\n");
            tasksCs.add(taskArea);
        }

        return tasksCs;
    }

    public List<JTextArea> listNotes() {
        List<JTextArea> tasksCs = new ArrayList<>();

        try {
            this.notes = getNotesFromDatabase();
        } catch (SQLException e) {
            // todo: handle
        }

        for (Note n : this.notes) {
            JTextArea noteArea = new JTextArea(n.getTitle() + "\n" + n.getDescription());
            tasksCs.add(noteArea);
        }

        return tasksCs;
    }

    public JFrame newWindow() {
        window = new JFrame();
        window.setTitle("Note app");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);

        return window;
    }

    public JButton newButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Color.CYAN);
        
        return b;
    }

    MainWindow(Connection connection) {
        this.connection = connection;
        this.tasks = new ArrayList<>();
        this.notes = new ArrayList<>();

        newWindow();

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel notePanel = new JPanel(new GridLayout(4, 1, 5, 5)); 
        createNote = newButton("Create Note");

        // new note creation zone
        JTextArea newNArea = new JTextArea("Enter title: ");
        JTextArea newNDArea = new JTextArea("Enter description: ");
        JTextArea displayNotesArea = new JTextArea();
        displayNotesArea.setEditable(false); 

        newNArea.setPreferredSize(new Dimension(50, 30));
        newNDArea.setPreferredSize(new Dimension(100, 30));

        notePanel.add(createNote);
        notePanel.add(newNArea);
        notePanel.add(newNDArea);
        notePanel.add(displayNotesArea);

        JPanel taskPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        createTask = newButton("Create ToDo");

        // new task creation zone
        JTextArea newTArea = new JTextArea("Todo title: ");
        JTextArea displayTasksArea = new JTextArea();
        displayTasksArea.setEditable(false);

        newTArea.setPreferredSize(new Dimension(100, 5));

        taskPanel.add(createTask);
        taskPanel.add(newTArea);
        taskPanel.add(displayTasksArea);

        createTask.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                String fullStr = newTArea.getText();
                String prefixToRemove = "Todo title: ";
                String taskTitle = fullStr.substring(prefixToRemove.length());

                newTArea.setText("");
                newTArea.setText(prefixToRemove);
        
                String query = "INSERT INTO tasks(title) VALUES (" + "'" + taskTitle + "'" + /* "','"+ taskDescription + "' */ ");";
        
                try {
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.execute();
                    
                    // executing statement, and retrieving all data
                    List<Task> ts = getTasksFromDatabase();

                    String strToDisplay = "";
                    for (Task t : ts) {
                        strToDisplay += t.getTitle() + "\n";
                        // strToDisplay += t.getDescription() + "\n";
                    }
                    displayTasksArea.setText("");
                    displayTasksArea.setText(strToDisplay);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Inside the createNote ActionListener
        createNote.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent a) {
                String fullStr = newNArea.getText();
                String prefixToRemove = "Enter title: ";
                String noteTitle = fullStr.substring(prefixToRemove.length());
                newNArea.setText(prefixToRemove);

                    
                String fullDesc = newNDArea.getText();
                prefixToRemove = "Enter description: ";
                String noteDescription = fullDesc.substring(prefixToRemove.length());
                newNDArea.setText(prefixToRemove);
                

                String query = "INSERT INTO notes(title, description) VALUES ('" + noteTitle + "','" + noteDescription + "');";
        
                try {
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.execute();
                    
                    // executing statement, and retrieving all data
                    List<Note> ts = getNotesFromDatabase();

                    String strToDisplay = "";
                    for (Note n : ts) {
                        strToDisplay += n.getTitle() + "\n";
                        strToDisplay += n.getDescription() + "\n";
                    }
                
                    // Update the display area when a new note is created
                    displayNotesArea.setText("");
                    displayNotesArea.setText(strToDisplay);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Populate the display areas with existing tasks and notes
        // for (JTextArea taskArea : listTasks()) {
        //     displayTasksArea.append("\n" + taskArea.getText());
        // }

        // for (JTextArea noteArea : listNotes()) {
        //     displayNotesArea.append("\n" + noteArea.getText());
        // }

        panel.add(notePanel);
        panel.add(taskPanel);

        window.add(panel);
    }

    // Helper method to get notes from the database
    private List<Note> getNotesFromDatabase() throws SQLException {
        String query = "SELECT * FROM notes;";

        Statement stmt = connection.createStatement();

        try {
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String description = result.getString("description");

                Note n = new Note(id, title, description);
                notes.add(n);
            }

        } catch (SQLException e) {
            // todo: handle
        }

        return notes;
    }

    // Helper method to get tasks from the database
    private List<Task> getTasksFromDatabase() throws SQLException {
        String query = "SELECT * FROM tasks;";

        Statement stmt = connection.createStatement();

        try {
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                // String description = result.getString("description");

                Task t = new Task(id, title);
                tasks.add(t);
            }
            
        } catch (SQLException e) {
            // todo: handle
            System.out.println("SOMETHING WENT WRONG!@!!!!");
        }

        return tasks;
    }

    public void show() {
        window.setVisible(true);
    }
}
