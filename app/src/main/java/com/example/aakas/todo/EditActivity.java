package com.example.aakas.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText e1, e2;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        e1 = findViewById(R.id.expenseedittext);
        e2 = findViewById(R.id.amountEdittext);

        Intent intent= getIntent();
        e1.setText(intent.getStringExtra("Title"));
        int amt = intent.getIntExtra("Amount",0);
        e2.setText( amt + "");

        position= intent.getIntExtra("Position",-1);
    }

    public void saveChanges(View view){
        Intent intent = new Intent();

        intent.putExtra("Title",e1.getText().toString());
        intent.putExtra("Amount",e2.getText().toString());
        intent.putExtra("Position",position);

        setResult(2,intent);
        finish();
    }
}
