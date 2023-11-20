package com.example.main.model;

import com.example.gui.Model;
import com.example.main.worker.JoinWorker;

public class JoinModel extends Model {
    private JoinWorker m_worker;

    private int m_testValue = 0;

    public JoinModel() {
        m_worker = new JoinWorker(this);
    }
}