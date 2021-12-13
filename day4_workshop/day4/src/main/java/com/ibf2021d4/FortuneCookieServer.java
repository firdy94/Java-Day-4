package com.ibf2021d4;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.ibf2021d4.FortuneCookie;
import com.ibf2021d4.CookieClientHandler;


public class FortuneCookieServer {
    public static void main( String[] args ) throws IOException{
        int portNum=Integer.parseInt(args[0]);
        String cookieFilePath=args[1];

        // ExecutorService threadPool = Executors.newFixedThreadPool(3);    //Multi-threading lines commented out
        ServerSocket server =new ServerSocket(portNum);
        System.out.printf("Listening at port %d...", portNum);
    
        try{
            // while(true){
            Socket socket=server.accept();

            CookieClientHandler clientSocket = new CookieClientHandler(socket, cookieFilePath);  
            // threadPool.submit(clientSocket);

            Thread nThread= new Thread(clientSocket);
            nThread.start();
            // }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            server.close();
        }
        
    
}

    
}
