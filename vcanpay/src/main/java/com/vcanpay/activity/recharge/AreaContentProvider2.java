package com.vcanpay.activity.recharge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.vcanpay.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by patrick wai on 2015/6/23.
 */
public class AreaContentProvider2 {
    private static final String TAG = "AreaContentProvider2";

    private static final String DB_NAME = "vcp.db";
    private static final String TABLE_NAME = "thai_area";

    private static final String CREATE_TABLE_SQL = "" +
            "CREATE TABLE " + TABLE_NAME + "(" +
            Area.AREA_ID + " INTEGER, " +
            Area.AREA_NAME + " TEXT, " +
            Area.PARENT_ID + " INTEGER)";

    static DatabaseHelper dbHelper;

    public AreaContentProvider2(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insert(ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor queryAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        return c;
    }

    public Cursor queryByParentId(int parentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.rawQuery("select * from " + TABLE_NAME+ " where parentId=?", new String[]{parentId+""});
    }

    public Cursor queryById(int id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where areaId=?", new String[]{id+""});
    }


    public class DatabaseHelper extends SQLiteOpenHelper {

        Context context;

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SQL);

            Log.d(TAG, "table created");

            init(context, db);
        }

        public void init(Context context, SQLiteDatabase db) {

            Log.d(TAG, "begin init data...");
            try {
                InputStream is = context.getResources().openRawResource(R.raw.area);

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String sql = null;
                while ((sql = reader.readLine()) != null) {
                    if (!TextUtils.isEmpty(sql)) {
                        db.execSQL(sql);
                        Log.i(TAG, "sql flushed: " + sql);
                    }
                }
                reader.close();
                is.close();

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
