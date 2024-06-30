package com.example.group04_readbookonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.group04_readbookonline.adapter.DanhSachOrderAdapter;
import com.example.group04_readbookonline.adapter.SuKienReturn;
import com.example.group04_readbookonline.model.Order;
import com.example.group04_readbookonline.model.OrderCRUD;

import java.util.List;

public class QuanLyOrderActivity extends SuKienReturn {
    private ListView listViewOrders;
    private DanhSachOrderAdapter adapter;
    private OrderCRUD orderCRUD;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyorder);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        listViewOrders = findViewById(R.id.listViewOrders);
        orderCRUD = new OrderCRUD(this);
        List<Order> orderList = orderCRUD.getAllOrders();
        adapter = new DanhSachOrderAdapter(this, orderList);
        listViewOrders.setAdapter(adapter);
        listViewOrders.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy đơn hàng được chọn và lưu vào biến selectedOrder
            selectedOrder = orderList.get(position);
        });

        Button btnChangeStatus = findViewById(R.id.btnChangeStatus);
        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    // Cập nhật trạng thái đơn hàng thành "Đã thanh toán"
                    boolean updated = orderCRUD.updateOrderStatus(selectedOrder.getOrderID(), "Đã thanh toán");

                    if (updated) {
                        // Cập nhật danh sách đơn hàng sau khi thay đổi trạng thái
                        orderCRUD.updateOrderList(orderList);

                        // Cập nhật lại giao diện ListView
                        adapter.notifyDataSetChanged();

                        // Hiển thị thông báo thành công
                        Toast.makeText(QuanLyOrderActivity.this, "Đã cập nhật trạng thái đơn hàng thành công.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Hiển thị thông báo lỗi nếu cập nhật không thành công
                        Toast.makeText(QuanLyOrderActivity.this, "Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Thông báo cho người dùng rằng họ chưa chọn đơn hàng
                    Toast.makeText(QuanLyOrderActivity.this, "Vui lòng chọn một đơn hàng để cập nhật trạng thái.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TODO: Xử lý sự kiện khi người dùng chọn một đơn hàng để xem chi tiết
        Button btnViewDetails = findViewById(R.id.btnViewDetails);
        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    // Tạo Intent để chuyển đến ChiTietQLDHActivity
                    Intent intent = new Intent(QuanLyOrderActivity.this, ChiTietQuanLyOrderActivity.class);

                    // Truyền thông tin đơn hàng sang màn hình chi tiết
                    intent.putExtra("OrderID", selectedOrder.getOrderID());
                    intent.putExtra("UserID", selectedOrder.getUserID());
                    intent.putExtra("OrderDate", selectedOrder.getOrderDate());
                    intent.putExtra("Status", selectedOrder.getStatus());

                    // Chuyển đến màn hình chi tiết
                    startActivity(intent);
                } else {
                    // Thông báo cho người dùng rằng họ chưa chọn đơn hàng
                    Toast.makeText(QuanLyOrderActivity.this, "Vui lòng chọn một đơn hàng để xem.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
