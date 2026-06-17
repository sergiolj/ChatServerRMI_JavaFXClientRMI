package br.edu.ucsal.sergiolj.containers.chat.gui.model;

import br.edu.ucsal.sergiolj.containers.chat.gui.controller.MainViewController;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ServerSpecs;
import br.edu.ucsal.sergiolj.containers.chat.shared.ChatServerInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private final String userName;
    private ChatServerInterface proxy;
    private final MainViewController controller;

    public Client(String userName, MainViewController controller, ServerSpecs server) throws RemoteException, NotBoundException {
        super();
        this.userName = userName;
        this.controller = controller;
        connectToServer(server);
    }

    /**
     * Executa a conexão com o servidor com base nas configurações do servidor injetadas.
     *
     */
    private void connectToServer(ServerSpecs server) throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry(server.getIP(),
                    server.getPort());
            this.proxy = (ChatServerInterface) registry.lookup(server.getName());
            this.proxy.registerUser(this);
    }


    public void sendMessageCommand(String message) throws RemoteException {
        proxy.processEntry(this, message);
    }

    @Override
    public void broadcast(String message) throws RemoteException {
        Platform.runLater(()-> {
            if(controller!=null) {
                controller.publishMessage(message);
            }
        });
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
