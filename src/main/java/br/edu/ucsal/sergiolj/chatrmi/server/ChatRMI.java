package br.edu.ucsal.sergiolj.chatrmi.server;

import br.edu.ucsal.sergiolj.chatrmi.shared.ChatServerInterface;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;


public class ChatRMI {
    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "start":
                startServerConsole(args);
                break;
            case "stop":
                if (args.length < 4) {
                    System.out.println("Sintaxe incorreta! " +
                            "Para parar use chatrmi <ip_servidor> <porta> <senha>\\nSenha default: admin\" ");
                } else {
                    shutdownServer(args[1], args[2], args[3], args[4]);
                }
                break;
            default:
                System.out.println("Comando desconhecido: " + command);
                printHelp();
        }
    }

    private static void shutdownServer(String name, String ip, String portNumber, String password) {
            int port = Integer.parseInt(portNumber);

            try{
                String url = "rmi://" + ip + ":" + port + "/" + name;
                ChatServerInterface adminStub = (ChatServerInterface) Naming.lookup(url);

                System.out.println("Conectado ao servidor " + ip + ". Enviando comando de shutdown...");

                adminStub.shuttdown(password);

                System.out.println("Comando aceito! O servidor será desligado com segurança.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    private static void startServerConsole(String[] args) {
        try{
            System.out.println("Utilizando o SO para iniciar o servidor em uma nova Command Line Interface CLI...");

            if(Config.processArgs(args)){
            /* O construtor de processos aciona o SO para executar o cli (cmd.exe). A flag '/c' determina o fechamento
            do cli após a finalização do comando, a flag start cria uma nova janela e um novo PDI e executa dentro dessa
            janela o executável determinado.*/

                List<String> command = new ArrayList<>(List.of("cmd.exe", "/c", "start",
                        "\"Servidor RMI - github.com/sergiolj\"", "ChatServer.exe"));
                command.addAll(List.of(args));

                System.out.println(command);
                ProcessBuilder builder = new ProcessBuilder(command);
            /* Define o diretório de trabalho do processo, o diretório de trabalho é usado, por exemplo,
             para os logs e para localizar o arquivo servers.txt, além de determinar o
             diretório de busca pelo programa executável.*/
                builder.directory(new File("."));
                builder.start();
            }else{
                System.out.println("Argumentos inválidos.");
            }

        }catch (IOException e){
            System.out.println("Erro ao iniciar servidor. " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("""
                # Servidor de Chat implementado usando Java RMI /Java FX
                # Autor Sérgio Lopes Júnior - https://www.github.com/sergiolj
                *************************************************************
                LISTA DE COMANDOS
                # start                               -> Inicializa o servidor com o nome default 'RMI_ChatServer' e porta 1099.
                # start -n <name> -p <port>           -> Inicializa o servidor com um nome e porta específicos.
                # stop <name> <ip> <port> <password>  -> Inicia o shutdown controlado do servidor.
                """);
    }
}
