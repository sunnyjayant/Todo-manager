package com.example.aakas.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter {

    List<Expense> items;
    LayoutInflater inflater;

    public ExpenseAdapter(@NonNull Context context, List<Expense> items) {
        super(context, 0,  items);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output= convertView;
        if(convertView == null){
            output = inflater.inflate(R.layout.expense_row_layout,parent,false);

            TextView nameTextView = output.findViewById(R.id.expenseName);
            TextView amountTextView = output.findViewById(R.id.expenseAmount);

            ExpenseViewHolder expenseViewHolder= new ExpenseViewHolder();
            expenseViewHolder.t1= nameTextView;
            expenseViewHolder.t2= amountTextView;

            output.setTag(expenseViewHolder);

        }

        ExpenseViewHolder ex= (ExpenseViewHolder) output.getTag();
        Expense expense = items.get(position);

        ex.t1.setText(expense.getName());
        ex.t2.setText(expense.getAmount() + "Rs");

        return output;
    }
}
