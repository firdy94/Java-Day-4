package com.ibf2021d4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class FortuneCookieClient {

    private BufferedInputStream buffInS;
    private BufferedOutputStream buffOutS;
    private BufferedReader buffRead;
    private DataInputStream dataInS;
    private DataOutputStream dataOutS;

    public static void main( String[] args ) throws IOException{
        FortuneCookieClient mycookieClient = new FortuneCookieClient();
        String[] portAndNum =args[0].split(":");
        Socket socket = mycookieClient.createClientSocket(Integer.parseInt(portAndNum[1]));
        mycookieClient.clientInteraction(socket);
    
    }
    public Socket createClientSocket(int portNum) throws IOException{
        return new Socket ("localhost", portNum );
    }

    public  void clientInteraction(Socket socket) throws IOException{
        try (OutputStream outS= socket.getOutputStream();InputStream inS = socket.getInputStream()) {
            buffOutS= new BufferedOutputStream(outS);
            dataOutS = new DataOutputStream(buffOutS);
            buffRead =new BufferedReader(new InputStreamReader(System.in));
            buffInS = new BufferedInputStream(inS);
            dataInS = new DataInputStream(buffInS);
            String clientOut= "";
            String clientIn= "";
            while(!clientIn.equals("close")){
                clientIn=buffRead.readLine();
                if((clientIn.contains("get-cookie"))||(clientIn.contains("close"))){
                    dataOutS.writeUTF(clientIn);
                    dataOutS.flush();
                    clientOut=dataInS.readUTF();
                    System.out.println(clientOut.replace("cookie-text ",""));
                }
                else if (clientIn.contains("/")){
                    dataOutS.writeUTF(clientIn);
                    dataOutS.flush();
                    clientOut=dataInS.readUTF();
                    System.out.println(clientOut);  
                }  
                else{
                    System.out.println("Invalid Argument");
                }
            }
        }
        catch(EOFException e){
            System.out.println("Connection closed!");

        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            socket.close();
        }
    }
    public void closeStreams(BufferedReader buffRead,DataInputStream dataInS, DataOutputStream DataOutputStream){
        try{
        if (buffRead!=null){
            buffRead.close();
        }
        if(dataInS!=null){
            dataInS.close();
        }
        if(dataOutS!=null){
            dataOutS.close();
        }
    }
    catch(IOException e){
        e.printStackTrace();
    }
    }
}