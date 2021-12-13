package com.ibf2021d4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class FortuneCookieTest {

@Test
public void openCookieFileTest(){
    List<String> actualCookieList =new ArrayList<>();
    try{
    FortuneCookie myCookie =new FortuneCookie();
    myCookie.openCookieFile("db/cookielist.txt");
    actualCookieList= myCookie.getCookieList();
    }
    catch(IOException e){
        e.printStackTrace();
    }
    String[] cookies = {"Chocolate Chip",
        "Peanut Butter",
        "White Chocolate",
        "Dark Chocolate",
        "Oatmeal",
        "Butter",
        "Wheat",
        "Gingerbread",
        "Cream Sandwich"};
    List<String> expectedCookieList = Arrays.asList(cookies);
    assertEquals(expectedCookieList, actualCookieList);


}
@Test
public void openCookieFileTest_Exception(){
        FortuneCookie myCookie =new FortuneCookie();
        List<String> actualCookieList=new ArrayList<>();
        try {
            actualCookieList = myCookie.openCookieFile("db/test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(new ArrayList<String>(),actualCookieList);

    }
@Test
public void getCookieTest(){
    String actualCookie ="";
    try{
    FortuneCookie myCookie =new FortuneCookie();
    myCookie.openCookieFile("db/cookielist.txt");
    actualCookie = myCookie.getCookie(myCookie.getCookieList());
    }
    catch(IOException e){
        e.printStackTrace();
    }
    String[] cookies = {"Chocolate Chip",
        "Peanut Butter",
        "White Chocolate",
        "Dark Chocolate",
        "Oatmeal",
        "Butter",
        "Wheat",
        "Gingerbread",
        "Cream Sandwich"};
    List<String> expectedCookieList = Arrays.asList(cookies);
    assertTrue(expectedCookieList.contains(actualCookie));
}  
}
