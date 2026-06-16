package br.edu.ucsal.sergiolj.containers.chat.gui.model;

import br.edu.ucsal.sergiolj.containers.chat.gui.controller.MainViewController;
import br.edu.ucsal.sergiolj.containers.chat.shared.ChatServerInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.Config;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private final String userName;
    private Registry registry;
    private ChatServerInterface proxy;
    private final MainViewController controller;

    public Client(String userName, MainViewController controller) throws RemoteException {
        super();
        this.userName = userName;
        this.controller = controller;
        connectToServer();
        //this.proxy.processEntry(this, "./list");
    }

    /**
     * Executa a conexão com o servidor com base nas informações estáticas do Config.class
     * Falta implementar a lista de ChatServers que aparece no ChoiceBox utilizado no ConnectController
     */
    private void connectToServer() {
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
        return this.userName;
    }


    @Override
    public void onlineUsersListChanged(List<String> onlineUsersList) throws RemoteException {
        Platform.runLater(()->{
            if(controller!=null) {
                controller.refreshOnlineUsers(onlineUsersList);
            }
        });
    }

    @Override
    public void disconnect(MainViewController controller) throws RemoteException {
        proxy.disconnectUser(this);
    }

    @Override
    public String toString() {
        return userName;
    }
}
