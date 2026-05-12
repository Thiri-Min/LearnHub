package com.training.demo;

public class Course {

    private final int id;
    private final String title;
    private final String description;
    private final String icon;
    private final double price;

    public Course(int id, String title, String description, String icon, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getPrice() {
        return price;
    }
}
