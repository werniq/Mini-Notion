package com.noteino;

public class Note {
    private int id;
    
    private String title;

    private String description;

    Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
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
