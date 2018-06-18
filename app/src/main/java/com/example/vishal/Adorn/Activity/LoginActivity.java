
package com.example.vishal.Adorn.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishal.Adorn.MyPojo.MyPojo;
import com.example.vishal.Adorn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

        Button btnSkip,btnSignUp,btnLogin;
    EditText editEmail,editPassword;

    String email=" ",password=" ";
    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
        MyPojo myPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Registration");

        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText) findViewById(R.id.editPassword);

        btnSignUp=(Button) findViewById(R.id.singUp);
        btnSkip=(Button) findViewById(R.id.btnSkip);
        btnLogin=findViewById(R.id.btnLogin);
        myPojo=new MyPojo();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

       btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=editEmail.getText().toString();
                password=editPassword.getText().toString();
                int flag1=0,flag2=0;

                if(email.length()==0){
                    flag1=1;
                    editEmail.requestFocus();
                    editEmail.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!email.matches("[0-9a-zA-Z.]+" + "@gmail.com")){
                    flag1=1;
                    editEmail.requestFocus();
                    editEmail.setError("ENTER CORRECT EMAIL");
                }
                else {
                    flag1=0;
                }

                if(password.length()==0){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!password.matches("[0-9a-zA-Z.]+")){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("ENTER CORRECT PASSWORD");
                }
                else if(password.length()<8){
                    flag2=1;
                    editPassword.requestFocus();
                    editPassword.setError("ENTER MiN 8 CHARACTER");
                }
                else {
                    flag2=0;
                }

                if(flag1==0 && flag2==0){
                    login();
                }
            }
        });
    }

    private void login() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    }
                    else {

                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
