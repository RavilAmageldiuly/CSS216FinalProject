package ravil.amangeldiuly.example.simpleregisterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTheme extends SQLiteOpenHelper {

    public static final String DBNAME = "Theme.db";

    public DBTheme(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table theme(id INT PRIMARY KEY, currentTheme TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists theme");
    }

    public void insertData(String theme) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 0);
        contentValues.put("currentTheme", theme);
        long result = myDb.insert("theme", null, contentValues);
    }

    public void updateTheme(String theme) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("currentTheme", theme);
        myDb.update("theme", contentValues, "id = 0", null);
    }

    public String checkTheme() {
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor cursor = myDb.rawQuery("select currentTheme from theme where id = 0", null);
        String data = "";
        while (cursor.moveToNext()) {
            data = cursor.getString(0);
        }
        cursor.close();
        return data;
    }
}
