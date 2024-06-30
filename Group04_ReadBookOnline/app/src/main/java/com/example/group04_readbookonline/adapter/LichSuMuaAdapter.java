package com.example.group04_readbookonline.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group04_readbookonline.NguoiDungActivity;
import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.Order;

import java.util.List;

public class LichSuMuaAdapter extends BaseAdapter {
    private Context context;
    private List<Order> purchaseHistory;
    private Order selectedOrder;

    public LichSuMuaAdapter(Context context, List<Order> purchaseHistory) {
        this.context = context;
        this.purchaseHistory = purchaseHistory;
    }

    @Override
    public int getCount() {
        return purchaseHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return purchaseHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.purchase_history_item, null);
        }

        // Lấy đối tượng Order tại vị trí position
        Order order = purchaseHistory.get(position);

        // Hiển thị thông tin của Order trong View


        TextView tvProductName = view.findViewById(R.id.tvProductName);
        ImageView imgProduct = view.findViewById(R.id.imgProduct);



        tvProductName.setText("Tên sản phẩm: " + order.getProductName());

        int resourceId = context.getResources().getIdentifier(order.getProductImageName(), "drawable", context.getPackageName());
        // Hiển thị ảnh
        imgProduct.setImageResource(resourceId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected order
                Order selectedOrder = purchaseHistory.get(position);

                // Create an intent to start the NgươiDungActivity
                Intent intent = new Intent(context, NguoiDungActivity.class);

                // Pass relevant information to the NgườiDungActivity
                intent.putExtra("orderID", selectedOrder.getOrderID());

                context.startActivity(intent);
            }
        });

        return view;
    }

}