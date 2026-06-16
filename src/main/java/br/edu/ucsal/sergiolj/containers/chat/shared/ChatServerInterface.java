package br.edu.ucsal.sergiolj.containers.chat.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

    public interface ChatServerInterface extends Remote {
        void registerUser(ClientInterface user) throws RemoteException;
        void processEntry(ClientInterface user, String msg) throws RemoteException;
        void onlineUsersChangedNotifyAll() throws RemoteException;
        void disconnectUser(ClientInterface user) throws RemoteException;
    }
