package com.example.ratingapp.model;

public class Content {

    private int id;
    private String title;
    private String content;
    private String image;
    private String createDate;

    public Content() {
    }

    public Content(int id, String title, String content, String image, String createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
