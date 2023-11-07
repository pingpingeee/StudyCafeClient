package com.example.main;

import com.example.mysecondproject.IntroActivity;
import com.example.mysecondproject.ServerCon;
import com.example.network.NetworkModule;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkThread extends StudyThread {
    public NetworkThread() {
        serviceQueue = new ConcurrentLinkedQueue<IService>();
    }

    private ConcurrentLinkedQueue<IService> serviceQueue;
    @Override
    public void run(){
        Socket clientSocket = ServerCon.connectToServer();
        IntroActivity.networkModule = new NetworkModule(clientSocket);
        IntroActivity.networkModule.writeByte(1);
        while(super.isRun()){
            while(serviceQueue.size() > 0){
                serviceQueue.poll().tryExecuteService();
            }
        }
    }
    public void requestService(IService iService){
        serviceQueue.offer(iService);
    }
}
