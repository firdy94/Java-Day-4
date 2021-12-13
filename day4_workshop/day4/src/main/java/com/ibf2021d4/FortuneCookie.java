package com.ibf2021d4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class FortuneCookie {

    private String currentPath = new File("").getAbsolutePath();
    private List<String> cookieList =new ArrayList<>();
    private Random numGen= new Random();

    public static void main(String[] args) {

        
    }
    public List<String> openCookieFile(String s) throws IOException{
        currentPath=currentPath+"/"+s;
        File inputpathDBFile = Paths.get(currentPath).toFile();

        if ((inputpathDBFile.exists())){
            BufferedReader bfread = new BufferedReader(new FileReader(currentPath));
            Stream<String> myStream = bfread.lines().sequential();
            cookieList = myStream.toList();

            bfread.close();
        }
        return cookieList;

    }


    public String getCookie(List<String> cList){

        int randNum = numGen.nextInt(cList.size());

        return cList.get(randNum);
        
    }
    
    public String getCurrentPath() {
        return this.currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public List<String> getCookieList() {
        return this.cookieList;
    }

    public void setCookieList(List<String> cookieList) {
        this.cookieList = cookieList;
    }

    public Random getNumGen() {
        return this.numGen;
    }

    public void setNumGen(Random numGen) {
        this.numGen = numGen;
    }
}

