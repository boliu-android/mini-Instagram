package listdemo.boliu.com.listdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by boliu on 3/22/18.
 */

public class DogDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dog_table.db";
    private static final int DATABASE_VERSION = 1;

    public DogDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        DogTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        DogTable.onUpgrade(database, oldVersion, newVersion);
    }
}
