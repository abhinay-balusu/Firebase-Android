package com.abhinaybalusu.inclass10;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullnameET;
    private EditText emailETSignUP;
    private EditText passwordETSignUP;
    private FirebaseAuth firebaseAuth;

    private String emailSignUp, passwordSignUp, fullnameSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullnameET = (EditText)findViewById(R.id.nameEditText);
        emailETSignUP = (EditText)findViewById(R.id.emailEditTextSignUp);
        passwordETSignUP = (EditText)findViewById(R.id.passwordEditTextSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButtonNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    return;
                }

                FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
                final DatabaseReference ref = dbRef.getReference();
                FirebaseUser fUser = firebaseAuth.getCurrentUser();

                emailSignUp = emailETSignUP.getText().toString();
                passwordSignUp = passwordETSignUP.getText().toString();
                fullnameSignUp = fullnameET.getText().toString();

                SharedPreferences Prefs = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                SharedPreferences.Editor prefsEditor = Prefs.edit();
                prefsEditor.putString("email",emailSignUp);
                prefsEditor.putString("fullname",fullnameSignUp);
                prefsEditor.commit();

                firebaseAuth.createUserWithEmailAndPassword(emailSignUp, passwordSignUp)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Account Not Created and User should select a different email if not",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this, "User has been created",
                                            Toast.LENGTH_SHORT).show();
                                    System.out.println(task.getResult());

                                    Users user = new Users();
                                    user.setEmail(emailSignUp);
                                    user.setFullname(fullnameSignUp);

                                    ref.child("Users").child(task.getResult().getUser().getUid()).setValue(user);

                                    finish();

//                                    Users user = new Users();
//                                    user.setEmail(emailSignUp);
//                                    user.setFullname(fullnameSignUp);
//                                    user.setPassword(passwordSignUp);
                                }
                            }
                        });

            }
        });

        findViewById(R.id.cancelButtonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailETSignUP.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailETSignUP.setError("Required.");
            valid = false;
        } else {
            emailETSignUP.setError(null);
        }

        String password = passwordETSignUP.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordETSignUP.setError("Required.");
            valid = false;
        } else {
            passwordETSignUP.setError(null);
        }

        return valid;
    }
}
