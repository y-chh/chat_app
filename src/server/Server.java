package server;

import exception_handler.ChatAppException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;



public class Server {

    private ServerSocket serverSocket;
    private Socket socket;

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server() {

        try{
            serverSocket = new ServerSocket(7878);
            logger.severe("Server has started...");
            logger.info("Waiting for connection ...");


        }catch (IOException e) {
            throw new ChatAppException();
        }
    }



}
