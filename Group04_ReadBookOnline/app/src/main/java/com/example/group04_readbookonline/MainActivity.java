package com.example.group04_readbookonline;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

//import viewpaper2, Handler, Runnable
import android.os.Handler;
import android.os.Looper;
import androidx.viewpager2.widget.ViewPager2;




import androidx.appcompat.app.AppCompatActivity;

import com.example.group04_readbookonline.adapter.ChiTietsTruyenAdapter;
import com.example.group04_readbookonline.model.TruyenCRUD;
import com.example.group04_readbookonline.model.Truyen;
import com.example.group04_readbookonline.model.CategoryCRUD;


//importAdsPagerAdapter
import com.example.group04_readbookonline.adapter.AdsPagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Truyen> productList;
    private ChiTietsTruyenAdapter ChiTietsTruyenAdapter;
    private List<String> categories;
    private CategoryCRUD categoryCRUD;
    private EditText editTextSearch;
    private Spinner spinnerCategory;


    // Các biến khác...
    private ViewPager2 viewPagerAds;
    private Handler sliderHandler;
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnProfile = findViewById(R.id.btnProfile);
        editTextSearch = findViewById(R.id.editTextSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);


        // Các khởi tạo khác...
        viewPagerAds = findViewById(R.id.viewPagerAds);

        // Danh sách các hình ảnh quảng cáo (đặt các resource ID hình ảnh tại đây)
        List<Integer> imageResIds = new ArrayList<>();
        imageResIds.add(R.drawable.giongto);
        imageResIds.add(R.drawable.doraemon);
        imageResIds.add(R.drawable.chipheo);




        // Tạo và thiết lập AdsPagerAdapter
        AdsPagerAdapter adsPagerAdapter = new AdsPagerAdapter(this, imageResIds);
        viewPagerAds.setAdapter(adsPagerAdapter);
        // Tạo Handler và Runnable để tự động chuyển hình ảnh
        sliderHandler = new Handler(Looper.getMainLooper());
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPagerAds.getCurrentItem();
                int nextItem = (currentItem + 1) % imageResIds.size();
                viewPagerAds.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 2000);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 2000);




        // Khởi tạo CategoryCRUD
        categoryCRUD = new CategoryCRUD(this);

        // Lấy danh sách danh mục từ CategoryCRUD
        categories = categoryCRUD.getAllDanhMucWithAllOption();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(spinnerAdapter);

        // Xử lý sự kiện khi chọn một danh mục từ Spinner
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                if ("Tất cả".equals(selectedCategory)) {
                    // Hiển thị tất cả truyện
                    updateListView(getBooksFromSQLite());
                } else {
                    // Lấy danh sách sản phẩm theo danh mục được chọn
                    int categoryId = categoryCRUD.getCategoryIdByName(selectedCategory);
                    List<Truyen> filteredBookFakes = getBooksByCategory(categoryId);
                    // Cập nhật ListView với danh sách truyện mới
                    updateListView(filteredBookFakes);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        productList = getBooksFromSQLite();
        ChiTietsTruyenAdapter = new ChiTietsTruyenAdapter(this, productList);
        ListView listView = findViewById(R.id.DSSach);
        listView.setAdapter(ChiTietsTruyenAdapter);




        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on the search text
                if (ChiTietsTruyenAdapter != null) {
                    ChiTietsTruyenAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text changes
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateListView(List<Truyen> filteredBookFakes) {
        ChiTietsTruyenAdapter.clear();
        ChiTietsTruyenAdapter.addAll(filteredBookFakes);
        ChiTietsTruyenAdapter.notifyDataSetChanged();
    }

    // Phương thức để lấy danh sách sản phẩm theo danh mục
    private List<Truyen> getBooksByCategory(int categoryId) {

        List<Truyen> truyen = new ArrayList<>();
        TruyenCRUD truyenCRUD = new TruyenCRUD(this);


        truyenCRUD.open();

        // Lấy danh sách sách từ SQLite
        truyen = truyenCRUD.getTruyenByCategory(categoryId);

        // Đóng cơ sở dữ liệu
        truyenCRUD.close();

        return truyen;
    }

    // Phương thức để lấy danh sách tất cả sách từ SQLite
    private List<Truyen> getBooksFromSQLite() {
        List<Truyen> truyen = new ArrayList<>();
        TruyenCRUD truyenCRUD = new TruyenCRUD(this);

        // Mở cơ sở dữ liệu
        truyenCRUD.open();

        // Lấy danh sách sách từ SQLite
        truyen = truyenCRUD.getAllTruyen();

        // Đóng cơ sở dữ liệu
        truyenCRUD.close();

        return truyen;
    }
}