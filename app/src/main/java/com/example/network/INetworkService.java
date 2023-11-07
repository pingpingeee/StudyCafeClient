package com.example.network;
import com.example.main.*;

public interface INetworkService extends IService
{
    boolean tryExecuteService();

    void bindNetworkModule(INetworkModule _netModule);
}
