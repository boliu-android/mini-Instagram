package listdemo.boliu.com.listdemo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by boliu on 3/22/18.
 */

public class PhotoContentProvider extends ContentProvider {

    // database
    private PhotoDataBaseHelper mHelper;
    private SQLiteDatabase mDatabase;
    private static final String TABLE_NAME = PhotoTable.TABLE_NAME;

    private static final int TODOS = 10;
    private static final int TODO_ID = 20;

    private static final String AUTHORITY = "listdemo.boliu.com.listdemo.photos.contentprovider";


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";


    public static final int ITEM = 1;
    public static final int ITEM_ID = 2;

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/photo";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, TABLE_NAME, ITEM);
        mMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mHelper = new PhotoDataBaseHelper(getContext());
        mDatabase = mHelper.getWritableDatabase();
        return false;
    }


    @Override
    public String getType(Uri uri) {
        switch (mMatcher.match(uri)) {
            case ITEM:
                return CONTENT_TYPE;
            case ITEM_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long rowId;
        if (mMatcher.match(uri) != ITEM) {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        rowId = mDatabase.insert(TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        Cursor cursor = null;
        switch (mMatcher.match(uri)) {
            case ITEM:
                cursor = mDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                cursor = mDatabase.query(TABLE_NAME, projection, COLUMN_ID + "=" + uri.getLastPathSegment(), selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

}
