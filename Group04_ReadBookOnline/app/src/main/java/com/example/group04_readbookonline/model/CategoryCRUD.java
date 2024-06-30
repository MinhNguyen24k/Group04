package com.example.group04_readbookonline.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.group04_readbookonline.TruyenDBHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryCRUD {
    private TruyenDBHelper dbHelper;
    private SQLiteDatabase database;
    public CategoryCRUD(Context context) {
        dbHelper = new TruyenDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public List<Truyen> getTruyenByCategory(int categoryId) {
        List<Truyen> bookFakes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Book WHERE CategoryID = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Truyen bookFake = new Truyen();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return bookFakes;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(columnIndex);
                        categories.add(categoryName);
                    } while (cursor.moveToNext());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return categories;
    }
    public List<String> getAllDanhMucWithAllOption() {
        List<String> categories = new ArrayList<>();
        categories.add("Tất cả"); // Thêm mục "Tất cả" vào đầu danh sách
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(columnIndex);
                        categories.add(categoryName);
                    } while (cursor.moveToNext());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return categories;
    }

    public int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryID FROM Category WHERE CategoryName = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{categoryName});
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("CategoryID");
                if (columnIndex != -1) {
                    return cursor.getInt(columnIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return -1; // Trả về -1 nếu không tìm thấy CategoryID
    }
    public boolean deleteDanhMuc(String categoryName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows = db.delete("Category", "CategoryName=?", new String[]{categoryName});
        db.close();
        return affectedRows > 0;
    }

    public String getTenDanhMucById(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category WHERE CategoryID = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1) {
                    return cursor.getString(columnIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy tên danh mục
    }

}