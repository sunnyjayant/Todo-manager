package com.example.aakas.todo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    EditText e1,e2;
    int check=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        e1= findViewById(R.id.expenseEdittext);
        e2= findViewById(R.id.amountEdittext);
        Intent intent1= getIntent();
        check= intent1.getIntExtra("check",-1);
        if(check == 1){
            e1.setText(intent1.getStringExtra("title"));
            e2.setText(intent1.getStringExtra("amount"));
        }

    }

    public void saveDetails(View view){
        Intent intent = new Intent();
        intent.putExtra("Title",e1.getText().toString());
        intent.putExtra("Amount",e2.getText().toString());
        setResult(1,intent);
        finish();
    }
}
