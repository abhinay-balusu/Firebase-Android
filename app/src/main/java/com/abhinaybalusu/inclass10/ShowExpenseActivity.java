package com.abhinaybalusu.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);

        Expense expense_Show =(Expense) getIntent().getExtras().getParcelable("expense");

        TextView expenseNameValueTextView_Show = (TextView)findViewById(R.id.expenseNameValueTextView_Show);
        TextView expenseCategoryValueTextView_Show = (TextView)findViewById(R.id.expenseCategoryValueTextView_Show);
        TextView expenseAmountValueTextView_Show = (TextView)findViewById(R.id.expenseAmountValueTextView_Show);
        TextView expenseDateValueTextView_Show = (TextView)findViewById(R.id.expenseDateValueTextView_Show);

        expenseNameValueTextView_Show.setText(expense_Show.getExpenseName());
        expenseCategoryValueTextView_Show.setText(expense_Show.getExpenseCategory());
        expenseAmountValueTextView_Show.setText("$ "+expense_Show.getExpenseAmount());
        expenseDateValueTextView_Show.setText(expense_Show.getExpenseDate());

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
