package pl.labs.orange.orangekontakty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        final Button btnSave = findViewById(R.id.btn_add);
        final TextView tvUserName = findViewById(R.id.tvFillData);
        final EditText editText = findViewById(R.id.etUserName);
        editText.getText();
        editText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length() < 3) {
                    editText.setError(getString(R.string.error_name_count));
                    btnSave.setEnabled(false);
                }else{
                    editText.setError(null);
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("onClick","add");
                tvUserName.setText(editText.getText());
            }
        });
    }
}
