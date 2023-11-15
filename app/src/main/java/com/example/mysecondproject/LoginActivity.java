package com.example.mysecondproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.gui.AndroidView;
import com.example.gui.Model;
import com.example.main.LoginHandler;
import com.example.main.model.JoinModel;
import com.example.service.LoginService;

import customfonts.MyTextView_Poppins_Medium;

public class LoginActivity extends AndroidView {
    LoginActivity loginActivity;
    private JoinModel l_model;
    public void showfailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.fail_dialog, null);
        builder.setView(dialogView);

        customfonts.MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
        MyTextView_Poppins_Medium confirmButton = dialogView.findViewById(R.id.confirm_button);

        dialogTitle.setText("이용이 정지된 계정입니다.");
        confirmButton.setText("확인");

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        MyTextView_Poppins_Medium buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        MyTextView_Poppins_Medium buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = ((EditText)findViewById(R.id.editTextAccount)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();

                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    TextView errorTextView = findViewById(R.id.Error);
                    errorTextView.setText("아이디와 비밀번호를 모두 입력하세요.");
                    errorTextView.setTextColor(Color.RED);
                    // 키패드 내리기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // 아이디와 비밀번호 입력 칸 비우기
                    ((EditText) findViewById(R.id.editTextAccount)).setText("");
                    ((EditText) findViewById(R.id.editTextPassword)).setText("");

                    return;
                }

                LoginHandler loginHandler;
                loginHandler = new LoginHandler(loginActivity, v);
                LoginService loginService = new LoginService(loginHandler, account, password);
                loginService.bindNetworkModule(IntroActivity.networkModule);
                //System.out.println(Thread.currentThread().getName());
                IntroActivity.networkThread.requestService(loginService);


            }
        });
    }

    @Override
    public Model getModel() {
        return l_model;
    }
}