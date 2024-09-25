package vn.lvc.quanlychitieu.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.lvc.quanlychitieu.database.DatabaseHelper;
import vn.lvc.quanlychitieu.model.LoaiThu;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuDAO {
    final SQLiteDatabase db;
    final DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "LoaiThu";
    public static final String COLUMN_MA_LOAI_THU = "maloaithu";
    public static final String COLUMN_ICON_LOAI_THU = "iconloaithu";
    public static final String SQL_LOAI_THU = "Create table " + TABLE_NAME + " ("
            + COLUMN_MA_LOAI_THU + " text primary key,"
            +COLUMN_ICON_LOAI_THU + " integer)";
    public LoaiThuDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //insert
    public int insertLoaiThu(LoaiThu loaiThu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU,loaiThu.getMaLoai());
        contentValues.put(COLUMN_ICON_LOAI_THU,loaiThu.getIcon());

       try{
           if (db.insert(TABLE_NAME,null,contentValues) < 0){
               return -1;
           }
       }catch (Exception e){
           return -1;
       }
        return 1;
    }
    //get all
    public List<LoaiThu> getAllLoaiThu(){
        List<LoaiThu> loaiThuList = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select * From LoaiThu",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiThu loaiThu = new LoaiThu(cursor.getString(0),
                    cursor.getInt(1));
            loaiThuList.add(loaiThu);
            cursor.moveToNext();
        }
        cursor.close();

        return loaiThuList;
    }
    //update
    public int updateLoaiThu(LoaiThu loaiThu,String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI_THU,loaiThu.getMaLoai());
        contentValues.put(COLUMN_ICON_LOAI_THU,loaiThu.getIcon());

        try {
            if (db.update(TABLE_NAME,contentValues,COLUMN_MA_LOAI_THU + " = ? ",new String[]{id}) < 0){
                return -1;
            }
        }catch (Exception e){
            return -1;
        }
        return 1;

    }
    //delete
    public int deleteLoaiThu(String id){
        if (db.delete(TABLE_NAME,COLUMN_MA_LOAI_THU+" = ?",new String[]{id}) < 0){
            return -1;
        }
        return 1;
    }

}
