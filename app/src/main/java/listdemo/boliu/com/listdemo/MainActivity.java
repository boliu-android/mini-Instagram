package listdemo.boliu.com.listdemo;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.adapter.ContactAdapter;
import listdemo.boliu.com.listdemo.api.ApiService;
import listdemo.boliu.com.listdemo.api.RetroClient;
import listdemo.boliu.com.listdemo.model.Contact;
import listdemo.boliu.com.listdemo.model.ContactList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    List<Contact> contactList;
    private View parentView;
    private ListView listView;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),
                        contactList.get(position).getName() + " => " + contactList.get(position).getPhone().getHome(),
                        Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog;
                /**
                 * Progress Dialog for User Interaction
                 */
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("get json");
                dialog.setMessage("json");
                dialog.show();

                final ApiService apiService = RetroClient.getApiService();

                Call<ContactList> call = apiService.getContectList();

                call.enqueue(new Callback<ContactList>() {
                    @Override
                    public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            contactList = response.body().getContacts();

                            adapter = new ContactAdapter(MainActivity.this, contactList);
                            listView.setAdapter(adapter);
                        } else {
                            Snackbar.make(parentView,"wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactList> call, Throwable t) {
                        dialog.dismiss();
                        Snackbar.make(parentView, "fails", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
