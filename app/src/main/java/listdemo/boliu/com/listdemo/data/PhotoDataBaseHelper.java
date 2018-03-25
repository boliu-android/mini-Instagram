package listdemo.boliu.com.listdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by boliu on 3/22/18.
 */

public class PhotoDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "photo_table.db";
    private static final int DATABASE_VERSION = 1;

    public PhotoDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        PhotoTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        PhotoTable.onUpgrade(database, oldVersion, newVersion);
    }
}
