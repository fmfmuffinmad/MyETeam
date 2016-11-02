package com.myeteam.muffinmad.myeteam.classes;

/**
 * Created by muffinmad on 02/11/2016.
 */

public class ListModelSimple {

    private String title;
    private String Subtitle;
    private int id;

    public ListModelSimple(String title, String subtitle, int id) {
        this.title = title;
        Subtitle = subtitle;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
