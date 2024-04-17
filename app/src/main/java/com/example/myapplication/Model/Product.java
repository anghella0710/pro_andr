//package com.example.myapplication.Model;
//
//public class Product {
//    private int id;
//    private String name;
//    private double price;
//
//    public Product() {
//    }
//
//    public Product(String name, double price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public Product(int id, String name, double price) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//    }
//
//    // Getter và Setter cho id
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    // Getter và Setter cho tên sản phẩm
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    // Getter và Setter cho giá sản phẩm
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                '}';
//    }
//}
package com.example.myapplication.Model;

public class Product {
//    private int id;
//    private String name;
//    private double price;
//
//    public Product() {
//    }
//
//    public Product(String name, double price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public Product(int id, String name, double price) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//    }
//
//    // Getter và Setter cho id
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    // Getter và Setter cho tên sản phẩm
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    // Getter và Setter cho giá sản phẩm
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", price=" + price +
//                '}';
//    }

    private int id;
    private String name;
    private String brand;
    private int vol;
    private String imgUrl;
    private int capitalPrice;
    private int price;
    private int amount;

    public Product(int id, String name, String brand, int vol, String imgUrl, int capitalPrice, int price, int amount) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.vol = vol;
        this.imgUrl = imgUrl;
        this.capitalPrice = capitalPrice;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getVol() {
        return vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getCapitalPrice() {
        return capitalPrice;
    }

    public void setCapitalPrice(int capitalPrice) {
        this.capitalPrice = capitalPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product(String name, String brand, int vol, String imgUrl, int capitalPrice, int price, int amount) {
        this.name = name;
        this.brand = brand;
        this.vol = vol;
        this.imgUrl = imgUrl;
        this.capitalPrice = capitalPrice;
        this.price = price;
        this.amount = amount;
    }

    public Product(String name, String brand, int vol, String imgUrl, int capitalPrice, int price) {
        this.name = name;
        this.brand = brand;
        this.vol = vol;
        this.imgUrl = imgUrl;
        this.capitalPrice = capitalPrice;
        this.price = price;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", vol=" + vol +
                ", imgUrl='" + imgUrl + '\'' +
                ", capitalPrice=" + capitalPrice +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

}
