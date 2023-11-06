package com.example.mysecondproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gui.AndroidView;
import com.example.gui.Model;
import com.example.gui.ModelManager;
import com.example.handler.ButtonHandler;
import com.example.handler.TextViewHandler;
import com.example.main.MainModel;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AndroidView {
    private MainModel m_model;
    HomeFragment homeFragment;
    InfoFragment infoFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NOTE: 모델 관리자에서 모델을 찾습니다. 찾을 수 없다면 새로 할당합니다.
        String modelClassName = MainModel.class.getName();

        if(!ModelManager.getInstance().hasModel(modelClassName))
        ModelManager.getInstance().addModel(modelClassName, new MainModel());

        m_model = (MainModel) ModelManager.getInstance().getModel(modelClassName);
        m_model.registerView(this);

        homeFragment = new HomeFragment();
        infoFragment = new InfoFragment();
        settingFragment = new SettingFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.info) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, infoFragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public Model getModel() {
        return m_model;
    }
}