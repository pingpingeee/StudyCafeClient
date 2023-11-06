package com.example.handler;

import android.os.Handler;
import android.view.View;

public abstract class AndroidViewHandler extends Handler
{
    private View m_androidView;
    private String m_bundleKey;

    protected AndroidViewHandler(View _androidView, String _bundleKey)
    {
        m_androidView = _androidView;
        m_bundleKey = _bundleKey;
    }

    protected final View getAndroidView()
    {
        return m_androidView;
    }

    public final String getBundleKey()
    {
        return m_bundleKey;
    }

}
