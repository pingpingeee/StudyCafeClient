package study.customer.service;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

import study.customer.handler.ReservableWeekdaySelectHandler;
import study.customer.in.INetworkModule;
import study.customer.in.INetworkService;

public class ReservableWeekdaySelectService implements INetworkService {
    private INetworkModule m_netModule;
    ReservableWeekdaySelectHandler reservableWeekdaySelectHandler;
    private String day;

    public ReservableWeekdaySelectService(ReservableWeekdaySelectHandler reservableWeekdaySelectHandler, String day) {
        this.reservableWeekdaySelectHandler = reservableWeekdaySelectHandler;
        this.day = day;
    }

    @Override
    public boolean tryExecuteService() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                // Background thread: Perform network operations
                m_netModule.writeLine("RESERVABLE_WEEKDAY_SELECT_SERVICE");
                m_netModule.writeLine(day);

                String serviceEnable = m_netModule.readLine();
                String response = m_netModule.readLine();

                // Send the result to the UI thread
                sendToUIThread(response, serviceEnable);

                return true;
            }

            private void sendToUIThread(String response, String serviceEnable) {
                // UI thread: Update UI with the result
                Message message = reservableWeekdaySelectHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("response", response);
                bundle.putString("serviceEnable", serviceEnable);
                message.setData(bundle);
                reservableWeekdaySelectHandler.sendMessage(message);
            }
        }.execute();

        return true;
    }

    @Override
    public void bindNetworkModule(INetworkModule _netModule)
    {
        m_netModule = _netModule;
    }
}
