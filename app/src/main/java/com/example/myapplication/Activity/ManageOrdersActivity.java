package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.OrderAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.concretions.OrderDatabase;
import com.example.myapplication.Model.Order;
import com.example.myapplication.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManageOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseHelper databaseHelper;
    private List<Order> orderList;
    private OrderDatabase db;
    private EditText edtOrderIdProduct, edtOrderCustomerName, edtOrderCustomerAddress, edtOrderCustomerPhone, edtOrderStatus, edtOrderAmount;
    private Button btnAddOrder, btnUpdateOrder, btnDeleteOrder;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        db = new OrderDatabase(databaseHelper);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        edtOrderIdProduct = findViewById(R.id.edtOrderIdProduct);
        edtOrderCustomerName = findViewById(R.id.edtOrderNameCustomer);
        edtOrderCustomerPhone = findViewById(R.id.edtOrderPhoneCustomer);
        edtOrderCustomerAddress = findViewById(R.id.edtOrderAddressCustomer);
        edtOrderAmount = findViewById(R.id.edtOrderAmount);
        edtOrderStatus = findViewById(R.id.edtOrderStatus);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnDeleteOrder = findViewById(R.id.btnDeleteOrder);

        orderList = new ArrayList<>();
        Log.d("Tag", "Debug message");
       orderList = db.readAllOrder();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderAdapter(this, orderList, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                selectedOrder = order;
                edtOrderIdProduct.setText(String.valueOf(selectedOrder.getIdProduct()) );
                edtOrderCustomerName.setText(selectedOrder.getNameCustomer());
                edtOrderCustomerPhone.setText(selectedOrder.getNameCustomer());
                edtOrderCustomerAddress.setText(selectedOrder.getAddressCustomer());
                edtOrderStatus.setText(selectedOrder.getStatus());
                edtOrderAmount.setText(String.valueOf(selectedOrder.getAmount()));
            }
        });
        recyclerView.setAdapter(orderAdapter);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idOrderProduct = Integer.parseInt(edtOrderIdProduct.getText().toString().trim());
                String nameOrderCustomer = edtOrderCustomerName.getText().toString().trim();
                String phoneOrderCustomer = edtOrderCustomerPhone.getText().toString().trim();
                String addressOrderCustomer = edtOrderCustomerAddress.getText().toString().trim();
                int amountOrder = Integer.parseInt(edtOrderAmount.getText().toString().trim());
                String statusOrder = edtOrderStatus.getText().toString().trim();
                Timestamp timeOrder = new Timestamp(System.currentTimeMillis());
                Order order = new Order(idOrderProduct, nameOrderCustomer, phoneOrderCustomer, addressOrderCustomer,statusOrder, timeOrder, amountOrder);
                long result = db.createOrder(order);

                if (result > 0) {
                    Toast.makeText(ManageOrdersActivity.this, "Order added successfully!", Toast.LENGTH_SHORT).show();
                    refreshOrderList();
                    clearFields();
                }
                else if(result == -1) {
                    Toast.makeText(ManageOrdersActivity.this, "Lỗi! Vui lòng đảm bảo id của sản phẩm tồn tại", Toast.LENGTH_LONG).show();
                }
                else if(result == -2){
                    Toast.makeText(ManageOrdersActivity.this, "Lỗi! Vui lòng nhập số lượng đặt nhò hơn hoặc bằng số lượng đang có ", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ManageOrdersActivity.this, "Failed! Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    int idOrderProduct = Integer.parseInt(edtOrderIdProduct.getText().toString().trim());
                    String nameOrderCustomer = edtOrderCustomerName.getText().toString().trim();
                    String phoneOrderCustomer = edtOrderCustomerPhone.getText().toString().trim();
                    String addressOrderCustomer = edtOrderCustomerAddress.getText().toString().trim();
                    int amountOrder = Integer.parseInt(edtOrderAmount.getText().toString().trim());
                    String statusOrder = edtOrderStatus.getText().toString().trim();
                    selectedOrder.setIdProduct(idOrderProduct);
                    selectedOrder.setNameCustomer(nameOrderCustomer);
                    selectedOrder.setPhoneCustomer(phoneOrderCustomer);
                    selectedOrder.setAddressCustomer(addressOrderCustomer);
                    selectedOrder.setAmount(amountOrder);
                    selectedOrder.setStatus(statusOrder);
                    int result = db.updateOrder(selectedOrder);

                    if (result > 0) {
                        Toast.makeText(ManageOrdersActivity.this, "Order updated successfully!", Toast.LENGTH_SHORT).show();
                        refreshOrderList();
                    } else {
                        Toast.makeText(ManageOrdersActivity.this, "Failed to update order!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageOrdersActivity.this, "Please select an order to update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    int result = db.deleteOrderById(selectedOrder.getId());

                    if (result > 0) {
                        Toast.makeText(ManageOrdersActivity.this, "Order deleted successfully!", Toast.LENGTH_SHORT).show();
                        refreshOrderList();
                        clearFields();
                    } else {
                        Toast.makeText(ManageOrdersActivity.this, "Failed to delete order!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageOrdersActivity.this, "Please select an order to delete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshOrderList() {
        orderList.clear();
        orderList.addAll(db.readAllOrder());
        orderAdapter.notifyDataSetChanged();
    }

    private void clearFields() {
        edtOrderIdProduct.setText("");
        edtOrderCustomerName.setText("");
        edtOrderCustomerPhone.setText("");
        edtOrderCustomerAddress.setText("");
        edtOrderAmount.setText("");
        edtOrderStatus.setText("");
        selectedOrder = null;
    }
}
