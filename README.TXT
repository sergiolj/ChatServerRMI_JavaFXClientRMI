Comandos para matar processos no Windows
Get-Process | Where-Object { $_.ProcessName -match "Chat|java"}
Stop-Process -Id 10892 -Force


Comandos para criação dos arquivos executáveis.

mvn clean javafx:jlink -Pserver
jpackage --type app-image --name ChatServer --dest target/dist --runtime-image target/chat-server --module br.edu.ucsal.sergiolj.chatrmi/br.edu.ucsal.sergiolj.chatrmi.server.ChatServer --win-console

A opção --win-console mantém uma janela de console para o processo, sem essa opção o jpackage assume que o programa possui
uma interface gráfica.


mvn clean javafx:jlink -Pclient
jpackage --type app-image --name ChatCliente --dest target/dist --runtime-image target/app-cliente --module br.edu.ucsal.sergiolj.chatrmi/br.edu.ucsal.sergiolj.chatrmi.gui.MainApp

mvn clean javafx:jlink -Padmin
jpackage --type app-image --name ChatRMI --dest target/dist --runtime-image target/ChatRMI --module br.edu.ucsal.sergiolj.chatrmi/br.edu.ucsal.sergiolj.chatrmi.server.ChatRMI --win-console

