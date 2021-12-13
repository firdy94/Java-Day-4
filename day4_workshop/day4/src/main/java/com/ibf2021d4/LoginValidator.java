package com.ibf2021d4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import java.util.Iterator;


public class LoginValidator {
    private static HashMap<String,String> userList=new HashMap<>();
    private static String currentPath = new File("").getAbsolutePath()+"/"+"users.txt";
    private BufferedWriter buffwrite;
    
    
    private static HashMap<String,String> loadUserList() throws IOException{
        File inputpathDBFile = Paths.get(currentPath).toFile();
        if ((inputpathDBFile.exists())){
            BufferedReader bfread = new BufferedReader(new FileReader(currentPath));
            Stream<String> myStream = bfread.lines().sequential();
            for (String user: myStream.toList()){
                String[] userAndPassword = user.split("/");
                userList.put(userAndPassword[0], userAndPassword[1]);
        }
        bfread.close();
    }
        return userList; 
}


    public boolean checkPassword(String s) throws IOException{
        loadUserList();
        String[] userAndPassword=s.split("/");

        if(userList.isEmpty()){
            return false;
        }
        if(userList.containsKey(userAndPassword[0])){
            if (userList.get(userAndPassword[0]).equals(userAndPassword[1])){
                return true;
            }
        }
        return false;
    }

    public boolean addPassword(String s) throws IOException{
        String[] userAndPassword=s.split("/");
        loadUserList();
        userList.put(userAndPassword[0], userAndPassword[1]);
        return true;
    }

    public  void saveUsersList() throws IOException{

        try{
            buffwrite = new BufferedWriter(new FileWriter(currentPath));
            List<String> UsersArrayList = new ArrayList<>();
            String[] Users = userList.toString().split(",");

            for (String user:Users){
                user=user.strip();
                user=user.replace("=","/");
                user=user.replace("{","");
                user=user.replace("}","");
                UsersArrayList.add(user);
            }
            Stream<String> myStream =UsersArrayList.stream().sequential();
            Iterator<String> myIterate = myStream.iterator();
            while(myIterate.hasNext()){
                buffwrite.write(myIterate.next());
                buffwrite.newLine();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{   
            buffwrite.flush();
            buffwrite.close(); 
        }
    }
}



