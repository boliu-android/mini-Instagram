package listdemo.boliu.com.listdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.BasePresenter;
import listdemo.boliu.com.listdemo.model.Contact;

/**
 * Created by bloiu on 12/4/2017.
 */

public class FilesPresenter implements BasePresenter<ContactListView> {
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
    }
}
