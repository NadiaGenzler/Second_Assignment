package com.example.secondassignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText etName=findViewById(R.id.NameET);
        etName.setText(getIntent().getExtras().getString("KEY_FOR_NAME"));
        final EditText etGender=findViewById(R.id.GenderET);
        etGender.setText(getIntent().getExtras().getString("KEY_FOR_GENDER"));
        final EditText etLocation=findViewById(R.id.LocationET);
        etLocation.setText(getIntent().getExtras().getString("KEY_FOR_LOCATION"));

        databaseHelper = new DatabaseHelper(this);
        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.UpdateData(etName.getText().toString(),etGender.getText().toString(),
                        etLocation.getText().toString());

                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

