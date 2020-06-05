package pl.labs.orange.orangekontakty.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.labs.orange.orangekontakty.R;
import pl.labs.orange.orangekontakty.common.RetrofitInstance;
import pl.labs.orange.orangekontakty.data.Contact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        final Button btnSave = findViewById(R.id.btn_add);
        final TextView tvUserName = findViewById(R.id.tvFillData);
        final EditText edUserName = findViewById(R.id.etUserName);
        final EditText edUserSurName = findViewById(R.id.etUserSurName);
        final EditText edPhoneNumber = findViewById(R.id.etPhoneNumber);
        edUserName.getText();
        edUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() < 3) {
                    edUserName.setError(getString(R.string.error_name_count));
                    btnSave.setEnabled(false);
                } else {
                    edUserName.setError(null);
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                contact.setName(edUserName.getText().toString());
                contact.setLastName(edUserSurName.getText().toString());
                contact.setPhone(edPhoneNumber.getText().toString());
                saveContact(contact);
            }
        });
    }

    private void saveContact(Contact contact){
        RetrofitInstance.getContactApi().putContact(contact).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.isSuccessful()) {
                    Log.e("isSuccessful", response.body().toString());
                }else{
                    Log.e("isHttpError", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable throwable) {
                Log.e("onFailure","", throwable);
            }
        });
    }
}
