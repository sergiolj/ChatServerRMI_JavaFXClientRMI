package br.edu.ucsal.sergiolj.containers.chat.shared;

public class Config {
    private static String SERVER_NAME = "RMI_ChatServer";
    private static Integer SERVER_PORT = 1099;
    private static String IP_ADDRESS = "127.0.0.1";

    public static String getServerName() {
        return SERVER_NAME;
    }

    public static void setServerName(String serverName) {
        SERVER_NAME = serverName;
    }

    public static Integer getServerPort() {
        return SERVER_PORT;
    }

    public static void setServerPort(Integer serverPort) {
        SERVER_PORT = serverPort;
    }

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static void setIpAddress(String ipAddress) {
        IP_ADDRESS = ipAddress;
    }
}
