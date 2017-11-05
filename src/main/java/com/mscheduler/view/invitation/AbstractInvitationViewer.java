package com.mscheduler.view.invitation;

import com.mscheduler.view.View;
import com.mscheduler.controller.InvitationController;
import java.util.Scanner;

public abstract class AbstractInvitationViewer implements View {

    protected InvitationController ic;
    protected boolean exitCalled;
    protected String menu;
    
    public AbstractInvitationViewer(){
        this.ic = InvitationController.getInstance();
    }
    
    public void init() {
        this.exitCalled = false;
    }

    @Override
    public String getText() {
        return "Invalid command\n";
    }
    
    public String getText(int param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void handleInput(Scanner sc){
        System.out.print(this.getText());
    }
    
    public void handleInput(Scanner sc,int param){
        System.out.print(this.getText(param));
    }

}
