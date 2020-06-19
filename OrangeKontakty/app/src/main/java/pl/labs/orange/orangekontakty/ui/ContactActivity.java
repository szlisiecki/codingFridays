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
    private Call<Contact> contactCall = null;
    private Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        final Button btnSave = findViewById(R.id.btn_add);

        final EditText edUserName = findViewById(R.id.etUserName);
        final EditText edUserSurName = findViewById(R.id.etUserSurName);
        final EditText edPhoneNumber = findViewById(R.id.etPhoneNumber);
        Contact contact = (Contact) getIntent().getSerializableExtra("CONTACT_KEY");
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
        if(contact != null) {
            edUserName.setText(contact.getName());
            edUserSurName.setText(contact.getLastName());
            edPhoneNumber.setText(contact.getPhone());
            btnSave.setText("Edit");
            isEdit = true;
        }
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
        contactCall = RetrofitInstance.getContactApi().putContact(contact);
        contactCall.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(response.isSuccessful()) {
                    finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(contactCall != null){
            contactCall.cancel();
        }
    }
}
