package com.noteino;

import javax.swing.*;
import java.awt.*;

public class NoteComponent extends JPanel {
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel idLabel;

    public NoteComponent(int id, String title, String description) {
        setLayout(new GridLayout(2, 1));

        idLabel = new JLabel("Id: " + Integer.toString(id));
        add(idLabel);


        titleLabel = new JLabel("Title:" + title);
        add(titleLabel);

        descriptionLabel = new JLabel("Description: " + description);
        add(descriptionLabel);
    }

    public void setTitle(String title) {
        titleLabel.setText("Title: " + title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText("Description: " + description);
    }
}
