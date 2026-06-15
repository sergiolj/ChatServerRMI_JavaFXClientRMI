package br.edu.ucsal.sergiolj.containers.chat.gui.service;

public class NetworkDataChecker {

    public static boolean validadeData(String name, String ip, String port) {
        return isIpv4Valid(ip) && isPortNumberValid(port) && isServerNameValid(name);
    }

    /**
     * O nome de um servidor no register.lookup não pode ser mais de uma palavra ou string única sem espaços.
     * @param name Nome do servidor RMI.
     * @return
     */
    private static boolean isServerNameValid(String name) {
        //REGEX retorna verdadeiro se não houver espaços no texto.
        return name.matches("\\S+");
    }

    private static boolean isIpv4Valid(String ip){
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        //REGEX para determinar se o endereço de IP e válido.
        String regexIPv4 = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(regexIPv4);
    }

    private static boolean isPortNumberValid(String port) {
        try{
            //Verifica se o TextField fornecido é um número.
            int portNumber = Integer.parseInt(port);
            //Verifica se a porta especificada está no intervalo de portas válidas de rede.
            return portNumber > 0 && portNumber <= 65535;
        }catch (NumberFormatException e){
            return false;
        }
    }




}
