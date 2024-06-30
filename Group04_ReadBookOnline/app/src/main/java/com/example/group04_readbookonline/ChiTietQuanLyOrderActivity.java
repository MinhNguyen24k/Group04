package com.example.group04_readbookonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group04_readbookonline.adapter.SuKienReturn;
import com.example.group04_readbookonline.model.OrderCRUD;
import com.example.group04_readbookonline.model.ChiTietOrder;
import com.example.group04_readbookonline.model.TaiKhoan;
import com.example.group04_readbookonline.model.TaiKhoanCRUD;

import java.util.List;

public class ChiTietQuanLyOrderActivity extends SuKienReturn {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietquanlyorder);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            int orderID = intent.getIntExtra("OrderID", -1);
            int userID = intent.getIntExtra("UserID", -1);
            String orderDate = intent.getStringExtra("OrderDate");
            String status = intent.getStringExtra("Status");

            // Hiển thị thông tin đơn hàng trong giao diện của ChiTietQLDHActivity
            TextView tvOrderId = findViewById(R.id.tvOrderId);
            TextView tvBuyerName = findViewById(R.id.tvBuyerName);
            TextView tvProductName = findViewById(R.id.tvProductName);
            TextView tvPrice = findViewById(R.id.tvPrice);
            TextView trangthai = findViewById(R.id.trangthai);

            // Hiển thị thông tin đơn hàng trong giao diện
            tvOrderId.setText("Mã đơn hàng: " + orderID);

            // Lấy tên người mua
            String buyerName = getBuyerName(userID);
            tvBuyerName.setText("Người mua: " + buyerName);

            // Hiển thị thông tin sản phẩm và giá
            displayOrderDetails(orderID);

            trangthai.setText("Trạng thái thanh toán: " + status);

            // Xử lý sự kiện khi nhấn nút "Quay Lại"
            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Đóng màn hình hiện tại và quay lại màn hình trước đó
                }
            });
        }
    }
    private void displayOrderDetails(int orderID) {
        // Khởi tạo OrderDAO để thực hiện truy vấn cơ sở dữ liệu
        OrderCRUD orderCRUD = new OrderCRUD(this);
        orderCRUD.open();

        // Lấy danh sách chi tiết đơn hàng
        List<ChiTietOrder> chiTietOrderList = orderCRUD.getChiTietOrderByOrderId(orderID);

        // Khởi tạo các biến để lưu thông tin sản phẩm và giá
        StringBuilder productNames = new StringBuilder();
        double totalPrice = 0.0;

        // Lặp qua danh sách chi tiết đơn hàng để lấy thông tin sản phẩm và giá
        for (ChiTietOrder orderDetail : chiTietOrderList) {
            int bookID = orderDetail.getBookID();
            String productName = orderCRUD.getTruyenNameById(bookID);
            double price = orderCRUD.getGiaByTruyenId(bookID);

            // Thêm thông tin sản phẩm và giá vào StringBuilder
            productNames.append(productName).append(", ");
            totalPrice += price;
        }

        // Đóng OrderDAO sau khi sử dụng xong
        orderCRUD.close();

        // Kiểm tra xem có thông tin chi tiết đơn hàng hay không
        if (productNames.length() > 0) {
            // Xóa dấu phẩy cuối cùng
            productNames.deleteCharAt(productNames.length() - 2);
        }

        // Hiển thị thông tin trong tvProductName và tvPrice
        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvPrice = findViewById(R.id.tvPrice);

        tvProductName.setText("Sản phẩm: " + productNames.toString());
        tvPrice.setText("Tổng giá: " + totalPrice);
    }


    private String getBuyerName(int userID) {
        String buyerName = "Không xác định";
        TaiKhoanCRUD taiKhoanCRUD = new TaiKhoanCRUD(this);

        try {
            taiKhoanCRUD.open();
            TaiKhoan buyer = taiKhoanCRUD.getTaiKhoanByID(userID);

            if (buyer != null) {
                buyerName = buyer.getUsername();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            taiKhoanCRUD.close();
        }

        return buyerName;
    }


}
