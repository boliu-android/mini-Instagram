package listdemo.boliu.com.listdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bloiu on 11/28/2017.
 */
public class ContactList {

    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}