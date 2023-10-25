package com.example.communityexample;

public class CommunityListModel {
    String id;
    String title;
    String regDate;
    public CommunityListModel(String id, String title, String regDate) {
        this.id = id;
        this.title = title;
        this.regDate = regDate;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
