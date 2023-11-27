package study.customer.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.mysecondproject.R;
import com.google.android.material.navigation.NavigationBarView;

import study.customer.gui.HomeFragment;
import study.customer.gui.InfoFragment;
import study.customer.gui.SettingFragment;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    InfoFragment infoFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

}