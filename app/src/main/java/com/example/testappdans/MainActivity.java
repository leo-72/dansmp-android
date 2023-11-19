package com.example.testappdans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText inpUsername, inpPassword;
    Button btnLogin;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = "dans";
        password = "dans123";

        inpUsername = findViewById(R.id.et_username);
        inpPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            if (username.equals(inpUsername.getText().toString())){
                if (password.equals(inpPassword.getText().toString())){
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, JobListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Username salah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}