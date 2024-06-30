package com.example.group04_readbookonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group04_readbookonline.adapter.SuKienReturn;
import com.example.group04_readbookonline.model.TruyenCRUD;
import com.example.group04_readbookonline.model.Truyen;
import com.example.group04_readbookonline.model.CategoryCRUD;

public class ChiTietTruyenActivity extends SuKienReturn {
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettruyen);
        btnBack = findViewById(R.id.btnBack);
        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int bookID = intent.getIntExtra("BookID", -1);

        TruyenCRUD bookDAOFake = new TruyenCRUD(this);
        bookDAOFake.open();
        Truyen bookFake = bookDAOFake.getTruyenByID(bookID);
        bookDAOFake.close();

        if (bookFake != null) {
            // Hiển thị thông tin lên giao diện
            TextView tvTenTruyen = findViewById(R.id.tvTenTruyen);
            TextView tvDanhMuc = findViewById(R.id.tvDanhMuc);
            TextView tvGia = findViewById(R.id.tvGia);
            TextView tvGioiThieu = findViewById(R.id.tvGioiThieu);
            ImageView imgTruyen = findViewById(R.id.imgTruyen);

            tvTenTruyen.setText(bookFake.getTitle());

            // Sử dụng CategoryDAO để lấy tên danh mục
            CategoryCRUD categoryCRUD = new CategoryCRUD(this);
            categoryCRUD.open();
            String categoryName = categoryCRUD.getTenDanhMucById(bookFake.getCategoryID());
            categoryCRUD.close();
            tvDanhMuc.setText("Danh Mục: " + categoryName);

            tvGia.setText("Giá: " + String.valueOf(bookFake.getPrice()));
            tvGioiThieu.setText("Giới Thiệu: " + bookFake.getDescription());

            int resourceId = getResources().getIdentifier(bookFake.getImageName(), "drawable", getPackageName());
            imgTruyen.setImageResource(resourceId);
        }




        Button btnMua = findViewById(R.id.btnMua);
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sách từ Intent
                Intent intent = getIntent();
                int bookID = intent.getIntExtra("BookID", -1);

                // Lấy thông tin người dùng từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int userID = sharedPreferences.getInt("UserID", -1);

                // Khai báo biến bookDAO một lần
                TruyenCRUD bookDAOFake = new TruyenCRUD(ChiTietTruyenActivity.this);

                if (bookID != -1 && userID != -1) {
                    // Lấy giá sách từ cơ sở dữ liệu
                    bookDAOFake.open();
                    Truyen bookFake = bookDAOFake.getTruyenByID(bookID);
                    if (bookFake != null) {
                        // Thêm thông tin đơn hàng và chi tiết đơn hàng vào SQLite
                        long orderID = bookDAOFake.addOrder(userID);  // Thêm đơn hàng và lấy OrderID
                        if (orderID != -1) {
                            // Thêm chi tiết đơn hàng với giá của sách
                            double price = bookFake.getPrice();
                            bookDAOFake.addChiTietOrder(orderID, bookID, price);

                            // Hiển thị thông báo mua sách thành công
                            Toast.makeText(ChiTietTruyenActivity.this, "Mua sách thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Hiển thị thông báo lỗi khi thêm đơn hàng
                            Toast.makeText(ChiTietTruyenActivity.this, "Có lỗi xảy ra khi mua sách. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Hiển thị thông báo lỗi khi không tìm thấy sách
                        Toast.makeText(ChiTietTruyenActivity.this, "Không tìm thấy sách. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }

                    bookDAOFake.close();
                }
            }
        });










    }
}