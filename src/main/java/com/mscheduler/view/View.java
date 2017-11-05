package com.mscheduler.view;

import java.util.Scanner;

public interface View {

    public abstract String getText();

    public abstract void handleInput(Scanner sc);

    public abstract String getHelp();

    public abstract void exit();

    public abstract void printMenu();

}
