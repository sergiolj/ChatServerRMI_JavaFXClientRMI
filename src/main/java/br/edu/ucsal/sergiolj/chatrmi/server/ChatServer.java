package br.edu.ucsal.sergiolj.chatrmi.server;

import br.edu.ucsal.sergiolj.chatrmi.server.service.ChatServerRemoteObject;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
    public static void main(String[] args) {
        if(Config.processArgs(args)){
            try{
                ChatServerRemoteObject chatServer = new ChatServerRemoteObject();
                Registry registry = LocateRegistry.createRegistry(Config.getServerPort());
                registry.rebind(Config.getServerName(), chatServer);

                System.out.println("Server RMI \n[Server Name: " + Config.getServerName() + "] port: "
                        + Config.getServerPort() + " ] online..");

                Runtime.getRuntime().addShutdownHook(new Thread(()->{
                    System.out.println("\n["+Config.getServerName()+"] Sinal de encerramento recebido.");
                    System.out.println("\n["+Config.getServerName()+"] Desvinculando RMI e liberando portas...");

                    try{
                        Naming.unbind("rmi://localhost:"+ Config.getServerPort() + "/" + Config.getServerName());
                        UnicastRemoteObject.unexportObject(chatServer, true);
                        UnicastRemoteObject.unexportObject(registry, true);

                    }catch (Exception e){
                        System.err.println("Erro ao desvincular: " + e.getMessage());
                    }
                    System.out.println("\n["+Config.getServerName()+"] desligado com segurança.");
                }));

            } catch (RemoteException e) {
                System.out.println("Erro ao iniciar servidor RMI. " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
