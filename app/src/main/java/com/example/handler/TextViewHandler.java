package com.example.handler;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class TextViewHandler extends AndroidViewHandler
{
    public TextViewHandler(TextView _textView, String _bundleKey)
    {
        super(_textView, _bundleKey);
    }

    @Override
    public void handleMessage(@NotNull Message _message)
    {
        super.handleMessage(_message);

        Bundle bundle = _message.getData();
        String message = bundle.getString(super.getBundleKey());
        ((TextView)(super.getAndroidView())).setText(message);
    }

    public void setText(String _message)
    {
        Message message = super.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(super.getBundleKey(), _message);
        message.setData(bundle);
        this.sendMessage(message);
    }

    public void setTextFormat(String _formatMessage, Object... _arguments)
    {
        Message message = super.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(super.getBundleKey(), String.format(_formatMessage, _arguments));
        message.setData(bundle);
        this.sendMessage(message);
    }
}
