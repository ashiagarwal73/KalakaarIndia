package com.agarwal.ashi.kalakaarindia.Model;

import java.util.List;

public class HomePageModel {

    String bottom_poster;

    List<String> states;

    List<Categories> categories;
    String top_poster;

    public String getTop_poster() {
        return top_poster;
    }

    public void setTop_poster(String top_poster) {
        this.top_poster = top_poster;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public String getBottom_poster() {
        return bottom_poster;
    }

    public void setBottom_poster(String bottom_poster) {
        this.bottom_poster = bottom_poster;
    }


    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<Product> getTrending_products() {
        return trending_products;
    }

    public void setTrending_products(List<Product> trending_products) {
        this.trending_products = trending_products;
    }

    List<Product> trending_products;



}
