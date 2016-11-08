package com.abhinaybalusu.inclass10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailET;
    private EditText passwordET;
    private String email,password;
    private ProgressDialog pd;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser fUser;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = (EditText)findViewById(R.id.emailEditText);
        passwordET = (EditText)findViewById(R.id.passwordEditText);

        pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading.....");
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        auth = FirebaseAuth.getInstance();

        fUser = auth.getCurrentUser();

        if(fUser != null)
        {
            Intent intent = new Intent(MainActivity.this, ExpenseAppActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please Login to Continue", Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                pd.show();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter all the details")
                            .setTitle("Alert")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            Toast.makeText(MainActivity.this, "Password length should be >6", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, ExpenseAppActivity.class);
                                        startActivity(intent);
                                    }

                                    pd.dismiss();
                                }
                            });
                }

            }
        });

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
