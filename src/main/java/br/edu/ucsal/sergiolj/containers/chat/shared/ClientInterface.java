package br.edu.ucsal.sergiolj.containers.chat.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    void broadcast(String message) throws RemoteException;
    String userName() throws RemoteException;
}
