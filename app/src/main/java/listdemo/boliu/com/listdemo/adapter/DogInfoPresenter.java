package listdemo.boliu.com.listdemo.adapter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.BasePresenter;
import listdemo.boliu.com.listdemo.api.ApiService;
import listdemo.boliu.com.listdemo.api.RetroClient;
import listdemo.boliu.com.listdemo.data.PhotoTable;
import listdemo.boliu.com.listdemo.model.Dog.DogInfo;

import static listdemo.boliu.com.listdemo.data.PhotoContentProvider.CONTENT_URI;

/**
 * Created by bloiu on 5/13/2017.
 */

public class DogInfoPresenter implements BasePresenter<DogInfoListView>, LoaderManager.LoaderCallbacks<Cursor> {
    private final static String TAG = "DogInfoPresenter";
    private final int CURSOR_LOADER_ID = 1;

    private Activity mActivity;
    private DogInfoListView mListView;
    private List<DogInfo> mList = new ArrayList<>();

    @Override
    public void attachView(DogInfoListView view) {
        mListView = view;
    }

    @Override
    public void detachView(DogInfoListView view) {

    }

    public DogInfoPresenter(Activity mActivity) {
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

    public void filterByQuery(String query) {
        if (!TextUtils.isEmpty(query)) {
            String str = query.trim().toLowerCase();
            List<DogInfo> list = new ArrayList<>();
            for (DogInfo dogInfo : mList) {
                if (dogInfo.ownerName.contains(str) || dogInfo.dogName.contains(str)) {
                    list.add(dogInfo);
                }
            }
            mListView.showResult(list);
        } else {
            mListView.showResult(mList);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d("BO", "onCreateLoader");
        return new CursorLoader(mActivity, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mList = getPhotoList(cursor);
        mListView.showResult(mList);
        Log.d("BO", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListView.showResult(null);
        Log.d("BO", "onLoadReset");

    }

    List getPhotoList(Cursor cursor) {
        List<DogInfo> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DogInfo dogInfo = new DogInfo();
                int uriColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_URI);
                String uri = cursor.getString(uriColumnIndex);
                int ownerColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_OWNER_NAME);
                String owner = cursor.getString(ownerColumnIndex);
                int dogNameColumnIndex = cursor.getColumnIndex(PhotoTable.COLUMN_DOG_NAME);
                String dogName = cursor.getString(dogNameColumnIndex);
                //dogInfo.uri = uri;
                dogInfo.ownerName = owner;
                dogInfo.dogName = dogName;
                dogInfo.uri = uri;
                list.add(dogInfo);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
