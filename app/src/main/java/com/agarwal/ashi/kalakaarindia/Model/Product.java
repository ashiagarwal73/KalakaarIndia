package com.agarwal.ashi.kalakaarindia.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    String product_details;

    String product_image;

    String product_name;

    int product_price;

    String product_id;

    String product_image_2;

    String product_image_3;

    String product_size_text;

    String selected_size;
    List<String> size=new ArrayList<>();

    int product_quantity;

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getSelected_size() {
        return selected_size;
    }

    public void setSelected_size(String selected_size) {
        this.selected_size = selected_size;
    }


    public String getProduct_size_text() {
        return product_size_text;
    }

    public void setProduct_size_text(String product_size_text) {
        this.product_size_text = product_size_text;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public String getProduct_image_2() {
        return product_image;
    }

    public void setProduct_image_2(String product_image_2) {
        this.product_image_2 = product_image_2;
    }

    public String getProduct_image_3() {
        return product_image;
    }

    public void setProduct_image_3(String product_image_3) {
        this.product_image_3 = product_image_3;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }


}
