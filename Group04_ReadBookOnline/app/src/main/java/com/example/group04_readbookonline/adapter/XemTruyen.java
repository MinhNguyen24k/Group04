package com.example.group04_readbookonline.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.TruyenCRUD;
import com.example.group04_readbookonline.model.Truyen;

import java.util.List;

    public class XemTruyen extends SuKienReturn {
        private ListView listViewProducts;
        private List<Truyen> truyenList;
        private TruyenCRUD truyenCRUD;
        private TruyenAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_xemtruyen);

            // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
            setupBackButton();

            listViewProducts = findViewById(R.id.listViewProducts);
            truyenCRUD = new TruyenCRUD(this);
            truyenList = truyenCRUD.getAllTruyen();
            Button btnThemSP = findViewById(R.id.btnThemSP);
            adapter = new TruyenAdapter(this, truyenList);
            listViewProducts.setAdapter(adapter);

            // Xử lý sự kiện khi người dùng nhấp vào một sách trong danh sách
            listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
                // Lấy sách được chọn
                Truyen selectedBookFake = truyenList.get(position);
                // TODO: Xử lý logic khi sách được chọn
            });
            btnThemSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển đến màn hình thêm danh mục sản phẩm
                    Intent intent = new Intent(XemTruyen.this, AddTruyen.class);
                    startActivity(intent);
                }
            });
            // TODO: Thêm xử lý cho nút xóa và nút sửa
            Button btnDeleteSP = findViewById(R.id.btnDeleteSP);
            btnDeleteSP.setOnClickListener(v -> {
                // Lấy vị trí của sách được chọn
                int selectedPosition = listViewProducts.getCheckedItemPosition();
                if (selectedPosition != ListView.INVALID_POSITION) {
                    // Lấy sách được chọn
                    Truyen selectedBookFake = truyenList.get(selectedPosition);
                    // Xóa sách từ cơ sở dữ liệu
                    if (truyenCRUD.deleteTruyen(selectedBookFake.getBookID())) {
                        // Xóa thành công, cập nhật lại danh sách và thông báo
                        truyenList.remove(selectedBookFake);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(XemTruyen.this, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(XemTruyen.this, "Xóa sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(XemTruyen.this, "Vui lòng chọn một sách để xóa", Toast.LENGTH_SHORT).show();
                }
            });

            Button btnChangeSP = findViewById(R.id.btnChangeSP);
            btnChangeSP.setOnClickListener(v -> {
                int selectedPosition = listViewProducts.getCheckedItemPosition();
                if (selectedPosition != ListView.INVALID_POSITION) {
                    Truyen selectedBookFake = truyenList.get(selectedPosition);
                    Intent intent = new Intent(XemTruyen.this, SuaTruyen.class);
                    intent.putExtra("bookID", selectedBookFake.getBookID());
                    startActivity(intent);
                } else {
                    Toast.makeText(XemTruyen.this, "Vui lòng chọn một sách để sửa", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
