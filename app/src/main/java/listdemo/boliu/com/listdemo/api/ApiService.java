package listdemo.boliu.com.listdemo.api;

import listdemo.boliu.com.listdemo.model.ContactList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bloiu on 11/28/2017.
 */

public interface ApiService {

    @GET("/json_data.json")
    Call<ContactList> getContactList();
}
