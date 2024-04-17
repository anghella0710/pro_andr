package com.example.myapplication.Database.abstraction;

import com.example.myapplication.Model.Product;

import java.util.List;

public interface IProductDatabase {
    public long createProduct(Product product);
    public int updateProduct(Product product);
    public Product readProductById(int id);
    public List<Product> readAllProduct();
    public int deleteProductById(int id);
}
