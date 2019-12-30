package com.theteam.classproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    Button btn_register, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        confirmPasswordEditText = findViewById(R.id.et_cpassword);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm_password = confirmPasswordEditText.getText().toString();

                if(username.equals("") || password.equals("") || confirm_password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(confirm_password)){
                        Boolean checkusername = databaseHelper.CheckUsername(username);
                        if(checkusername == true){
                            Boolean insert = databaseHelper.Insert(username, password);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                                usernameEditText.setText("");
                                passwordEditText.setText("");
                                confirmPasswordEditText.setText("");
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
