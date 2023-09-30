package com.example.makenotes;

public class noteView {
    private int img, id;
    private String title, content, time;

    public noteView(int img, String title, String content, String time, int id) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.time = time;
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }
}
