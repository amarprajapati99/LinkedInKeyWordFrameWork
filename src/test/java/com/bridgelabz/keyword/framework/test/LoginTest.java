package com.bridgelabz.keyword.framework.test;

import keyword.engine.pages.KeyWordEngine;
import org.testng.annotations.Test;

public class LoginTest{
    public KeyWordEngine keyWordEngine;

    @Test
    public void loginTest(){
        keyWordEngine = new KeyWordEngine ();
        keyWordEngine.startExecution ("Login_Credentials");

    }
}
