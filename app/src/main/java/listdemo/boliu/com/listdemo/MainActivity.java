package listdemo.boliu.com.listdemo;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.adapter.ContactAdapter;
import listdemo.boliu.com.listdemo.adapter.ContactListView;
import listdemo.boliu.com.listdemo.adapter.ContactPresenter;
import listdemo.boliu.com.listdemo.api.ApiService;
import listdemo.boliu.com.listdemo.api.RetroClient;
import listdemo.boliu.com.listdemo.model.Contact;
import listdemo.boliu.com.listdemo.model.ContactList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ContactListView, SwipeRefreshLayout.OnRefreshListener {

    private ContactPresenter mPresenter;
    private View parentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentView = findViewById(R.id.parentLayout);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),
//                        contactList.get(position).getName() + " => " + contactList.get(position).getPhone().getHome(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

        // refresh to load layout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // listView
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ContactAdapter(MainActivity.this);
        listView.setAdapter(adapter);

        // MVP
        mPresenter = new ContactPresenter();
        mPresenter.attachView(this);
        mPresenter.startLoadContacts();
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

    //    private void load() {
//        final ProgressDialog dialog;
//        /**
//         * Progress Dialog for User Interaction
//         */
//        dialog = new ProgressDialog(MainActivity.this);
//        dialog.setTitle("get json");
//        dialog.setMessage("json");
//        dialog.show();
//
//        final ApiService apiService = RetroClient.getApiService();
//
//        Call<ContactList> call = apiService.getContactList();
//
//        call.enqueue(new Callback<ContactList>() {
//            @Override
//            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
//                dialog.dismiss();
//
//                if (response.isSuccessful()) {
//                    contactList = response.body().getContacts();
//
//                    adapter = new ContactAdapter(MainActivity.this, contactList);
//                    listView.setAdapter(adapter);
//                } else {
//                    Snackbar.make(parentView,"wrong", Snackbar.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ContactList> call, Throwable t) {
//                dialog.dismiss();
//                Snackbar.make(parentView, "fails", Snackbar.LENGTH_LONG).show();
//            }
//        });
//    }
}
