package com.example.group04_readbookonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group04_readbookonline.adapter.LichSuMuaAdapter;
import com.example.group04_readbookonline.adapter.doctruyen;
import com.example.group04_readbookonline.model.Order;
import com.example.group04_readbookonline.model.OrderCRUD;

import java.util.List;

public class NguoiDungActivity extends AppCompatActivity {
    private List<Order> purchaseHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoidung);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnView = findViewById(R.id.btnView);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        int userID = sharedPreferences.getInt("UserID", -1); // -1 là giá trị mặc định nếu không tìm thấy
        String username = sharedPreferences.getString("Username", "Không có tên");


        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);


        Log.d("ProfileActivity", "Username: " + username);

        displayPurchaseHistory(userID);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NguoiDungActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin người dùng từ SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Chuyển hướng đến màn hình đăng nhập
                Intent intent = new Intent(NguoiDungActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng ProfileActivity
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected orderID from the intent
                int orderID = getIntent().getIntExtra("orderID", -1);

                if (orderID != -1) {
                    // Create an intent to start the DocsachActivity
                    Intent intent = new Intent(NguoiDungActivity.this, doctruyen.class);

                    // Pass relevant information to the DocsachActivity
                    intent.putExtra("orderID", orderID);

                    startActivity(intent);
                } else {
                    // Notify the user that no order is selected
                    // For example, you can show a Toast or display a message in the UI.
                    Toast.makeText(NguoiDungActivity.this, "Bạn chưa chọn đơn hàng để xem.", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
    private void displayPurchaseHistory(int userId) {
        // Truy cập vào cơ sở dữ liệu để lấy lịch sử mua hàng của người dùng
        OrderCRUD orderCRUD = new OrderCRUD(this);
        orderCRUD.open(); // Mở cơ sở dữ liệu

        try {
            // Lấy lịch sử mua hàng từ cơ sở dữ liệu cho userId
            purchaseHistory = orderCRUD.getLichSuMuaHang(userId);

            // Hiển thị lịch sử mua hàng trong ListView
            ListView listViewPurchaseHistory = findViewById(R.id.listViewPurchaseHistory);
            LichSuMuaAdapter adapter = new LichSuMuaAdapter(this, purchaseHistory);
            listViewPurchaseHistory.setAdapter(adapter);
        } finally {
            // Đảm bảo rằng cơ sở dữ liệu được đóng ngay cả khi có ngoại lệ xảy ra
            orderCRUD.close();
        }
    }



}