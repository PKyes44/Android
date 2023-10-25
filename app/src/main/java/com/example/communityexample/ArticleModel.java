package com.example.communityexample;

public class ArticleModel {
    String id;
    String title;
    String content;
    String regDate;

    public ArticleModel(String id, String title, String content, String regDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
    }
    public ArticleModel() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getRegDate() {
        return regDate;
    }
}
