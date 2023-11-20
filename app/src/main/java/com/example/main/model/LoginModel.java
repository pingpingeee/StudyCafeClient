package com.example.main.model;

import com.example.gui.Model;
import com.example.main.worker.LoginWorker;

public class LoginModel extends Model {
    private LoginWorker L_worker;

    public LoginModel() {
        L_worker = new LoginWorker(this);
    }
}