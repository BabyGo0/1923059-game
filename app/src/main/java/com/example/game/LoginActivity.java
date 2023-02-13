package com.example.game;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText Account, Password;
    private CheckBox checkBox;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Account = findViewById(R.id.Account);
        Password = findViewById(R.id.Password);
        checkBox = findViewById(R.id.checkbox);
        Login = findViewById(R.id.Login);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE);
        String username = sharedPreferences.getString("Account", "");
        String password = sharedPreferences.getString("Password", "");

        if (!username.equals("") && !password.equals("")) {
            Account.setText(username);
            Password.setText(password);
            checkBox.setChecked(true);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = Account.getText().toString();
                String password = Password.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                if (account.equals("xyx") && password.equals("1923059")) {
                    if (checkBox.isChecked()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Account", account);
                        editor.putString("Password", password);
                        editor.apply();
                    }
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}