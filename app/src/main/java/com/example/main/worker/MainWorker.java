package com.example.main.worker;

import com.example.gui.Worker;
import com.example.main.model.MainModel;

public class MainWorker extends Worker
{
    private MainModel m_model;

    public MainWorker(MainModel _model)
    {
        super(_model);

        m_model = _model;
        new Thread(this).start();
    }

    private String userId;
    private String nickname;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
    @Override
    public void onUpdate() {
        String userId = getUserId();
        String nickname = getNickname();
    }
}