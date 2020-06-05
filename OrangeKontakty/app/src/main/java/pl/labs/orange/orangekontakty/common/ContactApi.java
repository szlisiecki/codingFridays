package pl.labs.orange.orangekontakty.common;

import pl.labs.orange.orangekontakty.data.Contact;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactApi {
    @POST("contacts")
    Call<Contact> putContact(@Body Contact contact);
}
