package com.example.group04_readbookonline.adapter;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group04_readbookonline.R;

public class SuKienReturn extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBackButton() {
        // Tìm đối tượng Button bằng ID
        View btnBack = findViewById(R.id.btnBack);

        // Thêm lắng nghe sự kiện cho nút "Quay Lại"
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi nút "Quay Lại" được nhấn
                    onBackPressed();
                }
            });
        }
    }
}

