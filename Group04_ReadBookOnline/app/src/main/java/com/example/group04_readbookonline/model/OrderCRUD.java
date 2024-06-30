package com.example.group04_readbookonline.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.group04_readbookonline.TruyenDBHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderCRUD {
    private SQLiteDatabase database;
    private TruyenDBHelper dbHelper;

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public OrderCRUD(Context context) {
        dbHelper = new TruyenDBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM Orders";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                int orderIdIndex = cursor.getColumnIndex("OrderID");
                int userIdIndex = cursor.getColumnIndex("UserID");
                int orderDateIndex = cursor.getColumnIndex("OrderDate");
                int statusIndex = cursor.getColumnIndex("Status");

                if (orderIdIndex >= 0) {
                    order.setOrderID(cursor.getInt(orderIdIndex));
                }

                if (userIdIndex >= 0) {
                    order.setUserID(cursor.getInt(userIdIndex));
                }

                if (orderDateIndex >= 0) {
                    order.setOrderDate(cursor.getString(orderDateIndex));
                }

                if (statusIndex >= 0) {
                    order.setStatus(cursor.getString(statusIndex));
                }

                orderList.add(order);
            }
            cursor.close();
        }

        return orderList;
    }


    public List<ChiTietOrder> getChiTietOrderByOrderId(int orderId) {
        List<ChiTietOrder> chiTietOrderDetails = new ArrayList<>();
        String query = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ChiTietOrder orderDetail = new ChiTietOrder();

                int orderDetailIdIndex = cursor.getColumnIndex("OrderDetailID");
                int bookIdIndex = cursor.getColumnIndex("BookID");
                int quantityIndex = cursor.getColumnIndex("Quantity");

                if (orderDetailIdIndex >= 0) {
                    orderDetail.setOrderDetailID(cursor.getInt(orderDetailIdIndex));
                }

                if (bookIdIndex >= 0) {
                    orderDetail.setBookID(cursor.getInt(bookIdIndex));
                }

                if (quantityIndex >= 0) {
                    orderDetail.setQuantity(cursor.getInt(quantityIndex));
                }

                chiTietOrderDetails.add(orderDetail);
            }
            cursor.close();
        }

        return chiTietOrderDetails;
    }

    public String getTruyenNameById(int bookId) {
        String bookName = "";
        String query = "SELECT Title FROM Book WHERE BookID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookId)});

        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("Title");
            if (titleIndex >= 0) {
                bookName = cursor.getString(titleIndex);
            }
            cursor.close();
        }

        return bookName;
    }

    public double getGiaByTruyenId(int bookId) {
        double price = 0.0;
        String query = "SELECT Price FROM Book WHERE BookID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookId)});

        if (cursor != null && cursor.moveToFirst()) {
            int priceIndex = cursor.getColumnIndex("Price");
            if (priceIndex >= 0) {
                price = cursor.getDouble(priceIndex);
            }
            cursor.close();
        }

        return price;
    }

    // Các phương thức thêm, xóa, cập nhật đơn hàng...
    public boolean updateOrderStatus(int orderId, String newStatus) {
        ContentValues values = new ContentValues();
        values.put("Status", newStatus);

        String whereClause = "OrderID = ?";
        String[] whereArgs = {String.valueOf(orderId)};

        return database.update("Orders", values, whereClause, whereArgs) > 0;
    }

    public void updateOrderList(List<Order> orderList) {
        orderList.clear();
        orderList.addAll(getAllOrders());
    }

    public List<Order> getLichSuMuaHang(int userID) {
        List<Order> purchaseHistory = new ArrayList<>();
        String query = "SELECT Orders.OrderID, Orders.OrderDate, Orders.Status, OrderDetails.BookID " +
                "FROM Orders " +
                "INNER JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID " +
                "WHERE Orders.UserID = ? AND Orders.Status = 'Đã thanh toán'";



        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                int orderIDIndex = cursor.getColumnIndex("OrderID");
                int orderDateIndex = cursor.getColumnIndex("OrderDate");
                int statusIndex = cursor.getColumnIndex("Status");

                if (orderIDIndex >= 0) {
                    order.setOrderID(cursor.getInt(orderIDIndex));
                }

                if (orderDateIndex >= 0) {
                    order.setOrderDate(cursor.getString(orderDateIndex));
                }

                if (statusIndex >= 0) {
                    order.setStatus(cursor.getString(statusIndex));
                }

                // Lấy thông tin sản phẩm từ Truyen
                int bookIdIndex = cursor.getColumnIndex("BookID");
                if (bookIdIndex >= 0) {
                    int bookID = cursor.getInt(bookIdIndex);
                    Truyen bookFake = getTruyenById(bookID);
                    order.setProductName(bookFake.getTitle());
                    order.setProductImageName(bookFake.getImageName());
                }

                purchaseHistory.add(order);
            }
            cursor.close();
        }

        return purchaseHistory;
    }


    // Hàm mới để lấy thông tin truyen bằng BookID
    private Truyen getTruyenById(int bookID) {
        String query = "SELECT * FROM Book WHERE BookID = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookID)});

        Truyen bookFake = new Truyen();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndex("Title");
                int imageNameIndex = cursor.getColumnIndex("ImageName");

                if (titleIndex >= 0) {
                    bookFake.setTitle(cursor.getString(titleIndex));
                }

                if (imageNameIndex >= 0) {
                    bookFake.setImageName(cursor.getString(imageNameIndex));
                }
            }

            cursor.close();
        }

        return bookFake;
    }
    public Truyen getThongTinTruyenFromOrder(int orderID) {
        Truyen bookFake = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT B.* FROM Book B " +
                "JOIN OrderDetails OD ON B.BookID = OD.BookID " +
                "JOIN Orders O ON OD.OrderID = O.OrderID " +
                "WHERE O.OrderID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderID)});

        if (cursor.moveToFirst()) {
            bookFake = new Truyen();
            // Kiểm tra xem cột có tồn tại trong kết quả truy vấn không
            int columnIndexBookID = cursor.getColumnIndex("BookID");
            if (columnIndexBookID >= 0) {
                bookFake.setBookID(cursor.getInt(columnIndexBookID));
            }

            int columnIndexCategoryID = cursor.getColumnIndex("CategoryID");
            if (columnIndexCategoryID >= 0) {
                bookFake.setCategoryID(cursor.getInt(columnIndexCategoryID));
            }

            int columnIndexTitle = cursor.getColumnIndex("Title");
            if (columnIndexTitle >= 0) {
                bookFake.setTitle(cursor.getString(columnIndexTitle));
            }

            int columnIndexPrice = cursor.getColumnIndex("Price");
            if (columnIndexPrice >= 0) {
                bookFake.setPrice(cursor.getDouble(columnIndexPrice));
            }

            int columnIndexDescription = cursor.getColumnIndex("Description");
            if (columnIndexDescription >= 0) {
                bookFake.setDescription(cursor.getString(columnIndexDescription));
            }

            int columnIndexContent = cursor.getColumnIndex("Content");
            if (columnIndexContent >= 0) {
                bookFake.setContent(cursor.getString(columnIndexContent));
                Log.d("docsach", "Content: " + bookFake.getContent());
            }


            int columnIndexImageName = cursor.getColumnIndex("ImageName");
            if (columnIndexImageName >= 0) {
                bookFake.setImageName(cursor.getString(columnIndexImageName));
            }
        }

        cursor.close();
        return bookFake;
    }

}