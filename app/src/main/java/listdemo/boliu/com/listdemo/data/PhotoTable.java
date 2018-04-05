package listdemo.boliu.com.listdemo.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by boliu on 3/22/18.
 */

public class PhotoTable {
    // Database table
    public static final String TABLE_NAME = "photo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_DESCRIPTION = "description";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DATA + " text not null, "
            + COLUMN_URI + " text not null, "
            + COLUMN_PATH + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.d(PhotoTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
