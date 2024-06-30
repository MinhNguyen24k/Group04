package com.example.group04_readbookonline.adapter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.TruyenDBHelper;
import com.example.group04_readbookonline.model.CategoryCRUD;

import java.util.List;

public class AddDanhMucTruyen extends SuKienReturn {

    private EditText edtCategoryName;
    private Button btnAddProduct;

    private TruyenDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddanhmuctruyen);

        setupBackButton();

        edtCategoryName = findViewById(R.id.edtCategoryName);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        dbHelper = new TruyenDBHelper(this);


        Spinner spinnerExistingCategories = findViewById(R.id.spinnerExistingCategories);
        Button btnDeleteCategory = findViewById(R.id.btnDeleteCategory);

// Lấy danh sách danh mục từ cơ sở dữ liệu
        CategoryCRUD categoryCRUD = new CategoryCRUD(this);
        List<String> existingCategories = categoryCRUD.getAllCategories();

// Tạo ArrayAdapter để hiển thị danh sách danh mục trong Spinner
        ArrayAdapter<String> existingCategoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, existingCategories);
        existingCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExistingCategories.setAdapter(existingCategoriesAdapter);

// Thiết lập sự kiện khi chọn một danh mục từ Spinner
        spinnerExistingCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = existingCategories.get(position);
                // TODO: Xử lý khi chọn một danh mục từ Spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không cần xử lý gì khi không chọn gì cả
            }
        });

// Thiết lập sự kiện khi ấn nút Xóa Danh Mục
        btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = spinnerExistingCategories.getSelectedItem().toString();

                if (categoryCRUD.deleteDanhMuc(selectedCategory)) {
                    // Xóa thành công, cập nhật lại danh sách danh mục trong Spinner
                    existingCategories.remove(selectedCategory);

                    // Thông báo cho adapter về sự thay đổi
                    existingCategoriesAdapter.notifyDataSetChanged();

                    // Hiển thị thông báo xóa thành công
                    Toast.makeText(AddDanhMucTruyen.this, "Xóa danh mục thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddDanhMucTruyen.this, "Xóa danh mục thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String categoryName = edtCategoryName.getText().toString();

                // Thêm danh mục vào cơ sở dữ liệu
                addCategory(categoryName);
            }
        });
    }

    private void addCategory(String categoryName) {
        // Mở cơ sở dữ liệu để ghi
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tạo đối tượng ContentValues để chứa dữ liệu
        ContentValues values = new ContentValues();
        values.put("CategoryName", categoryName);

        // Thực hiện thêm dữ liệu
        long newRowId = db.insert("Category", null, values);

        // Đóng cơ sở dữ liệu
        db.close();

        // Hiển thị thông báo
        if (newRowId != -1) {
            Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}


