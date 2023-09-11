package server;

import exception_handler.ChatAppException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;
import java.io.*;



public class Server {

    private ServerSocket serverSocket;
    private Socket socket;

    private BufferedReader readClient, readServer;

    private PrintWriter write;

    private Runnable t1, t2; // implement multi threading

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server() {

        try{
            serverSocket = new ServerSocket(7878);
            logger.severe("Server has started...");
            logger.info("Waiting for connection ...");
            socket = serverSocket.accept(); // when server is accepting the request, data passed through will be read by BufferedReader obj
            InputStream inputStream = socket.getInputStream(); // hold the incoming stream of data into this object.
            OutputStream outputStream = socket.getOutputStream();
            // read data from the input stream
            readClient = new BufferedReader(new InputStreamReader(inputStream));
            write = new PrintWriter(outputStream);

            // create functions to perform reading and writing
            readingData();
            writingData();

        }catch (IOException e) {
            throw new ChatAppException();
        }
    }

    public void readingData() {
        t1 = () -> {
            logger.info("Reader Started...");
            while(true) {
                try {
                    String clientMessage = readClient.readLine();
                    if(clientMessage.equalsIgnoreCase("quit")){
                        logger.info("Client has initiated chat termination.");
                        break;
                    }
                    logger.info("Client : " +clientMessage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(t1).start();
    }

    public void writingData() {
        t2 = () -> {
            while(true) {
                try{
                    readServer = new BufferedReader(new InputStreamReader(System.in)); // read data from console
                    String serverResponse = readServer.readLine();
                    write.println(serverResponse);
                    write.flush();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(t2).start();
    }


    public static void main(String[] args) {
        Server sr = new Server();
    }
}




















