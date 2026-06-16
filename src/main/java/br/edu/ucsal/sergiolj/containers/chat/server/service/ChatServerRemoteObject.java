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
    public synchronized void registerUser(ClientInterface user) throws RemoteException {
        clients.add(user);
        System.out.println("New user " + user.userName() + " registered.");
        notifyAll();
        onlineUsersChangedNotifyAll();
        broadcast(user,  " entrou na sala.");
    }

    /**
     * Método básico de entrada para mensagens do usuário, nesse comando são testados se o texto digitado é um texto
     * simples ou contém instruções (comandos) para o servidor.
     *
     * @param user
     * @param msg
     * @throws RemoteException
     */
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

    /**
     * Cria uma lista de String com o nome dos clientes online.
     *
     * @return
     * @throws RemoteException
     */
    public List<String> getOnlineClients() throws RemoteException {
        List<String> users = new ArrayList<>();
        if (!clients.isEmpty()) {
            for (ClientInterface user : clients) {
                users.add(user.userName());
            }
        }
        return users;
    }

    public void broadcast(ClientInterface user, String msg) throws RemoteException {
        System.out.println(user.userName() + " broadcast to all: " + msg);
        for (ClientInterface client : clients) {
            try{
                client.broadcast(user.userName() + ": " + msg);
            }catch (RemoteException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Lista de comandos implementados no servidor para respostas padronizadas ao usuário.
     * Todos os comandos começam com "./" isso sinaliza ao servidor de que a mensagem não é uma string e sim um comando.
     *
     * @param user
     * @param cmd
     * @throws RemoteException
     */
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

    /**
     * Notifica todos os usuários conectados quando a lista de usuários for alterada.
     * TODO Se um cliente cair ou se desligar a lista não é alterada e isso gera um erro nesse CallBack que
     * precisa ser corrigido
     *
     */
    @Override
    public void onlineUsersChangedNotifyAll() throws RemoteException {
        List<String> onlineUsers = getOnlineClients();
        for (ClientInterface client : clients) {
            try {
                client.onlineUsersListChanged(onlineUsers);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized void disconnectUser(ClientInterface user) throws RemoteException {
        clients.remove(user);
        System.out.println("User " + user.userName() + " disconnected.");
        notifyAll();
        onlineUsersChangedNotifyAll();
    }
}

