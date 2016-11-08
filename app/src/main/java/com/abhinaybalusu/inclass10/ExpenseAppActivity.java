package com.abhinaybalusu.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseAppActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView noExpensesTextView;
    private ListView expensesListView;
    private ImageView addExpenseImageView;

    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private DatabaseReference mDatabase;
    private String fUid;
    private ValueEventListener mPostListener;
    private CustomAdapter mAdapter;

    private ArrayList<Expense> expensesList;
    private ArrayList<String> keysList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_app);

        noExpensesTextView = (TextView)findViewById(R.id.noExpensesTextView);
        addExpenseImageView = (ImageView)findViewById(R.id.addExpenseImageView);
        expensesListView = (ListView)findViewById(R.id.expensesListView);

        noExpensesTextView.setVisibility(View.INVISIBLE);

        expensesList = new ArrayList<Expense>();
        keysList = new ArrayList<String>();
        addExpenseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExpenseAppActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });

        fAuth = FirebaseAuth.getInstance();

        fUser = fAuth.getCurrentUser();

        fUid = fUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(fUid).child("expense");



    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                expensesList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Expense expense = postSnapshot.getValue(Expense.class);
                    String id = postSnapshot.getKey();
                    keysList.add(id);
                    expensesList.add(expense);

                }

                if(expensesList.size()>0) {

                    noExpensesTextView.setVisibility(View.INVISIBLE);
                    expensesListView.setVisibility(View.VISIBLE);
                    CustomAdapter expenseAdapter = new CustomAdapter(getApplicationContext(), R.layout.row_item_list, expensesList);
                    expensesListView.setAdapter(expenseAdapter);
                    expensesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(ExpenseAppActivity.this,ShowExpenseActivity.class);
                            intent.putExtra("expense",expensesList.get(i));
                            startActivity(intent);
                        }
                    });
                    expensesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            expensesList.remove(i);
                            String key = keysList.get(i);
                            keysList.remove(i);
                            mDatabase = FirebaseDatabase.getInstance().getReference()
                                    .child("Users").child(fUid).child("expense");

                            mDatabase.child(key).removeValue();

                            Toast.makeText(getApplicationContext(), "Expense deleted Successfully", Toast.LENGTH_LONG).show();

                            if(expensesList.size()>0) {
                                noExpensesTextView.setVisibility(View.INVISIBLE);
                                expensesListView.setVisibility(View.VISIBLE);
                                CustomAdapter expenseAdapter = new CustomAdapter(getApplicationContext(), R.layout.row_item_list, expensesList);
                                expensesListView.setAdapter(expenseAdapter);
                            }
                            else{
                                noExpensesTextView.setVisibility(View.VISIBLE);
                                expensesListView.setVisibility(View.INVISIBLE);
                            }
                            return false;
                        }
                    });
                }
                else{

                    noExpensesTextView.setVisibility(View.VISIBLE);
                    expensesListView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ExpenseAppActivity.this, "Failed to load Data.",
                        Toast.LENGTH_SHORT).show();

            }
        };

        mDatabase.addValueEventListener(postListener);

        mPostListener = postListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            mDatabase.removeEventListener(mPostListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_signout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.item_signout)
        {
            fAuth.signOut();
            Intent intent = new Intent(ExpenseAppActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
