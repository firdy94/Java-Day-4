package com.ibf2021d4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FortuneCookieServer {
    public static void main( String[] args ) throws IOException{
        int portNum=Integer.parseInt(args[0]);
        String cookieFilePath=args[1];
        ExecutorService threadPool = Executors.newFixedThreadPool(3);    //Multi-threading lines commented out
        ServerSocket server =new ServerSocket(portNum);
        
        System.out.printf("Listening at port %d...", portNum);
        try{
            while(!server.isClosed()){
            server.setSoTimeout(12000);
            Socket socket=server.accept();
            CookieClientHandler clientSocket = new CookieClientHandler(socket, cookieFilePath);
            threadPool.submit(clientSocket);
            // Thread nThread= new Thread(clientSocket);
            // nThread.start();
            }
        }
        catch(SocketTimeoutException s){
            System.out.println("Server shutting down due to inactivity");
            threadPool.shutdown();
            server.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
    
}
    
}
