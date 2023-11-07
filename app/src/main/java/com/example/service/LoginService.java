package com.example.service;

import android.os.Bundle;
import android.os.Message;

import com.example.main.JoinHandler;
import com.example.main.LoginHandler;
import com.example.network.INetworkModule;
import com.example.network.INetworkService;
import com.example.network.NetworkLiteral;

public class LoginService implements INetworkService
{
    private LoginHandler loginHandler;
    private INetworkModule m_netModule;

    private String m_id;
    private String m_pw;

    private String m_uuid;

    public LoginService(LoginHandler loginHandler, String _id, String _pw)
    {
        this.loginHandler = loginHandler;
        m_id = _id;
        m_pw = _pw;
    }

    @Override
    public boolean tryExecuteService()
    {
        m_netModule.writeLine("LOGIN_SERVICE");
        m_netModule.writeLine(m_id);
        m_netModule.writeLine(m_pw);
        m_netModule.writeLine(NetworkLiteral.EOF);

        String response = m_netModule.readLine();
        System.out.println(response);

        Message message = loginHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("response", response);

        if(response.equals("<SUCCESS>")){
            bundle.putString("uuid", m_netModule.readLine());
        }

        message.setData(bundle);
        loginHandler.sendMessage(message);

        return true;
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }

    public String getUuid()
    {
        return m_uuid;
    }
}