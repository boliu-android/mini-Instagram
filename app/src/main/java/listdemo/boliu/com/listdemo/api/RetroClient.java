package listdemo.boliu.com.listdemo.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bloiu on 11/28/2017.
 */

public class RetroClient {
    private static final String SERVICE_ENDPOINT = "http://pratikbutani.x10.mx";

    /**
     * Get Retrofit Instance
     * @return
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
