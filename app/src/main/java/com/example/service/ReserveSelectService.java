package com.example.service;

import android.os.Bundle;
import android.os.Message;

import com.example.main.ReserveHandler;
import com.example.main.ReserveSelectHandler;
import com.example.main.Service;
import com.example.mysecondproject.CustomerManager;
import com.example.network.INetworkModule;
import com.example.network.INetworkService;
import com.example.network.NetworkLiteral;

import java.util.ArrayList;
import java.util.Vector;


public class ReserveSelectService extends Service implements INetworkService {
    ReserveSelectHandler reserveSelectHandler;
    private INetworkModule m_netModule;


    public ReserveSelectService(ReserveSelectHandler reserveSelectHandler) {
        this.reserveSelectHandler = reserveSelectHandler;
    }

    @Override
    public boolean tryExecuteService() {
        if (m_netModule == null) {
            System.out.println("초기화안됨");
            // INetworkModule이 초기화되지 않았음을 확인하고 처리
            return false;
        }

        m_netModule.writeLine("RESERVE_SELECT_SERVICE");
        m_netModule.writeLine(Integer.toString(CustomerManager.getManager().getUuid()));
        m_netModule.writeLine(NetworkLiteral.EOF);

        Vector<String> lines = new Vector<String>();

        while (true) {
            String line = m_netModule.readLine();
            if (line.equals(NetworkLiteral.EOF)) break;
            lines.add(line);
        }


        String response = m_netModule.readLine();

        Message message = reserveSelectHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("lines", new ArrayList<>(lines));
        bundle.putString("response", response);

        message.setData(bundle);
        reserveSelectHandler.sendMessage(message);
        return true;
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }
}
