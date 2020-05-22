package pl.labs.orange.orangekontakty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Button buttion = findViewById(R.id.btn_add);
        buttion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("onClick","add");
            }
        });
    }
}
