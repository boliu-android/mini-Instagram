package listdemo.boliu.com.listdemo.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import listdemo.boliu.com.listdemo.model.carmera.Photo;

import static listdemo.boliu.com.listdemo.data.PhotoContentProvider.CONTENT_URI;

/**
 * Created by boliu on 3/25/18.
 */

public class ContentResolverHelper {

    private final Context mContext;
    private final ContentResolver mContentResolver;

    public ContentResolverHelper(Context mContext) {
        this.mContext = mContext;
        mContentResolver = mContext.getContentResolver();
    }

    public Uri insertPhoto(Photo photo) {
        return mContext.getContentResolver().insert(CONTENT_URI, convertToContentValues(photo));
    }

    public int deletePhoto(@NonNull Photo photo) {
        return mContext.getContentResolver().delete(CONTENT_URI, PhotoTable.COLUMN_PATH + "= ?", new String[] {photo.path});
    }

    public Cursor getAllPhotosCursor() {
        return mContentResolver.query(CONTENT_URI,null,null,null,null);
    }

    public ContentValues convertToContentValues(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(PhotoTable.COLUMN_PATH, photo.path);
        values.put(PhotoTable.COLUMN_DATA, photo.data);
        return values;
    }
}
