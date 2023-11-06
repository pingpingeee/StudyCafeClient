package com.example.handler;

import android.view.View;
import android.widget.Button;

public class ButtonHandler extends AndroidViewHandler
{
    public ButtonHandler(Button _button, String _bundleKey)
    {
        super(_button, _bundleKey);
    }

    public void setListener(View.OnClickListener _listener)
    {
        Button button = (Button)super.getAndroidView();
        button.setOnClickListener(_listener);
    }
}