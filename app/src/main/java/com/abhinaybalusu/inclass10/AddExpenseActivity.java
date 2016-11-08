package com.abhinaybalusu.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText;
    private String categoryValue;
    private EditText amountEditText;
    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;

    private String expenseName, expenseAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);



        expenseNameEditText = (EditText)findViewById(R.id.nameEditText);


        Spinner categorySpinner = (Spinner)findViewById(R.id.categorySpinner);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.category_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(staticAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryValue = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        amountEditText = (EditText)findViewById(R.id.amountEditText);

        findViewById(R.id.expenseAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAuth = FirebaseAuth.getInstance();
                mFirebaseUser = mFirebaseAuth.getCurrentUser();

                mDatabase = FirebaseDatabase.getInstance().getReference();

                expenseName = expenseNameEditText.getText().toString();
                expenseAmount = amountEditText.getText().toString();

                if(expenseName.equals("") || expenseAmount.equals("") || categoryValue.equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please Enter all the Details", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String mUserId = mFirebaseUser.getUid();

                    if(mFirebaseUser != null)
                    {
                        Expense expense = new Expense();
                        expense.setExpenseName(expenseName);
                        expense.setExpenseAmount(expenseAmount);
                        expense.setExpenseCategory(categoryValue);

                        Date date = new Date();
                        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yy");
                        String newDate = sd.format(date);
                        expense.setExpenseDate(newDate.toString());

                        mDatabase.child("Users").child(mUserId).child("expense").push().setValue(expense);
                        Toast.makeText(AddExpenseActivity.this, "Expense added successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });

        findViewById(R.id.cancelButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
