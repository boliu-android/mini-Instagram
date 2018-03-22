package listdemo.boliu.com.listdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import listdemo.boliu.com.listdemo.adapter.ContactAdapter;
import listdemo.boliu.com.listdemo.adapter.ContactListView;
import listdemo.boliu.com.listdemo.adapter.ContactPresenter;
import listdemo.boliu.com.listdemo.carmar.CameraUtils;
import listdemo.boliu.com.listdemo.data.DataUtils;
import listdemo.boliu.com.listdemo.model.Contact;

import static listdemo.boliu.com.listdemo.carmar.CameraUtils.REQUEST_IMAGE;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.dateToString;
import static listdemo.boliu.com.listdemo.carmar.CameraUtils.getImageDestination;

public class MainActivity extends AppCompatActivity implements ContactListView, SwipeRefreshLayout.OnRefreshListener {

    private ContactPresenter mPresenter;
    private View parentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ContactAdapter adapter;
    private String mPhotoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        parentView = findViewById(R.id.parentLayout);

        // listView
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ContactAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        // MVP
        mPresenter = new ContactPresenter();
        mPresenter.attachView(this);
        mPresenter.startLoadContacts();
    }

    public void init() {
        // refresh to load layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtils.createFilesFolder();
                mPhotoName = dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
                startActivity(CameraUtils.getTakePhotoIntent(MainActivity.this, null,null));
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
        // use snackbar to replace Toast to show Error message

    }

    @Override
    public void showResult(List<Contact> list) {
        Snackbar.make(swipeRefreshLayout, "success", Snackbar.LENGTH_SHORT).show();
        adapter.setContacts(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showTitle(String title) {

    }

}
