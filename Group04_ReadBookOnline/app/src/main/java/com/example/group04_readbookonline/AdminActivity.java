package com.example.group04_readbookonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group04_readbookonline.adapter.AddDanhMucTruyen;
import com.example.group04_readbookonline.adapter.SuKienReturn;
import com.example.group04_readbookonline.adapter.XemTruyen;

public class AdminActivity extends SuKienReturn {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        // Lấy reference đến các nút từ layout
        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnAddCategory = findViewById(R.id.btnAddCategory);
        Button btnManageOrders = findViewById(R.id.btnManageOrders);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Thiết lập sự kiện onClick cho từng nút

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm truyện
                Intent intent = new Intent(AdminActivity.this, XemTruyen.class);
                startActivity(intent);
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm danh mục sản phẩm
                Intent intent = new Intent(AdminActivity.this, AddDanhMucTruyen.class);
                startActivity(intent);
            }
        });

        btnManageOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình quản lý đơn hàng
                Intent intent = new Intent(AdminActivity.this, QuanLyOrderActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
