package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 7878;

        try{
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to the server");
            Thread receiveThread = new Thread(()-> {
                try{
                    while(true) {
                        String serverMessage = reader.readLine();
                        if(serverMessage == null) {
                            System.out.println("Server Disconnected");
                            break;
                        }
                        System.out.println(serverMessage);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();
            while(true) {
                String clientMessage = scanner.nextLine();
                if(clientMessage.equalsIgnoreCase("quit")) {
                    break;
                }
                writer.println(clientMessage);
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
