package br.edu.ucsal.sergiolj.chatrmi.server;

import br.edu.ucsal.sergiolj.chatrmi.shared.NetworkDataChecker;

public class Config {
    private static String SERVER_NAME = "RMI_ChatServer";
    private static Integer SERVER_PORT = 1099;

    public static String getServerName() {
        return SERVER_NAME;
    }

    public static Integer getServerPort() {
        return SERVER_PORT;
    }

    public static void setServerName(String serverName) {
        SERVER_NAME = serverName;
    }

    public static void setServerPort(Integer serverPort) {
        SERVER_PORT = serverPort;
    }

    public static boolean processArgs(String[] args) {
        if(args.length == 0){
            System.out.println("Para iniciar e parar o servidor use o programa ChatRMI.exe");
            return false;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("start")){
            return true;
        }

        if(args[0].equalsIgnoreCase("start")){
            for(int i=0; i< args.length; i++) {
                if (args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("-help")) {
                    System.out.println("""
                        Opções
                        -n -> Definir o nome do servidor
                        -p -> Definir uma porta específica para o servidor.
                        -h -> Menu de ajuda.
                        """);
                    return false;
                }
                if (args[i].equalsIgnoreCase("-n") && (i + 1) < args.length) {
                    Config.setServerName(args[i + 1]);
                    i++;
                } else if (args[i].equalsIgnoreCase("-p") && (i + 1) < args.length) {
                    try {
                        int port = Integer.parseInt(args[i + 1]);
                        if (NetworkDataChecker.isPortNumberValid(args[i + 1])) {
                            Config.setServerPort(Integer.parseInt(args[i + 1]));
                            i++;
                        } else {
                            System.out.println("Parâmetro inválido para -p. Porta de rede com parâmetros inválidos.");
                            return false;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Erro. Formato de atributo para porta inválido.");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
