package listdemo.boliu.com.listdemo.adapter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.BasePresenter;
import listdemo.boliu.com.listdemo.api.ApiService;
import listdemo.boliu.com.listdemo.api.RetroClient;
import listdemo.boliu.com.listdemo.data.PhotoTable;
import listdemo.boliu.com.listdemo.model.Contact;
import listdemo.boliu.com.listdemo.model.carmera.Photo;

import static listdemo.boliu.com.listdemo.data.PhotoContentProvider.CONTENT_URI;

/**
 * Created by bloiu on 12/4/2017.
 */

public class ContactPresenter implements BasePresenter<ContactListView>, LoaderManager.LoaderCallbacks<Cursor> {
    private final static String TAG = "ContactPresenter";
    private final int CURSOR_LOADER_ID = 1;

    private Activity mActivity;
    private ContactListView mListView;
    private List<Contact> mList = new ArrayList<>();

    @Override
    public void attachView(ContactListView view) {
        mListView = view;
    }

    @Override
    public void detachView(ContactListView view) {

    }

    public ContactPresenter(Activity mActivity) {
        this.mActivity = mActivity;
        LoaderManager loaderManager = mActivity.getLoaderManager();
        //loaderManager.initLoader(CURSOR_LOADER_ID, null, this);
    }

    public void startLoadContacts() {

        if (mListView == null) {
            Log.w(TAG, "[startLoadFacts] please attach view first.");
        }

        //mListView.showLoading();

        final ApiService apiService = RetroClient.getApiService();

        LoaderManager loaderManager = mActivity.getLoaderManager();
        loaderManager.restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d("BO", "onCreateLoader");
        return new CursorLoader(mActivity, CONTENT_URI,null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mListView.showResult(getPhotoList(cursor));
        Log.d("BO", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListView.showResult(null);
        Log.d("BO", "onLoadReset");

    }

    List getPhotoList(Cursor cursor) {
        List<Photo> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            Photo photo = new Photo();
            int uriColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_URI);
            String uri = cursor.getString(uriColumnIndex);
            int pathColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_PATH);
            String path = cursor.getString(pathColumnIndex);
            int dataColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_DATA);
            String data = cursor.getString(dataColumnIndex);
            photo.uri = uri;
            photo.path = path;
            photo.data = data;
            list.add(photo);
        }
        return list;
    }
}
