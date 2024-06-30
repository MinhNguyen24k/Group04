package com.example.group04_readbookonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.Order;
import com.example.group04_readbookonline.model.OrderCRUD;
import com.example.group04_readbookonline.model.ChiTietOrder;

import java.util.List;

public class DanhSachOrderAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orderList;

    public DanhSachOrderAdapter(Context context, List<Order> orderList) {
        super(context, 0, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        }

        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        Order order = orderList.get(position);

        tvOrderId.setText("Mã đơn hàng: " + order.getOrderID());

        // Lấy danh sách chi tiết đơn hàng
        OrderCRUD orderCRUD = new OrderCRUD(context);
        List<ChiTietOrder> chiTietOrderDetails = orderCRUD.getChiTietOrderByOrderId(order.getOrderID());

        // Hiển thị tên sản phẩm và trạng thái của đơn hàng
        StringBuilder productNames = new StringBuilder();
        for (ChiTietOrder orderDetail : chiTietOrderDetails) {
            // Lấy thông tin sách từ bảng Book
            String productName = orderCRUD.getTruyenNameById(orderDetail.getBookID());
            productNames.append(productName).append(", ");
        }
        if (productNames.length() > 0) {
            // Xóa dấu phẩy cuối cùng
            productNames.deleteCharAt(productNames.length() - 2);
        }
        tvProductName.setText("Sản phẩm: " + productNames.toString());
        tvStatus.setText("Trạng thái: " + order.getStatus());

        return convertView;
    }
}
