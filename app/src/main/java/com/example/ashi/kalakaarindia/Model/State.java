package com.example.ashi.kalakaarindia.Model;

import java.util.List;

public class State {
    String top_poster;
    String bottom_poster;
    String hello_msg;
    List<Categories> categories;

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public String getHello_msg() {
        return hello_msg;
    }

    public void setHello_msg(String hello_msg) {
        this.hello_msg = hello_msg;
    }

    public String getBottom_poster() {
        return bottom_poster;
    }

    public void setBottom_poster(String bottom_poster) {
        this.bottom_poster = bottom_poster;
    }

    public String getTop_poster() {
        return top_poster;
    }

    public void setTop_poster(String top_poster) {
        this.top_poster = top_poster;
    }
}
