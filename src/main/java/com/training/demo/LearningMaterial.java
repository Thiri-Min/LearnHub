package com.training.demo;

public class LearningMaterial {
    private final String title;
    private final String type;
    private final String platform;
    private final String url;

    public LearningMaterial(String title, String type, String platform, String url) {
        this.title = title;
        this.type = type;
        this.platform = platform;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getPlatform() {
        return platform;
    }

    public String getUrl() {
        return url;
    }
}
