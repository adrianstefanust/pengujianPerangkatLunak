package com.mscheduler.view.user;

import com.mscheduler.view.View;
import com.mscheduler.controller.UserController;
import java.util.Scanner;

public class AbstractUserView implements View {

    protected boolean exitCalled;
    protected String menu;
    
    private UserController userController;

    public void init() {
        this.exitCalled = false;
    }

    @Override
    public String getText() {
        return "Wrong command\n";
    }

    public String getText(int param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printMenu() {
        System.out.print(">" + this.menu + ">");
    }

    @Override
    public String getHelp() {
        return "No Help";
    }

    @Override
    public void exit() {
        this.exitCalled = true;
    }
    
    /**
     * @see com.mscheduler.view.View#handleInput(java.util.Scanner)
     */
    @Override
    public void handleInput(Scanner sc) {
        System.out.print(this.getText());
    }

    public void handleInput(Scanner sc, int param) {
        System.out.print(this.getText(param));
    }

}
