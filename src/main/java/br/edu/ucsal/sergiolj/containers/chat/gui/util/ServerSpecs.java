package br.edu.ucsal.sergiolj.containers.chat.gui.util;

public class ServerSpecs {
    private String name;
    private String IP;
    private int port;


    public ServerSpecs(String name, String IP, int port){
        this.name = name;
        this.IP = IP;
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.IP + ": " + this.port + ")";
    }
}
