package ravil.amangeldiuly.example.simpleregisterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(userMail TEXT PRIMARY KEY, password TEXT, dateOfAccountCreation TEXT, username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop table if exists users");
    }

    public Boolean insertData(String userMail, String password, String creationDate, String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userMail", userMail);
        contentValues.put("password", password);
        contentValues.put("dateOfAccountCreation", creationDate);
        contentValues.put("username", username);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkUserMail(String userMail) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where userMail = ?", new String[] {userMail});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String checkUserPassword(String userMail) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select password from users where userMail = ?", new String[] {userMail});
        String data = "";
        while (cursor.moveToNext()) {
            data = cursor.getString(0);
        }
        cursor.close();
        return data;
    }

    public String getDateOfRegistration(String userMail) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where userMail = ?", new String[] {userMail});
        String data = "";
        while (cursor.moveToNext()) {
            data = cursor.getString(2);
        }
        cursor.close();
        return data;
    }

    public String getUsername(String userMail) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where userMail = ?", new String[] {userMail});
        String data = "";
        while (cursor.moveToNext()) {
            data = cursor.getString(3);
        }
        cursor.close();
        return data;
    }
}
