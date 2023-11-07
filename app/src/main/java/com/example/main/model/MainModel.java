package com.example.main.model;

import com.example.gui.Model;
import com.example.main.worker.MainWorker;

public class MainModel extends Model {
    private MainWorker m_worker;

    public MainModel() {
        m_worker = new MainWorker(this);
    }
}


