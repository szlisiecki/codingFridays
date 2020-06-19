package pl.labs.orange.orangekontakty.common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static ContactApi contactApi;
    private static final String BASE_URL = "http://192.168.1.49:8080";

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
           HttpLoggingInterceptor loggingInterceptor =  new HttpLoggingInterceptor();
           loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ContactApi getContactApi(){
        if(contactApi == null){
            contactApi = getRetrofitInstance().create(ContactApi.class);
        }
        return contactApi;
    }

}