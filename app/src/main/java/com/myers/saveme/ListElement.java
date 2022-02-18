package com.myers.saveme;

public class ListElement {
    public String color;
    public String title;
    public String description;
    public String date;

    public ListElement(String color, String title, String description, String date) {
        this.color = color;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}