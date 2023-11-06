package com.example.main;

import com.example.gui.Model;

public class LoginModel extends Model {
    private LoginWorker L_worker;

    public LoginModel() {
        L_worker = new LoginWorker(this);
    }
}


