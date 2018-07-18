package com.example.aakas.todo;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    List<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    ExpenseDAO expenseDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);


        ExpenseDatabase database = Room.databaseBuilder(getApplicationContext(),ExpenseDatabase.class,"expenses_db").allowMainThreadQueries().build();
        expenseDAO = database.getExpenseDao();
        expenses = expenseDAO.getExpenses();

//        ExpenseOpenHelper openHelper = new ExpenseOpenHelper(this);
//        SQLiteDatabase database = openHelper.getReadableDatabase();
//
//        Cursor cursor = database.query("expense_table",null,null,null,null,null,null);
//
//        while(cursor.moveToNext()){
//
//            String name = cursor.getString(cursor.getColumnIndex(Contract.expense.COLUM_NAME));
//            int amount = cursor.getShort(cursor.getColumnIndex(Contract.expense.COLUM_AMOUNT));
//
//            Expense expense = new Expense(name, amount);
//            expenses.add(expense);
//        }


       /* for(int i = 0;i<10;i++){

            Expense expense = new Expense("Expense " + i,i*100);
            expenses.add(expense);

        }*/

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.expense_row_layout,R.id.expenseName,expenses);

        adapter = new ExpenseAdapter(this, expenses);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) this);

        View view = new View(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        if(id == R.id.adddetails) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expense expense = expenses.get(i);

        final int position = i;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete " + expense.getName() + "?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                expenses.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });

       /* builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                Expense expense1 = expenses.get(i);
                intent.putExtra("Title", expense1.getName().toString());
                intent.putExtra("Amount", expense1.getAmount());

                startActivityForResult(intent,1);
            }
        });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == 1){
                String title = data.getStringExtra("Title");
                int amount = Integer.parseInt(data.getStringExtra("Amount"));
                Expense expense = new Expense(title,amount);
                expense.setName(title);
                expense.setAmount(amount);
                expenseDAO.addExpenses(expense);

                expenses.add(expense);
                adapter.notifyDataSetChanged();

            }
            else if(resultCode == 2){
                String title = data.getStringExtra("Title");
                int amount = Integer.parseInt(data.getStringExtra("Amount"));
                int pos= data.getIntExtra("Position",0);

                Expense expense = new Expense(title,amount);
                expense.setName(title);
                expense.setAmount(amount);
                expenseDAO.addExpenses(expense);



                expenses.set(pos,expense);
                adapter.notifyDataSetChanged();

            }
        }
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Expense expense = expenses.get(i);

        final int position = i;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Edit");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to edit " + expense.getName() + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                Expense expense1 = expenses.get(position);
                intent.putExtra("Title", expense1.getName());
                intent.putExtra("Amount", expense1.getAmount());
                intent.putExtra("Position",position);

                startActivityForResult(intent,1);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


        return true;
    }

    public void setAlarm(View view){

        Intent intent= new Intent(this,SetAlarmFAB.class);
        startActivity(intent);

    }
}



