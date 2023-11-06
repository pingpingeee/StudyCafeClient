package com.example.main;

import com.example.gui.Model;

public class JoinModel extends Model {
    private JoinWorker m_worker;

    private int m_testValue = 0;

    public JoinModel() {
        m_worker = new JoinWorker(this);
    }
}


