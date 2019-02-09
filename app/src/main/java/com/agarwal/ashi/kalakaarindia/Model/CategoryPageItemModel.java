package com.agarwal.ashi.kalakaarindia.Model;

import java.util.List;

public class CategoryPageItemModel {
    String name;
    String top_image;
    String type;
    List<Product> products;
    List<Product> trending_product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTop_image() {
        return top_image;
    }

    public void setTop_image(String top_image) {
        this.top_image = top_image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getTrending_product() {
        return trending_product;
    }

    public void setTrending_product(List<Product> trending_product) {
        this.trending_product = trending_product;
    }
}
