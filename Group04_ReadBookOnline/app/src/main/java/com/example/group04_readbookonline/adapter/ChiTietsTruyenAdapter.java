package com.example.group04_readbookonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group04_readbookonline.ChiTietTruyenActivity;
import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class ChiTietsTruyenAdapter extends ArrayAdapter<Truyen> {
    private Context context;
    private List<Truyen> truyenList;
    private List<Truyen> filteredList;
    public ChiTietsTruyenAdapter(Context context, List<Truyen> truyenList) {
        super(context, 0, truyenList);
        this.context = context;
        this.truyenList = truyenList;
        this.filteredList = new ArrayList<>(truyenList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        }

        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);

        Truyen bookFake = truyenList.get(position);

        // Hiển thị tên sách
        tvProductName.setText(bookFake.getTitle());

        // Hiển thị ảnh sách từ resources
        int resourceId = context.getResources().getIdentifier(bookFake.getImageName(), "drawable", context.getPackageName());
        imgProduct.setImageResource(resourceId);

        // Xử lý sự kiện khi click vào item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang trang chi tiết sách
                Intent intent = new Intent(context, ChiTietTruyenActivity.class);
                intent.putExtra("BookID", bookFake.getBookID());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                filteredList.clear();

                if (filterPattern.isEmpty()) {
                    // Nếu filterPattern rỗng, hiển thị tất cả sản phẩm
                    filteredList.addAll(truyenList);
                } else {
                    for (Truyen bookFake : truyenList) {
                        if (bookFake.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(bookFake);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                clear();
                addAll((List<Truyen>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}