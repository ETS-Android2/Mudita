package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registerscreen extends AppCompatActivity {
    private EditText Name,Email,Password,Age;
    private Button Register,Sign_in;
    private ImageView Male,Female;
    private FirebaseAuth auth;
    private String txt_name,txt_email,txt_password,txt_age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerscreen);
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.passwordreg);
        Age = findViewById(R.id.age);
        Register =findViewById(R.id.registerbutton);
        Sign_in = findViewById(R.id.signintransparent);
        Male = findViewById(R.id.maleicon);
        Female = findViewById(R.id.femaleicon);
        auth = FirebaseAuth.getInstance();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 txt_name = Name.getText().toString();
                 txt_email = Email.getText().toString();
                 txt_password = Password.getText().toString();
                 txt_age = Age.getText().toString();
                if ((TextUtils.isEmpty(txt_name)) || (TextUtils.isEmpty(txt_email)) || (TextUtils.isEmpty(txt_password)) || (TextUtils.isEmpty(txt_age)))
                {
                    Toast.makeText(Registerscreen.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6)
                {
                    Toast.makeText(Registerscreen.this, "Password should be greater than 6 character.", Toast.LENGTH_SHORT).show();
                } else
                    {
                    Registeruser(txt_name, txt_email, txt_password, txt_age);
                    }

            }
        });
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (Registerscreen.this,Loginscreen.class);
                startActivity(intent);
            }
        });
    }

    private void Registeruser(final String name, String email, String password, String age) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registerscreen.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {Intent intent=new Intent(Registerscreen.this,Mainscreen1.class);
                   intent.putExtra("username",txt_name);
                   intent.putExtra("photourl","nophoto");
                startActivity(intent);
                finish();}
                else {
                String str=task.getException().getMessage();
                    Toast.makeText(Registerscreen.this,""+str,Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}