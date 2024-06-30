package com.example.group04_readbookonline.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.Truyen;
import com.example.group04_readbookonline.model.OrderCRUD;

public class doctruyen extends SuKienReturn {
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docsach);
        setupBackButton();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderID")) {
            int orderId = intent.getIntExtra("orderID", -1);

            if (orderId != -1) {
                // Gọi OrderCRUD để lấy thông tin truyện từ đơn hàng
                OrderCRUD orderCRUD = new OrderCRUD(this);
                orderCRUD.open();

                // Lấy thông tin truyện từ đơn hàng
                Truyen truyen = orderCRUD.getThongTinTruyenFromOrder(orderId);

                orderCRUD.close();

                // Hiển thị thông tin truyện trên giao diện người dùng
                displayBookInfo(truyen);
            }
        }

        // Nhận thông tin đơn hàng từ Intent

        }
    private void displayBookInfo(Truyen truyen) {
        // Sử dụng thông tin truyện để cập nhật giao diện người dùng
        // Ví dụ:
        TextView titleTextView = findViewById(R.id.tvTenTruyen);
        TextView contentTextView = findViewById(R.id.tvContent);

        if (truyen != null) {
            titleTextView.setText(truyen.getTitle());

            // Kiểm tra xem có nội dung truyện hay không
            if (truyen.getContent() != null && !truyen.getContent().isEmpty()) {
                contentTextView.setText(truyen.getContent());
                Log.d("doctruyen", "Content set successfully: " + truyen.getContent());
            } else {
                contentTextView.setText("Nội dung truyện không có sẵn.");
                Log.d("doctruyen", "No content available.");
            }
        } else {
            titleTextView.setText("Không tìm thấy thông tin truyện");
            contentTextView.setText("");
        }
    }


}


