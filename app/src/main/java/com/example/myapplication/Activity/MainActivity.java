package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        DatabaseHelper db = new DatabaseHelper(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();


//                boolean isRegistered = db.registerUser(username, password);
//
//                if (isRegistered) {
//                    Toast.makeText(MainActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình quản lý sản phẩm
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = edtUsername.getText().toString().trim();
//                String password = edtPassword.getText().toString().trim();
//
//                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
//                boolean isAuthenticated = db.checkUser(username, password);
//
//                if (isAuthenticated) {
//                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                    // Chuyển đến màn hình Dashboard hoặc màn hình chính của ứng dụng
//                } else {
//                    Toast.makeText(MainActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
