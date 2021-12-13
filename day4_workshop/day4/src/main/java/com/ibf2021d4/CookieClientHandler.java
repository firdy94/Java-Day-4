package com.ibf2021d4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;





public class CookieClientHandler implements Runnable {

    private final Socket socket;
    private String cookieFilePath;
    private boolean isLogin;
    private DataInputStream dataInS;
    private DataOutputStream dataOutS;
    private String serverIn;

    private FortuneCookie myCookie = new FortuneCookie();
    private LoginValidator myLoginValidator =new LoginValidator();

    private Instant timeAfteroperation;
    private Instant timeBeforeOperation;
    private long timeElapsed; 


    public CookieClientHandler(Socket socket, String cookieFilePath){
        this.socket=socket;
        this.cookieFilePath=cookieFilePath;
    }

    @Override
    public void run(){
        try (InputStream is = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
            dataInS = new DataInputStream(new BufferedInputStream(is));
            dataOutS = new DataOutputStream( new BufferedOutputStream(out));
            serverIn =dataInS.readUTF();
            isLogin=myLoginValidator.checkPassword(serverIn);
            clientInputParser(myCookie, myLoginValidator);
        }
    
        catch(IOException e){
            e.printStackTrace();

        }
        finally{
            closeConnections(socket,dataInS, dataOutS);

        }

        }


    public void closeConnections(Socket socket,DataInputStream dataInS, DataOutputStream dataOutS){
         try{
            if(socket!=null){
                socket.close();
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
    public void clientInputParser(FortuneCookie myCookie, LoginValidator myLoginValidator) throws IOException{
        while(!serverIn.trim().toLowerCase().equals("close")){
            if(serverIn.contains("get-cookie")){
                if(isLogin){
                    timeBeforeOperation=Instant.now();
                    List<String> cookieList=myCookie.openCookieFile(cookieFilePath);
                    dataOutS.writeUTF("cookie-text "+myCookie.getCookie(cookieList));
                    dataOutS.flush();
                    serverIn =dataInS.readUTF();
                    timeAfteroperation=Instant.now();
                    timeElapsed= Duration.between(timeBeforeOperation, timeAfteroperation).toMillis();
                    if (timeElapsed>=30000){
                        if (serverIn.equals("close")){
                            break;
                        }
                        isLogin=false;
                        dataOutS.writeUTF("Your session has timed out due to inactivity. Please login again.");
                        dataOutS.flush();
                        serverIn=dataInS.readUTF();
                        isLogin=myLoginValidator.checkPassword(serverIn);
                    }
                }
                else{
                    timeBeforeOperation=Instant.now();
                    dataOutS.writeUTF("You are not logged in. Please enter your username and password e.g. abc/123");
                    dataOutS.flush();
                    serverIn =dataInS.readUTF();
                    boolean prevUserCheck=myLoginValidator.checkPassword(serverIn);
                    if (prevUserCheck){
                        dataOutS.writeUTF(String.format("Welcome back %s!",serverIn.split("/")[0]));
                        dataOutS.flush();
                    }
                    else{
                        myLoginValidator.addPassword(serverIn);
                        myLoginValidator.saveUsersList();
                        dataOutS.writeUTF(String.format("Welcome %s!",serverIn.split("/")[0]));
                        dataOutS.flush();
                    }
                    isLogin=true;
                    serverIn =dataInS.readUTF();
                    timeAfteroperation=Instant.now();
                    timeElapsed= Duration.between(timeBeforeOperation, timeAfteroperation).toMillis();
                    if (timeElapsed>=30000){
                        if (serverIn.equals("close")){
                            break;
                        }
                        isLogin=false;
                        dataOutS.writeUTF("Your session has timed out due to inactivity. Please login again.");
                        dataOutS.flush();
                        serverIn=dataInS.readUTF();
                        isLogin=myLoginValidator.checkPassword(serverIn);

                    }
                }
            }
            else if(isLogin==true){
                timeBeforeOperation=Instant.now();
                dataOutS.writeUTF(String.format("Welcome back %s!",serverIn.split("/")[0]));
                dataOutS.flush();
                serverIn =dataInS.readUTF();
                timeAfteroperation=Instant.now();
                timeElapsed= Duration.between(timeBeforeOperation, timeAfteroperation).toMillis();
                if (timeElapsed>=30000){
                    if (serverIn.equals("close")){
                        break;
                    }
                    isLogin=false;
                    dataOutS.writeUTF("Your session has timed out due to inactivity. Please login again.");
                    dataOutS.flush();
                    serverIn=dataInS.readUTF();
                    isLogin=myLoginValidator.checkPassword(serverIn);
                }
            }
            else if(isLogin==false){
                timeBeforeOperation=Instant.now();
                dataOutS.writeUTF("You are not registered. Please choose a username and password e.g. abc/123");
                dataOutS.flush();
                serverIn =dataInS.readUTF();
                boolean prevUserCheck=myLoginValidator.checkPassword(serverIn);
                if (prevUserCheck){
                    dataOutS.writeUTF(String.format("Welcome back %s!",serverIn.split("/")[0]));
                    dataOutS.flush();
                }
                else{
                    myLoginValidator.addPassword(serverIn);
                    myLoginValidator.saveUsersList();
                    dataOutS.writeUTF(String.format("Welcome %s!",serverIn.split("/")[0]));
                    dataOutS.flush();
                }
                isLogin=true;
                serverIn =dataInS.readUTF();
                timeAfteroperation=Instant.now();
                timeElapsed= Duration.between(timeBeforeOperation, timeAfteroperation).toMillis();
                if (timeElapsed>=30000){
                    if (serverIn.equals("close")){
                        break;
                    }
                    isLogin=false;
                    dataOutS.writeUTF("Your session has timed out due to inactivity. Please login again.");
                    dataOutS.flush();
                    serverIn=dataInS.readUTF();
                    isLogin=myLoginValidator.checkPassword(serverIn);
                }
            }
        }
    }
    }

