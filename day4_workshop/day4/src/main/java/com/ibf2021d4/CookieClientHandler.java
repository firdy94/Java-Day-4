package com.ibf2021d4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ibf2021d4.FortuneCookie;


public class CookieClientHandler implements Runnable {

    private final Socket socket;
    private String cookieFilePath;


    public CookieClientHandler(Socket socket, String cookieFilePath){
        this.socket=socket;
        this.cookieFilePath=cookieFilePath;
    }

    @Override
    public void run(){

        FortuneCookie myCookie = new FortuneCookie();


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
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
            socket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }



    }



    public static void main(String[] args) {
        
    }
    
}
