package com.example.main;

import com.example.gui.Model;
import com.example.gui.AndroidView;
import com.example.mysecondproject.MainActivity;

import java.util.Iterator;

public class MainModel extends Model {
    private MainWorker m_worker;

    public MainModel() {
        m_worker = new MainWorker(this);
    }
}


