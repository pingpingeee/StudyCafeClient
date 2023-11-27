package study.customer.handler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import study.customer.gui.LoginActivity;
import study.customer.main.MainActivity;

import com.example.mysecondproject.R;

import org.jetbrains.annotations.NotNull;

public class LoginHandler extends Handler {
    public static String uuid;
    View view;
    LoginActivity loginActivity;
    public LoginHandler(LoginActivity loginActivity, View view) {
        super();
        this.loginActivity = loginActivity;
        this.view = view;
    }

    @Override
    public void handleMessage(@NotNull Message message) {
        super.handleMessage(message);
        Bundle bundle = message.getData();
        String response = bundle.getString("response");

        if (response.equals("<SUCCESS>")) {
            uuid = bundle.getString("uuid");
            Intent intent = new Intent(loginActivity, MainActivity.class);
            loginActivity.startActivity(intent);
        } else if(response.equals("<BLACKED_ACCOUNT>")){

            loginActivity.showfailDialog();

        } else if (response.equals("<FAILURE>")){
            TextView errorTextView = loginActivity.findViewById(R.id.Error);
            errorTextView.setText("아이디 또는 비밀번호가 올바르지 않습니다.");
            // 키패드 내리기
            InputMethodManager imm = (InputMethodManager) loginActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            // 아이디와 비밀번호 입력 칸 비우기
            ((EditText)loginActivity.findViewById(R.id.editTextAccount)).setText("");
            ((EditText)loginActivity.findViewById(R.id.editTextPassword)).setText("");

            errorTextView.setTextColor(Color.RED);
        }else {
            System.out.println("test");
        }
    }
}
