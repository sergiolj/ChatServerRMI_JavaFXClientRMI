package br.edu.ucsal.sergiolj.containers.chat.gui.model;

import br.edu.ucsal.sergiolj.containers.chat.shared.ChatServerInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.Config;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private final String userName;
    private Registry registry;
    private ChatServerInterface proxy;

    public Client(String userName) throws RemoteException {
        super();
        this.userName = userName;
        this.proxy.processEntry(this, "./list");
        connectToServer();
    }

    public void connectToServer() {
        try{
            this.registry = LocateRegistry.getRegistry(Config.getIpAddress(),
                    Config.getServerPort());
            this.proxy = (ChatServerInterface) this.registry.lookup(Config.getServerName());
            this.proxy.registerUser(this);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatServerInterface getProxy() {
        return proxy;
    }

    @Override
    public void broadcast(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String userName() throws RemoteException {
        return userName;
    }
}
