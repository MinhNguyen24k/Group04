package com.example.group04_readbookonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class TruyenAdapter extends ArrayAdapter<Truyen> {
    private Context context;
    private List<Truyen> bookFakeList;
    private List<Truyen> filteredList;
    public TruyenAdapter(Context context, List<Truyen> bookFakeList) {
        super(context, 0, bookFakeList);
        this.context = context;
        this.bookFakeList = bookFakeList;
        this.filteredList = new ArrayList<>(bookFakeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        }

        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);

        Truyen bookFake = bookFakeList.get(position);

        // Hiển thị tên sách
        tvProductName.setText(bookFake.getTitle());

        // Hiển thị ảnh sách từ resources
        int resourceId = context.getResources().getIdentifier(bookFake.getImageName(), "drawable", context.getPackageName());
        imgProduct.setImageResource(resourceId);




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
                    filteredList.addAll(bookFakeList);
                } else {
                    for (Truyen bookFake : bookFakeList) {
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