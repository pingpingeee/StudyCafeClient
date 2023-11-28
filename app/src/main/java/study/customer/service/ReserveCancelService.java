package study.customer.service;

import android.os.Bundle;
import android.os.Message;

import java.util.ArrayList;
import java.util.Vector;

import study.customer.handler.ReserveSelectHandler;
import study.customer.in.INetworkModule;
import study.customer.in.INetworkService;
import study.customer.in.NetworkLiteral;
import study.customer.main.CustomerManager;

public class ReserveCancelService implements INetworkService {
    ReserveSelectHandler reserveSelectHandler;
    private INetworkModule m_netModule;

    public ReserveCancelService(ReserveSelectHandler reserveSelectHandler) {
        this.reserveSelectHandler = reserveSelectHandler;
    }
    @Override
    public boolean tryExecuteService() {
        m_netModule.writeLine("RESERVE_CANCEL_SERVICE");
        m_netModule.writeLine(NetworkLiteral.EOF);

        String response = m_netModule.readLine();

        Message message = reserveSelectHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("response", response);

        message.setData(bundle);
        reserveSelectHandler.sendMessage(message);
        return true;
    }
    @Override
    public void bindNetworkModule(INetworkModule _netModule) {
        this.m_netModule = m_netModule;
    }

}
