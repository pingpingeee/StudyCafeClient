package com.example.mysecondproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gui.AndroidView;
import com.example.gui.Model;
import com.example.main.JoinModel;
import com.example.main.MainModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

import customfonts.MyTextView_Poppins_Medium;

public class JoinActivity extends AndroidView {
    private JoinModel j_model;

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.success_dialog, null);
        builder.setView(dialogView);

        customfonts.MyTextView_Poppins_Medium dialogTitle = dialogView.findViewById(R.id.dialog_title);
        MyTextView_Poppins_Medium confirmButton = dialogView.findViewById(R.id.confirm_button);

        dialogTitle.setText("회원가입 완료");
        confirmButton.setText("확인");

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        MyTextView_Poppins_Medium backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyTextView_Poppins_Medium buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname = ((EditText)findViewById(R.id.editTextNickname)).getText().toString().trim();
                String account = ((EditText)findViewById(R.id.editTextAccount)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();

                if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    // 빈칸이 있을 경우, 에러 메세지 텍스트뷰에 메시지 설정
                    TextView errorTextView = findViewById(R.id.Error);
                    errorTextView.setText("칸을 채워주세요");
                    errorTextView.setTextColor(Color.RED); //

                    // 키패드 내리기
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // 아이디와 비밀번호 입력 칸 비우기
                    ((EditText) findViewById(R.id.editTextAccount)).setText("");
                    ((EditText) findViewById(R.id.editTextPassword)).setText("");
                    return;
                }

                // 서버로 데이터 전송
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket clientSocket = ServerCon.connectToServer();

                            if (clientSocket != null) {
                                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                                out.println("JOIN_SERVICE"); // 회원가입 요청임을 알려줌
                                out.println(nickname);
                                out.println(account);
                                out.println(password);


                                String response = in.readLine();
                                System.out.println("Account: " + account);
                                System.out.println("Password: " + password);
                                System.out.println("Response: " + response);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (response.equals("DUPLICATE_ACCOUNT")) {
                                            // 중복된 아이디 처리
                                            TextView errorTextView = findViewById(R.id.Error);
                                            errorTextView.setText("사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.");
                                            errorTextView.setTextColor(Color.RED);
                                            // 키패드 내리기
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                            //칸 비우기
                                            ((EditText) findViewById(R.id.editTextAccount)).setText("");

                                        } else if (response.equals("DUPLICATE_NICKNAME")) {
                                            // 중복된 닉네임 처리
                                            TextView errorTextView = findViewById(R.id.Error);
                                            errorTextView.setText("사용할 수 없는 닉네임입니다. 다른 닉네임을 입력해 주세요.");
                                            errorTextView.setTextColor(Color.RED);
                                            // 키패드 내리기
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                            //칸비우기
                                            ((EditText) findViewById(R.id.editTextNickname)).setText("");
                                        } else if(response.equals("JOIN_SUCCESS")){
                                            // 정상적으로 처리된 경우
                                            // 회원가입 성공 팝업창 띄우기
                                            showSuccessDialog();
                                        }
                                        else{
                                            System.out.println("회원가입 문제1");
                                        }
                                    }
                                });

                                clientSocket.close();
                            } else {
                                System.out.println("연결실패");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public Model getModel() {
        return j_model;
    }
}
