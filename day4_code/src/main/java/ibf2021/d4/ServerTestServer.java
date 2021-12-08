package ibf2021.d4;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTestServer {
    public static void main( String[] args ) throws IOException{

    ServerSocket server =new ServerSocket(3000);
     System.out.println("Listening at port 3000...");
    Socket socket=server.accept();

    try (InputStream is = socket.getInputStream()) {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
        String string =dis.readUTF();

        while(!string.equals("exit")){
            System.out.println("Message: "+string);
            string =dis.readUTF();
        }
        }
    
    finally{
        socket.close();
        server.close();
    }
        
    
    }
}
