package br.edu.ucsal.sergiolj.containers.chat.server.service;

import br.edu.ucsal.sergiolj.containers.chat.shared.ChatServerInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import br.edu.ucsal.sergiolj.containers.chat.shared.Config;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerRemoteObject extends UnicastRemoteObject implements ChatServerInterface  {

    private final List<ClientInterface> clients = new ArrayList<>();

    public ChatServerRemoteObject() throws RemoteException {
        super();
    }

    @Override
    public void registerUser(ClientInterface user) throws RemoteException {
        clients.add(user);
        System.out.println("New user " + user.userName() + " registered.");
        notifyAll();
    }

    @Override
    public synchronized void processEntry(ClientInterface user, String msg) throws RemoteException {
        if(msg.startsWith("./")){
            runCommand(user, msg);
        }else{
            if(!msg.equals("")){
                broadcast(user, msg);
            }
        }
    }

    public void broadcast(ClientInterface user, String msg) throws RemoteException {
        System.out.println("Distribuindo mensagem de " + user.userName() + " para todos: " + msg);
        for (ClientInterface client : clients) {
            try{
                client.broadcast("["+user.userName()+"] " + msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }
    public void runCommand(ClientInterface user, String cmd) throws RemoteException {
        if(cmd.startsWith("./")){
            switch (cmd){
                case "./list":
                    StringBuilder sb = new StringBuilder("Usuários conectados: ");
                    for (ClientInterface client : clients) {
                        sb.append("[").append(client.userName()).append("]");
                    }
                    user.broadcast(sb.toString());
                    break;
                case "./x":
                    String userExiting = user.userName();
                    clients.remove(user);
                    broadcast(null, "[" + Config.getServerName() + "] " + userExiting + "se desconectou do servidor.");
                    user.broadcast("Você foi desconectado com sucesso!");
                    break;
            }
        }else{
            user.broadcast("Comando desconhecido: " + cmd +", tente ./list ou ./x");
        }
    }

    public void notifyNewUser(ClientInterface user) throws RemoteException {
        for (ClientInterface client : clients) {
            try{
                if(!client.userName().equals(user.userName())){
                    client.broadcast("["+user.userName()+"] Novo usuário registrado.");
                }
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }
}
