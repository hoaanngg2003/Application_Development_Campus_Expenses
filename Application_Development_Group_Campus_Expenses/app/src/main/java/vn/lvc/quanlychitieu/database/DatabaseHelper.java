package vn.lvc.quanlychitieu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.lvc.quanlychitieu.MainActivity;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.LoaiChiDAO;
import vn.lvc.quanlychitieu.dao.LoaiThuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.dao.UserNameAttribute;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "qltc.db", null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    private SQLiteDatabase database;

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LoaiThuDAO.SQL_LOAI_THU);
        sqLiteDatabase.execSQL(ThuNhapDAO.SQL_THU_NHAP);
        sqLiteDatabase.execSQL(LoaiChiDAO.SQL_LOAI_CHI);
        sqLiteDatabase.execSQL(ChiTieuDAO.SQL_CHI_TIEU);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + LoaiThuDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + ThuNhapDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + LoaiChiDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + ChiTieuDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public static final int DATABASE_VERSION = 6;

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "userTable";
        public static final String COLUMN_USERNAME = "userName";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String PASSWORD= "password";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_USERNAME + " TEXT," +
                    UserEntry.EMAIL + " TEXT," +
                    UserEntry.PHONE_NUMBER  + " TEXT," +
                    UserEntry.PASSWORD + " TEXT)";

    public long insertUser(UserNameAttribute user){

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USERNAME, user.userName);
        values.put(UserEntry.EMAIL, user.email);
        values.put(UserEntry.PHONE_NUMBER, user.phoneNumber);
        values.put(UserEntry.PASSWORD, user.password);

        // Insert the new row, returning the primary key value of the new row
        return database.insertOrThrow(UserEntry.TABLE_NAME, null, values);
    }

    public long changeUser(UserNameAttribute user) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USERNAME, user.userName);
        values.put(UserEntry.EMAIL, user.email);
        values.put(UserEntry.PHONE_NUMBER, user.phoneNumber);
        values.put(UserEntry.PASSWORD, user.password);

        // Update the row with the new user information
        String whereClause = "username = ?";
        String[] whereArgs = {MainActivity.userName};

        return database.update(UserEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public long resetUserPass(UserNameAttribute user) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USERNAME, user.userName);
        values.put(UserEntry.EMAIL, user.email);
        values.put(UserEntry.PHONE_NUMBER, user.phoneNumber);
        values.put(UserEntry.PASSWORD, "1");

        // Update the row with the new user information
        String whereClause = "username = ?";
        String[] whereArgs = {user.userName};

        return database.update(UserEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public int deleteUser() {
        // Define the WHERE clause to specify the condition for deletion
        String whereClause = UserEntry.COLUMN_USERNAME + " = ?";

        // Define the selection arguments to replace the placeholders in the WHERE clause
        String[] whereArgs = {MainActivity.userName};

        // Execute the delete query and return the number of affected rows
        return database.delete(UserEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public long resetUserPassword(UserNameAttribute user) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USERNAME, user.userName);
        values.put(UserEntry.EMAIL, user.email);
        values.put(UserEntry.PHONE_NUMBER, user.phoneNumber);
        values.put(UserEntry.PASSWORD, user.password);

        // Update the row with the new user information
        String whereClause = "username = ?";
        String[] whereArgs = {MainActivity.userName};

        return database.update(UserEntry.TABLE_NAME, values, whereClause, whereArgs);
    }

    public List<UserNameAttribute> getAllUsers() {
        Cursor results = database.query(UserEntry.TABLE_NAME, new String[] {UserEntry._ID,UserEntry.COLUMN_USERNAME,UserEntry.EMAIL,UserEntry.PHONE_NUMBER, UserEntry.PASSWORD},
                null, null, null, null, UserEntry._ID);

        results.moveToFirst();
        List<UserNameAttribute> users = new ArrayList<>();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String userName = results.getString(1);
            String email = results.getString(2);
            String phoneNumber = results.getString(3);
            String password = results.getString(4);
            UserNameAttribute user = new UserNameAttribute();
            user.id = id;
            user.userName = userName;
            user.email = email;
            user.phoneNumber = phoneNumber;
            user.password = password;
            users.add(user);
            results.moveToNext();
        }

        return users;
    }


    public List<UserNameAttribute> getUserData() {
        // Define the selection and selectionArgs parameters
        String selection = UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { "d" };

        Cursor results = database.query(
                UserEntry.TABLE_NAME,
                new String[] {
                        UserEntry._ID,
                        UserEntry.COLUMN_USERNAME,
                        UserEntry.EMAIL,
                        UserEntry.PHONE_NUMBER,
                        UserEntry.PASSWORD
                },
                selection,
                selectionArgs,
                null,
                null,
                UserEntry._ID
        );

        // Rest of the code remains the same
        results.moveToFirst();
        List<UserNameAttribute> users = new ArrayList<>();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String userName = results.getString(1);
            String email = results.getString(2);
            String phoneNumber = results.getString(3);
            String password = results.getString(4);
            UserNameAttribute user = new UserNameAttribute();
            user.id = id;
            user.userName = userName;
            user.email = email;
            user.phoneNumber = phoneNumber;
            user.password = password;
            users.add(user);
            results.moveToNext();
        }

        return users;
    }
}
