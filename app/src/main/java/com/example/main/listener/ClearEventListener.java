package com.example.main.listener;

import android.view.View;

import com.example.main.MainModel;

public class ClearEventListener implements View.OnClickListener
{
    private MainModel m_model;

    public ClearEventListener(MainModel _model)
    {
        m_model = _model;
    }

    @Override
    public void onClick(View v)
    {
        //m_model.setTestValue(0);
    }
}
