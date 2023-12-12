package com.noteino;

public class Task {
 
    private int id;
    private String title;
    private String description;


    Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    Task(int id, String title) {
        this.id = id;
        this.title = title;
        // this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }


}
