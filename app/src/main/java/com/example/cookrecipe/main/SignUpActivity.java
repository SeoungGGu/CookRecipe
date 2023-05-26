package com.example.cookrecipe.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookrecipe.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextUserID, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEamil;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextUserID = findViewById(R.id.UserID);
        textInputEditTextEamil = findViewById(R.id.email);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);

        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserID, UserEmail, UserName, PassWord;
                UserID = String.valueOf(textInputEditTextUserID.getText());
                UserEmail = String.valueOf(textInputEditTextEamil.getText());
                UserName = String.valueOf(textInputEditTextUsername.getText());
                PassWord = String.valueOf(textInputEditTextPassword.getText());

                if(!UserID.equals("") && !UserName.equals("") && !UserEmail.equals("") && !PassWord.equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "UserID";
                            field[1] = "UserEmail";
                            field[2] = "UserName";
                            field[3] = "PassWord";

                            String[] data = new String[4];
                            data[0] = UserID;
                            data[1] = UserEmail;
                            data[2] = UserName;
                            data[3] = PassWord;
                            PutData putData = new PutData("http://192.168.0.14/AndroidDB/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"모든 빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}