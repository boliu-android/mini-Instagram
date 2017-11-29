package listdemo.boliu.com.listdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import listdemo.boliu.com.listdemo.adapter.DogInfoListView;
import listdemo.boliu.com.listdemo.adapter.DogInfoPresenter;
import listdemo.boliu.com.listdemo.adapter.DogAdapter;
import listdemo.boliu.com.listdemo.view.EditDogInfoActivity;
import listdemo.boliu.com.listdemo.view.DetailsInfoActivity;
import listdemo.boliu.com.listdemo.data.ContentResolverHelper;
import listdemo.boliu.com.listdemo.model.Dog.DogInfo;
import listdemo.boliu.com.listdemo.utils.PermissionUtils;

import static listdemo.boliu.com.listdemo.view.DetailsInfoActivity.DOG_NAME;
import static listdemo.boliu.com.listdemo.view.DetailsInfoActivity.IMAGE_PATH;
import static listdemo.boliu.com.listdemo.view.DetailsInfoActivity.OWNER_NAME;

/**
 * Created by bloiu on 5/13/2017.
 */

public class MainActivity extends AppCompatActivity implements DogInfoListView, SwipeRefreshLayout.OnRefreshListener {
    private DogInfoPresenter mPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DogAdapter adapter;
    private ListView mListView;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        // MVP
        mPresenter = new DogInfoPresenter(this);
        mPresenter.attachView(this);
        mPresenter.startLoadInfos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        //init search
        mListView.setTextFilterEnabled(true);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.filterByQuery(newText);
                return false;
            }

        });
        return true;
    }

    @Override
    public void onRefresh() {
        mPresenter.startLoadInfos();
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

    private void init() {
        // check permission
        PermissionUtils.checkPermission(this);

        // refresh to load layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final Intent intent = new Intent(this, EditDogInfoActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        // listView
        mListView = findViewById(R.id.listView);
        adapter = new DogAdapter(MainActivity.this);
        mListView.setAdapter(adapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DogInfo dogInfo = adapter.getItem(i);
                new ContentResolverHelper(mListView.getContext()).deleteInfo(dogInfo);
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                DogInfo dogInfo = adapter.getItem(i);
                                                final Intent intent = new Intent(MainActivity.this, DetailsInfoActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(DOG_NAME, dogInfo.ownerName);
                                                bundle.putString(OWNER_NAME, dogInfo.dogName);
                                                bundle.putString(IMAGE_PATH, dogInfo.uri);
                                                intent.putExtras(bundle);
                                                MainActivity.this.startActivity(intent);
                                            }
                                        }
        );
    }
}
