package com.mscheduler.view.user;

import com.mscheduler.view.View;
import com.mscheduler.controller.UserController;
import java.util.Scanner;

public class AbstractUserViewer implements View {

    protected UserController uc;
    protected boolean exitCalled;
    protected String menu;
    
//    private UserController userController;

    public AbstractUserViewer(){
        this.uc = UserController.getInstance();
    }
    
    public void init() {
        this.exitCalled = false;
    }

    @Override
    public String getText() {
        return "";
    }

    public String getText(String param) {
        return "";
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printMenu() {
        System.out.println("Waiting for your command. Type 'help' to show command list");
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

    public void handleInput(Scanner sc, String param) {
        System.out.print(this.getText(param));
    }

}
