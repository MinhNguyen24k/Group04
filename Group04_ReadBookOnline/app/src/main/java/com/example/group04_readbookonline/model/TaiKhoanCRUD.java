package com.example.group04_readbookonline.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.group04_readbookonline.TruyenDBHelper;

public class TaiKhoanCRUD {
    private SQLiteDatabase database;
    private TruyenDBHelper dbHelper;

    public TaiKhoanCRUD(Context context) {
        dbHelper = new TruyenDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addTaiKhoan(TaiKhoan taiKhoan) {
        ContentValues values = new ContentValues();
        values.put("Username", taiKhoan.getUsername());
        values.put("Password", taiKhoan.getPassword());
        values.put("RoleID", taiKhoan.getRoleID());

        return database.insert("Users", null, values);
    }

    public TaiKhoan getTaiKhoan(String username, String password) {
        String[] columns = {"UserID", "Username", "RoleID"};
        String selection = "Username = ? AND Password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = database.query("Users", columns, selection, selectionArgs, null, null, null);

        TaiKhoan taiKhoan = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIDIndex = cursor.getColumnIndex("UserID");
                int usernameIndex = cursor.getColumnIndex("Username");
                int roleIDIndex = cursor.getColumnIndex("RoleID");

                // Kiểm tra xem các cột có tồn tại trong kết quả trả về hay không
                if (userIDIndex != -1 && usernameIndex != -1 && roleIDIndex != -1) {
                    taiKhoan = new TaiKhoan();
                    taiKhoan.setUserID(cursor.getInt(userIDIndex));
                    taiKhoan.setUsername(cursor.getString(usernameIndex));
                    taiKhoan.setRoleID(cursor.getInt(roleIDIndex));

                    // Log giá trị Username tại đây
                    Log.d("UserDAO", "User's Username: " + taiKhoan.getUsername());
                }
            }
            cursor.close();
        }

        return taiKhoan;
    }
    public TaiKhoan getTaiKhoanByID(int userID) {
        TaiKhoan taiKhoan = null;
        String query = "SELECT * FROM Users WHERE UserID = ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});

            if (cursor != null && cursor.moveToFirst()) {
                int userIdIndex = cursor.getColumnIndex("UserID");
                int usernameIndex = cursor.getColumnIndex("Username");

                taiKhoan = new TaiKhoan();

                if (userIdIndex >= 0) {
                    taiKhoan.setUserID(cursor.getInt(userIdIndex));
                }

                if (usernameIndex >= 0) {
                    taiKhoan.setUsername(cursor.getString(usernameIndex));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return taiKhoan;
    }


}