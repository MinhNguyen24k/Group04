package com.example.group04_readbookonline.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.group04_readbookonline.TruyenDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TruyenCRUD {
    private SQLiteDatabase database;
    private TruyenDBHelper dbHelper;

    public TruyenCRUD(Context context) {
        dbHelper = new TruyenDBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addTruyen(Truyen bookFake) {
        ContentValues values = new ContentValues();
        values.put("CategoryID", bookFake.getCategoryID());
        values.put("Title", bookFake.getTitle());
        values.put("Price", bookFake.getPrice());
        values.put("Description", bookFake.getDescription());
        values.put("Content", bookFake.getContent());
        values.put("ImageName", bookFake.getImageName());  // Thêm tên ảnh

        return database.insert("Book", null, values);
    }

    public List<Truyen> getAllTruyen() {
        List<Truyen> bookFakeList = new ArrayList<>();
        String query = "SELECT * FROM Book";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Truyen bookFake = new Truyen();

                // Kiểm tra xem tên cột có tồn tại trong kết quả truy vấn hay không
                int bookIdIndex = cursor.getColumnIndex("BookID");
                int categoryIdIndex = cursor.getColumnIndex("CategoryID");
                int titleIndex = cursor.getColumnIndex("Title");
                int priceIndex = cursor.getColumnIndex("Price");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int contentIndex = cursor.getColumnIndex("Content");
                int imageNameIndex = cursor.getColumnIndex("ImageName");

                if (bookIdIndex >= 0) {
                    bookFake.setBookID(cursor.getInt(bookIdIndex));
                }

                if (categoryIdIndex >= 0) {
                    bookFake.setCategoryID(cursor.getInt(categoryIdIndex));
                }

                if (titleIndex >= 0) {
                    bookFake.setTitle(cursor.getString(titleIndex));
                }

                if (priceIndex >= 0) {
                    bookFake.setPrice(cursor.getDouble(priceIndex));
                }

                if (descriptionIndex >= 0) {
                    bookFake.setDescription(cursor.getString(descriptionIndex));
                }

                if (contentIndex >= 0) {
                    bookFake.setContent(cursor.getString(contentIndex));
                }

                if (imageNameIndex >= 0) {
                    bookFake.setImageName(cursor.getString(imageNameIndex));
                }

                bookFakeList.add(bookFake);
            }
            cursor.close();
        }

        return bookFakeList;
    }
    public boolean deleteTruyen(int bookID) {
        return database.delete("Book", "BookID=?", new String[]{String.valueOf(bookID)}) > 0;
    }

    public boolean updateTruyen(Truyen bookFake) {
        ContentValues values = new ContentValues();
        values.put("CategoryID", bookFake.getCategoryID());
        values.put("Title", bookFake.getTitle());
        values.put("Price", bookFake.getPrice());
        values.put("Description", bookFake.getDescription());
        values.put("Content", bookFake.getContent());
        values.put("ImageName", bookFake.getImageName());

        return database.update("Book", values, "BookID=?", new String[]{String.valueOf(bookFake.getBookID())}) > 0;
    }
    public Truyen getTruyenByID(int bookID) {
        Truyen bookFake = null;
        String query = "SELECT * FROM Book WHERE BookID = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookID)});

        if (cursor != null && cursor.moveToFirst()) {
            // Kiểm tra xem tên cột có tồn tại trong kết quả truy vấn hay không
            int bookIdIndex = cursor.getColumnIndex("BookID");
            int categoryIdIndex = cursor.getColumnIndex("CategoryID");
            int titleIndex = cursor.getColumnIndex("Title");
            int priceIndex = cursor.getColumnIndex("Price");
            int descriptionIndex = cursor.getColumnIndex("Description");
            int contentIndex = cursor.getColumnIndex("Content");
            int imageNameIndex = cursor.getColumnIndex("ImageName");

            bookFake = new Truyen();

            if (bookIdIndex >= 0) {
                bookFake.setBookID(cursor.getInt(bookIdIndex));
            }

            if (categoryIdIndex >= 0) {
                bookFake.setCategoryID(cursor.getInt(categoryIdIndex));
            }

            if (titleIndex >= 0) {
                bookFake.setTitle(cursor.getString(titleIndex));
            }

            if (priceIndex >= 0) {
                bookFake.setPrice(cursor.getDouble(priceIndex));
            }

            if (descriptionIndex >= 0) {
                bookFake.setDescription(cursor.getString(descriptionIndex));
            }

            if (contentIndex >= 0) {
                bookFake.setContent(cursor.getString(contentIndex));
            }

            if (imageNameIndex >= 0) {
                bookFake.setImageName(cursor.getString(imageNameIndex));
            }

            cursor.close();
        }

        return bookFake;
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
                    // Lấy thông tin từ Cursor và thêm vào danh sách books
                    int bookIdIndex = cursor.getColumnIndex("BookID");
                    int titleIndex = cursor.getColumnIndex("Title");
                    int priceIndex = cursor.getColumnIndex("Price");
                    int descriptionIndex = cursor.getColumnIndex("Description");
                    int contentIndex = cursor.getColumnIndex("Content");
                    int imageNameIndex = cursor.getColumnIndex("ImageName");

                    // Kiểm tra xem các cột có tồn tại trong kết quả truy vấn hay không
                    if (bookIdIndex >= 0) {
                        bookFake.setBookID(cursor.getInt(bookIdIndex));
                    }

                    if (titleIndex >= 0) {
                        bookFake.setTitle(cursor.getString(titleIndex));
                    }

                    if (priceIndex >= 0) {
                        bookFake.setPrice(cursor.getDouble(priceIndex));
                    }

                    if (descriptionIndex >= 0) {
                        bookFake.setDescription(cursor.getString(descriptionIndex));
                    }

                    if (contentIndex >= 0) {
                        bookFake.setContent(cursor.getString(contentIndex));
                    }

                    if (imageNameIndex >= 0) {
                        bookFake.setImageName(cursor.getString(imageNameIndex));
                    }

                    bookFakes.add(bookFake);
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

    public String getCurrentDate() {
        // Lấy ngày hiện tại theo định dạng yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    public long addOrder(int userID) {
        ContentValues values = new ContentValues();
        values.put("UserID", userID);
        values.put("OrderDate", getCurrentDate());
        values.put("Status", "Chưa thanh toán");

        return database.insert("Orders", null, values);
    }

    public long addChiTietOrder(long orderID, int bookID, double price) {
        ContentValues values = new ContentValues();
        values.put("OrderID", orderID);
        values.put("BookID", bookID);
        values.put("Price", price);  // Sửa lại tên cột

        return database.insert("OrderDetails", null, values);
    }


}