package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.concretions.ProductDatabase;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;
    private List<Product> productList;
    private ProductDatabase db;
    private EditText edtProductName, edtProductBrand, edtProductVol, edtProductImgUrl, edtProductPrice, edtProductCapitalPrice, edtProductAmount;
    private Button btnAdd, btnUpdate, btnDelete;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        db = new ProductDatabase(databaseHelper);

        recyclerView = findViewById(R.id.recyclerView);

        edtProductName = findViewById(R.id.edtProductName);
        edtProductBrand = findViewById(R.id.edtProductBrand);
        edtProductVol = findViewById(R.id.edtProductVol);
        edtProductImgUrl = findViewById(R.id.edtProductImageUrl);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductCapitalPrice = findViewById(R.id.edtProductCapitalPrice);
        edtProductAmount = findViewById(R.id.edtProductAmount);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        productList = new ArrayList<>();

       productList = db.readAllProduct();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        productAdapter = new ProductAdapter(this, productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                selectedProduct = product;
                Log.d("tag",product.toString());
                edtProductName.setText(selectedProduct.getName());
                edtProductBrand.setText(selectedProduct.getBrand());
                edtProductVol.setText(String.valueOf(selectedProduct.getVol()));
                edtProductImgUrl.setText(selectedProduct.getImgUrl());
                edtProductCapitalPrice.setText(String.valueOf(selectedProduct.getCapitalPrice()));
                edtProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
                edtProductAmount.setText(String.valueOf(selectedProduct.getAmount()));
            }
        });
        recyclerView.setAdapter(productAdapter);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProduct != null) {
                    Log.d("ddaay laf tag","day la tag");
                    String productName = edtProductName.getText().toString().trim();
                    String productBrand = edtProductBrand.getText().toString().trim();
                    int productVol = Integer.parseInt(edtProductVol.getText().toString().trim());
                    String productImageUrl = edtProductImgUrl.getText().toString().trim();
                    int productCapitalPrice = Integer.parseInt(edtProductCapitalPrice.getText().toString().trim());
                    int productPrice = Integer.parseInt(edtProductPrice.getText().toString().trim());
                    int productAmount = Integer.parseInt(edtProductAmount.getText().toString().trim());
//                    Product product = new Product(productName, productBrand,productVol,productImageUrl, productCapitalPrice,productPrice, productAmount);

                    selectedProduct.setName(productName);
                    selectedProduct.setBrand(productBrand);
                    selectedProduct.setVol(productVol);
                    selectedProduct.setImgUrl(productImageUrl);
                    selectedProduct.setCapitalPrice(productCapitalPrice);
                    selectedProduct.setPrice(productPrice);
                    selectedProduct.setAmount(productAmount);
                    int result = db.updateProduct(selectedProduct);

                    if (result > 0) {
                        Toast.makeText(ManageProductsActivity.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                        refreshList();
                        clearFields();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Failed to update product!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Please select a product to update!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String productName = edtProductName.getText().toString().trim();
                String productBrand = edtProductBrand.getText().toString().trim();
                int productVol = Integer.parseInt(edtProductVol.getText().toString().trim());
                String productImageUrl = edtProductImgUrl.getText().toString().trim();
                int productCapitalPrice = Integer.parseInt(edtProductCapitalPrice.getText().toString().trim());
                int productPrice = Integer.parseInt(edtProductPrice.getText().toString().trim());
                int productAmount = Integer.parseInt(edtProductAmount.getText().toString().trim());
                Product product = new Product(productName, productBrand,productVol,productImageUrl, productCapitalPrice,productPrice, productAmount);
                long result = db.createProduct(product);
                if (result > 0) {
                    Toast.makeText(ManageProductsActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                   refreshList();
                    clearFields();
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Failed to add order!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProduct != null) {
                    int result = db.deleteProductById(selectedProduct.getId());

                    if (result > 0) {
                        Toast.makeText(ManageProductsActivity.this, "Product deleted successfully!", Toast.LENGTH_SHORT).show();
                        refreshList();
                        clearFields();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Failed to delete product!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageProductsActivity.this, "Please select a product to delete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshList() {
        productList.clear();
        productList.addAll(db.readAllProduct());
        productAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        edtProductName.setText("");
        edtProductBrand.setText("");
        edtProductVol.setText("");
        edtProductImgUrl.setText("");
        edtProductCapitalPrice.setText("");
        edtProductPrice.setText("");
        edtProductAmount.setText("");
        selectedProduct = null;
    }
}
