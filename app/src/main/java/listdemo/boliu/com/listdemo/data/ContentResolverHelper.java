package listdemo.boliu.com.listdemo.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import listdemo.boliu.com.listdemo.model.carmera.DogInfo;

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

    public Uri insertInfo(DogInfo dogInfo) {
        return mContext.getContentResolver().insert(CONTENT_URI, convertToContentValues(dogInfo));
    }

    public int deleteInfo(@NonNull DogInfo dogInfo) {
        return mContext.getContentResolver().delete(CONTENT_URI, PhotoTable.COLUMN_URI + "= ?", new String[] {dogInfo.uri});
    }

    public Cursor getAllPhotosCursor() {
        return mContentResolver.query(CONTENT_URI,null,null,null,null);
    }

    public ContentValues convertToContentValues(DogInfo dogInfo) {
        ContentValues values = new ContentValues();
        //values.put(PhotoTable.COLUMN_IMAGE_PATH, dogInfo.imagePath);
        values.put(PhotoTable.COLUMN_URI, dogInfo.uri);
        values.put(PhotoTable.COLUMN_OWNER_NAME, dogInfo.OwnerName);
        values.put(PhotoTable.COLUMN_DOG_NAME, dogInfo.dogName);
        return values;
    }
}
