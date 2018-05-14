package listdemo.boliu.com.listdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import listdemo.boliu.com.listdemo.adapter.ContactListView;
import listdemo.boliu.com.listdemo.adapter.ContactPresenter;
import listdemo.boliu.com.listdemo.adapter.PhotoAdapter;
import listdemo.boliu.com.listdemo.carmar.CameraActivity;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.data.DataUtils;
import listdemo.boliu.com.listdemo.model.carmera.DogInfo;
import listdemo.boliu.com.listdemo.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements ContactListView, SwipeRefreshLayout.OnRefreshListener {

    public static final int GIF_CONVERT_MSG = 2;

    private ContactPresenter mPresenter;
    private View parentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotoAdapter adapter;
    private String mPhotoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        parentView = findViewById(R.id.parentLayout);

        // listView
        final ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new PhotoAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DogInfo dogInfo = adapter.getItem(i);
                new ContentResolverHelper(listView.getContext()).deleteInfo(dogInfo);
                return false;
            }
        });

        // MVP
        mPresenter = new ContactPresenter(this);
        mPresenter.attachView(this);
        mPresenter.startLoadContacts();
    }

    public void init() {
        // check permission
        PermissionUtils.checkPermission(this);

        // refresh to load layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final Intent intent = new Intent(this, CameraActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.startLoadContacts();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {
        // use snackbar to replace ToastUtils to show Error message

    }

    @Override
    public void showResult(List<DogInfo> list) {
        adapter.setContacts(list);
        adapter.notifyDataSetChanged();
        hideLoading();
        Log.d("BO", "Main showResult");
    }

    @Override
    public void showTitle(String title) {

    }

    private String getGifPath(String name) {
        return DataUtils.getFilesFolder().toString() + "/Gifs/" + name + ".gif";
    }

}
