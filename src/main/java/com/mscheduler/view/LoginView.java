package com.mscheduler.view;

import java.util.Scanner;

public abstract class  LoginView implements View {

    public String getText() {
        return null;
    }

    public void handleInput(Scanner sc) {

    }

    public String getHelp() {
        return null;
    }

    public void exit() {

    }

    @Override
    public void printMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
