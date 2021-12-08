package com.ibf2021d4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class FortuneCookieClient {
    public static void main( String[] args ) throws IOException{
        FortuneCookieClient mycookieClient = new FortuneCookieClient();
        String[] portAndNum =args[0].split(":");
        Socket socket = mycookieClient.clientSocket(Integer.parseInt(portAndNum[1]));




        try (OutputStream os= socket.getOutputStream()) {
            BufferedOutputStream bos= new BufferedOutputStream(os);
            DataOutputStream dos= new DataOutputStream(bos);
            BufferedReader buff =new BufferedReader(new InputStreamReader(System.in));

            InputStream in = socket.getInputStream();
            BufferedInputStream bin = new BufferedInputStream(in);
            DataInputStream dbin = new DataInputStream(bin);
            BufferedWriter buffwrite = new BufferedWriter(new OutputStreamWriter(System.out));
            


            String string2 = "";
            String string1= "";
            while(!string1.equals("close")){
                string1=buff.readLine();
                if((string1.contains("get-cookie"))||(string1.contains("close"))){
                    dos.writeUTF(string1);
                    dos.flush();
                    string2=dbin.readUTF();
                    System.out.println(string2.replace("cookie-text ",""));
                }
                else{
                    System.out.println("Invalid Argument");
                }


                
            }

        }
        finally{
            
            socket.close();
        }
    
    }
    public Socket clientSocket(int portNum) throws UnknownHostException, IOException{
        return new Socket ("localhost", portNum );
    }



    
}
