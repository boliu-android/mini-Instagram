package listdemo.boliu.com.listdemo.adapter;

import android.support.design.widget.Snackbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.BasePresenter;
import listdemo.boliu.com.listdemo.api.ApiService;
import listdemo.boliu.com.listdemo.api.RetroClient;
import listdemo.boliu.com.listdemo.model.Contact;
import listdemo.boliu.com.listdemo.model.ContactList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bloiu on 12/4/2017.
 */

public class ContactPresenter implements BasePresenter<ContactListView> {
    private final static String TAG = "ContactPresenter";

    private ContactListView mListView;
    private List<Contact> mList = new ArrayList<>();

    @Override
    public void attachView(ContactListView view) {
        mListView = view;
    }

    @Override
    public void detachView(ContactListView view) {

    }

    public void startLoadContacts() {
        if (mListView == null) {
            Log.w(TAG, "[startLoadFacts] please attach view first.");
        }

        mListView.showLoading();

        final ApiService apiService = RetroClient.getApiService();

        Call<ContactList> call = apiService.getContactList();

        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                mListView.hideLoading();

                if (response.isSuccessful()) {
                    List<Contact> contactList = response.body().getContacts();
                    mListView.showResult(contactList);
                } else {
                    mListView.showError(response.code() + " " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ContactList> call, Throwable e) {
                mListView.hideLoading();
                mListView.showError(e.getMessage());
            }
        });
    }
}
