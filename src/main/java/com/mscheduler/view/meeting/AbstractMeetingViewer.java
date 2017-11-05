package com.mscheduler.view.meeting;

import com.mscheduler.view.View;
import com.mscheduler.controller.MeetingController;
import com.mscheduler.controller.SchedulerController;
import com.mscheduler.controller.UserController;
import java.util.Scanner;

public abstract class AbstractMeetingViewer implements View {

    protected MeetingController meetingController;
    protected UserController userController;
//    protected SchedulerController schedulerController;
    protected boolean exitCalled;
    protected String menu;

    public AbstractMeetingViewer() {
        meetingController = MeetingController.getInstance();
        userController = UserController.getInstance();
//        schedulerController = new SchedulerController();
    }

    public boolean isExitCalled() {
        return exitCalled;
    }

    public void setExitCalled(boolean exitCalled) {
        this.exitCalled = exitCalled;
    }
    
    public void init() {
        this.exitCalled = false;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public AbstractMeetingViewer(MeetingController meetingController) {
        this.meetingController = meetingController;
    }
    
    public MeetingController getMeetingController() {
        return meetingController;
    }

    public void setMeetingController(MeetingController meetingController) {
        this.meetingController = meetingController;
    }
    
    @Override
    public String getText() {
        return "";
    }
    

    @Override
    public String getHelp() {
        return "No Help";
    }
    
    public String getText(int param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public void handleInput(Scanner sc,int param){
        System.out.print(this.getText(param));
    }

    @Override
    public void printMenu() {
        System.out.println("Waiting for your command. Type 'help' to show command list");
        System.out.print(">" + this.menu + ">");
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}
