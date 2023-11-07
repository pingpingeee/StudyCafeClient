package com.example.main.worker;

import com.example.gui.Worker;
import com.example.main.model.LoginModel;

public class LoginWorker extends Worker
{
    private LoginModel L_model;

    public LoginWorker(LoginModel _model)
    {
        super(_model);

        L_model = _model;
        new Thread(this).start();
    }

/*    @Override
    public void onUpdate()
    {
        m_model.increaseTestValue();
    }*/
}
