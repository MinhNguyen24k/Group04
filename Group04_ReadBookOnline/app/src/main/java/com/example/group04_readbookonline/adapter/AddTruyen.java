package com.example.group04_readbookonline.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.group04_readbookonline.R;
import com.example.group04_readbookonline.model.TruyenCRUD;
import com.example.group04_readbookonline.model.Truyen;
import com.example.group04_readbookonline.model.CategoryCRUD;

import java.util.List;

public class AddTruyen extends SuKienReturn {
    private EditText edtImageName, edtProductName, edtProductPrice, edtProductGioiThieu, edtBookContent;
    private Button btnAddProduct, btnBack;

    private Truyen newBookFake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtruyen);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductGioiThieu = findViewById(R.id.edtProductGioiThieu);
        edtBookContent = findViewById(R.id.edtBookContent);
        edtImageName = findViewById(R.id.edtImageName);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnBack = findViewById(R.id.btnBack);

        Spinner spinnerCategories = findViewById(R.id.spinnerCategories);

        //them danh muc vao spinner
        // Trong onCreate của Activity
        CategoryCRUD categoryCRUD = new CategoryCRUD(this);
        List<String> categories = categoryCRUD.getAllCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);




        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productName = edtProductName.getText().toString();
                double productPrice = Double.parseDouble(edtProductPrice.getText().toString());
                String productGioiThieu = edtProductGioiThieu.getText().toString();
                String bookContent = edtBookContent.getText().toString();
                String imageName = edtImageName.getText().toString();


                String selectedCategory = spinnerCategories.getSelectedItem().toString();
                int categoryID = categoryCRUD.getCategoryIdByName(selectedCategory);


                if (categoryID == -1) {
                    Toast.makeText(AddTruyen.this, "Lỗi: Không tìm thấy CategoryID cho danh mục đã chọn", Toast.LENGTH_SHORT).show();
                    return;
                }


                newBookFake = new Truyen();
                newBookFake.setCategoryID(categoryID);
                newBookFake.setTitle(productName);
                newBookFake.setPrice(productPrice);
                newBookFake.setDescription(productGioiThieu);
                newBookFake.setContent(bookContent);
                newBookFake.setImageName(imageName);

                TruyenCRUD bookDAOFake = new TruyenCRUD(AddTruyen.this);
                long result = bookDAOFake.addTruyen(newBookFake);
                if (result != -1) {
                    Toast.makeText(AddTruyen.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTruyen.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }









}

