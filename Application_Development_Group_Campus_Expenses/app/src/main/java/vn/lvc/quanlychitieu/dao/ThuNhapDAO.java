package vn.lvc.quanlychitieu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.lvc.quanlychitieu.database.DatabaseHelper;
import vn.lvc.quanlychitieu.model.ThuNhap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThuNhapDAO {
    final SQLiteDatabase db;
    final DatabaseHelper dbHelper;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static final String TABLE_NAME = "ThuNhap";
    public static final String COLUMN_MA_THU = "mathu";
    public static final String COLUMN_MA_LOAI_THU = "maloaithu";
    public static final String COLUMN_TIEN_THU = "tienthu";
    public static final String COLUMN_NGAY_THU = "ngaythu";
    public static final String COLUMN_GHI_CHU_THU = "ghichuthu";

    public static final String SQL_THU_NHAP = "Create Table " + TABLE_NAME + "("
            + COLUMN_MA_THU + " integer primary key autoincrement,"
            + COLUMN_MA_LOAI_THU + " text,"
            + COLUMN_TIEN_THU + " real,"
            + COLUMN_NGAY_THU + " date,"
            + COLUMN_GHI_CHU_THU + " text" +
            ",foreign key(maloaithu) references LoaiThu(maloaithu) on update cascade)";

    public ThuNhapDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public int insertThuNhap(String loai, double tien, Date ngay, String ghichu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU, loai);
        contentValues.put(COLUMN_TIEN_THU, tien);
        contentValues.put(COLUMN_NGAY_THU, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_THU, ghichu);

        try {
            if (db.insert(TABLE_NAME, null, contentValues) < 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    //update
    public int updateThuNhap(int ma, String loai, double tien, Date ngay, String ghichu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU, loai);
        contentValues.put(COLUMN_TIEN_THU, tien);
        contentValues.put(COLUMN_NGAY_THU, sdf.format(ngay));
        contentValues.put(COLUMN_GHI_CHU_THU, ghichu);

        try {
            if (db.update(TABLE_NAME, contentValues, COLUMN_MA_THU + " = ?", new String[]{String.valueOf(ma)}) < 0) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    //delete
    public int deleteThuNhap(int id) {
        if (db.delete(TABLE_NAME, COLUMN_MA_THU + " = ?", new String[]{String.valueOf(id)}) < 0) {
            return -1;
        }
        return 1;
    }

    //get all
    public List<ThuNhap> getAllThuNhap() {
        List<ThuNhap> thuNhapList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_NGAY_THU + " DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThuNhap thuNhap = null;
            try {
                thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        sdf.parse(cursor.getString(3)),
                        cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            thuNhapList.add(thuNhap);
            cursor.moveToNext();
        }
        cursor.close();

        return thuNhapList;
    }

    public int getIconThu(String maloai) {
        String sql = "Select " + LoaiThuDAO.COLUMN_ICON_LOAI_THU + " From " + LoaiThuDAO.TABLE_NAME
                + " Where " + LoaiThuDAO.COLUMN_MA_LOAI_THU + " = '" + maloai + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int icon = cursor.getInt(0);
        cursor.close();

        return icon;
    }

    public double getTongThuNam(String nam){
        Double tongThu = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_THU  + ") From " +TABLE_NAME +" Where strftime('%Y'," + COLUMN_NGAY_THU + ") " +
                " = '" + nam + "'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongThu = cursor.getDouble(0);
        cursor.close();
        return tongThu;
    }
    public double getTongThuThang(String nam,String thang){
        if (Integer.parseInt(thang) < 10){
            thang = "0" + thang;
        }
        Double tongThu = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_THU  + ") From " +TABLE_NAME +" Where strftime('%Y'," + COLUMN_NGAY_THU + ") " +
                " = '" + nam + "' And strftime('%m'," + COLUMN_NGAY_THU + ") = '" + thang + "'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongThu = cursor.getDouble(0);
        cursor.close();
        return tongThu;
    }
    public double getTongThuHN(String currentDay){
        Double tongThu = 0.0;
        String sql = "Select Sum(" +COLUMN_TIEN_THU  + ") From " +TABLE_NAME +" Where " + COLUMN_NGAY_THU + " = '" +  currentDay + "'";
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        tongThu = cursor.getDouble(0);
        cursor.close();
        return tongThu;
    }
    public List<ThuNhap> tim(String loai , String tien , String ngaybatdau, String ngaykethuc) {
        String sql = "";
        if (loai.equals("Tất cả")){
            sql = "Select * From ThuNhap";
            if (!tien.isEmpty()){
                sql +=" Where tienthu >= " +tien;
            }
            if (!ngaybatdau.isEmpty()){
                if (!tien.isEmpty()){
                    sql += " And ngaythu >= '" + ngaybatdau +"'";
                }else{
                    sql += " Where ngaythu >= '" + ngaybatdau + "'";
                }
            }
            if (!ngaykethuc.isEmpty()){
                if (!tien.isEmpty() || !ngaybatdau.isEmpty()) {
                    sql += " And ngaythu <= '" + ngaykethuc + "'";
                }else{
                    sql += " Where ngaythu <= '" + ngaykethuc + "'";
                }
            }
        }else{
            sql = "Select * From ThuNhap Where maloaithu = '" +loai +"'";
            if (!tien.isEmpty()){
                sql +=" And tienthu >=   " +tien;
            }
            if (!ngaybatdau.isEmpty()){
                sql += " And ngaythu >= '" + ngaybatdau +"'";
            }
            if (!ngaykethuc.isEmpty()){
                sql += " And ngaythu <= '" + ngaykethuc + "'";
            }
        }

        List<ThuNhap> thuNhapList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ThuNhap thuNhap = null;
            try {
                thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        sdf.parse(cursor.getString(3)),
                        cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            thuNhapList.add(thuNhap);
            cursor.moveToNext();
        }
        cursor.close();
        return thuNhapList;
    }
}

