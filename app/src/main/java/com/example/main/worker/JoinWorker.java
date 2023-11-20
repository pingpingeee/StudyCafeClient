package com.example.main.worker;

import com.example.gui.Worker;
import com.example.main.model.JoinModel;

public class JoinWorker extends Worker
{
    private JoinModel j_model;

    public JoinWorker(JoinModel _model)
    {
        super(_model);

        j_model = _model;
        new Thread(this).start();
    }

/*    @Override
    public void onUpdate()
    {
        m_model.increaseTestValue();
    }*/
}