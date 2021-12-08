package ibf2021.d4;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerTestClient {

    public static void main( String[] args ) throws IOException{
        Socket socket = new Socket ("localhost", 3000 );


    try (OutputStream os= socket.getOutputStream()) {
        BufferedOutputStream bos= new BufferedOutputStream(os);
        DataOutputStream dos= new DataOutputStream(bos);
        // Scanner myScan =new Scanner(System.in);
        BufferedReader buff =new BufferedReader(new InputStreamReader(System.in));

        String string="";
        while(!string.equals("exit")){
            string=buff.readLine();
            dos.writeUTF(string);
            dos.flush();
        }

    }
    finally{
        
        socket.close();
    }
    
    }
}