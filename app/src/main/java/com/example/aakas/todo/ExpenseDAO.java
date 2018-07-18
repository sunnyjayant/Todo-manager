package com.example.aakas.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void addExpenses(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Insert
    void addTwoExpenses(Expense expense1, Expense expense2);

    @Insert
    void addExpenses(Expense[] expenses);

    @Query("select * from expenses")
    List<Expense> getExpenses();

    @Query("select * from expenses where amount > :amountA")
    List<Expense> getExpensesMoreThan100(int amountA);

}
