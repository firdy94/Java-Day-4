package com.ibf2021d4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.ibf2021d4.FortuneCookie;

public class FortuneCookieServer {
    public static void main( String[] args ) throws IOException{
        int portNum=Integer.parseInt(args[0]);
        String cookieFilePath=args[1];
        FortuneCookie myCookie = new FortuneCookie();

        ServerSocket server =new ServerSocket(portNum);
        System.out.printf("Listening at port %d...", portNum);
        Socket socket=server.accept();
   
   
       try (InputStream is = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
           DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
           DataOutputStream dout = new DataOutputStream( new BufferedOutputStream(out));
           String str =dis.readUTF();

           while(!str.trim().toLowerCase().equals("close")){
                if(str.contains("get-cookie")){
                    List<String> cookieList=myCookie.openCookieFile(cookieFilePath);
                    dout.writeUTF("cookie-text "+myCookie.getCookie(cookieList));
                    dout.flush();


                    str =dis.readUTF();
           }
           }
        }
        finally{
        socket.close();
        server.close();
    }
    
    }

    
}
