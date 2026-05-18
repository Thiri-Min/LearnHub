package com.training.demo;

import java.util.List;

public class TechTopic {
    private final String id;
    private final String title;
    private final String icon;
    private final String description;
    private final List<LearningMaterial> materials;

    public TechTopic(String id, String title, String icon, String description, List<LearningMaterial> materials) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.description = description;
        this.materials = materials;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public List<LearningMaterial> getMaterials() {
        return materials;
    }
}
