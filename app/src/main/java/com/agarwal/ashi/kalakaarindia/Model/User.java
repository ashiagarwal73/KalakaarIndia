package com.agarwal.ashi.kalakaarindia.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String name;
    String phone_number;
    String password;
    String gender;
    String email;
    List<Product> fav_product;
    List<Product> cart_product;

    public List<Order> getOrders() {
        if(orders==null)
            orders=new ArrayList<>();
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    List<Order> orders;

    public List<Product> getFav_product() {
        if(fav_product==null)
        {
            return new ArrayList<>();
        }
        return fav_product;
    }

    public void setFav_product(List<Product> fav_product) {
        this.fav_product = fav_product;
    }

    public List<Product> getCart_product() {
        if(cart_product==null)
        {
            return new ArrayList<>();
        }
        return cart_product;
    }

    public void setCart_product(List<Product> cart_product) {
        this.cart_product = cart_product;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public void addFavProduct(Product product,String selectedSize,int quantity) {

        if(fav_product==null)
        {
            fav_product=new ArrayList<>();
            product.setSelected_size(selectedSize);
            product.setProduct_quantity(quantity);
            fav_product.add(product);
        }
        else {
            if(!fav_product.contains(product)) {
                product.setSelected_size(selectedSize);
                product.setProduct_quantity(quantity);
                fav_product.add(product);
            }
        }
    }
    public void addCartProduct(Product product,String selectedSize,int quantity) {

        if(cart_product==null)
        {
            cart_product=new ArrayList<>();
            product.setSelected_size(selectedSize);
            product.setProduct_quantity(quantity);
            cart_product.add(product);
        }
        else {
            if(!cart_product.contains(product)) {
                product.setSelected_size(selectedSize);
                product.setProduct_quantity(quantity);
                cart_product.add(product);
            }
        }
    }
}
