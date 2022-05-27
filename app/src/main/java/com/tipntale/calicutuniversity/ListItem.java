package com.tipntale.calicutuniversity;

public class ListItem {

    private String title;
    private String link;
    private String date;
    private int image;

    public ListItem(String title, String link, String date, int image) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }


}
