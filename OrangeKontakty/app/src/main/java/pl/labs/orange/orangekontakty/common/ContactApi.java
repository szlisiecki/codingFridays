package pl.labs.orange.orangekontakty.common;

import java.util.List;

import pl.labs.orange.orangekontakty.data.Contact;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContactApi {
    @POST("contacts")
    Call<Contact> putContact(@Body Contact contact);

    @PATCH("contacts")
    Call<Contact> udpateContact(@Body Contact contact);

    @GET("contacts")
    Call<List<Contact>> getContacts(@Query("size") int size);
}
