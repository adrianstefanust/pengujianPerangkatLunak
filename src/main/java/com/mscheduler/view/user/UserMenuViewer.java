/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.view.user;

import com.mscheduler.view.user.*;
import com.mscheduler.controller.UserController;
import com.mscheduler.Config;
import com.mscheduler.ViewMap;
import java.io.Console;
import static java.lang.System.console;
import java.util.List;
import java.util.Scanner;

public class UserMenuViewer extends AbstractUserViewer {
    //singleton + constructor
    private static UserMenuViewer instance;

    static {
        instance = new UserMenuViewer();
    }

    public static UserMenuViewer getInstance() {
        return instance;
    }

    private UserMenuViewer() {
        super();
        this.init();
    }
    //end of singleton
    public String handleInputLogin(Scanner sc) {
        String res, stat, input, inputPassword;
        boolean flag, flagRole;
        Console cnsl = null;

        flag = false;
        flagRole = false;
        cnsl = System.console();
        res = "";
        input = "";
        /*  
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
         */
        System.out.println("Welcome to Meeting Scheduler");
        while (!flag) {
            System.out.print("Please enter your email : ");
            input = sc.nextLine();
            System.out.print("Please enter your password : ");
            inputPassword = sc.nextLine();
//            char[] pwd = cnsl.readPassword("Please enter your password :");
//            inputPassword = new String(pwd);

            stat = uc.checkLogin(input, inputPassword);
            if (stat.equals("0")) {
                System.out.println("Invalid email or password, please try again.");
            } else if (stat.equals("2")) {
                while (!flagRole) {
                System.out.println("Please select a role ((a)dministrator,(i)nitiator,(p)articipant) :");
                    flag = true;
                    res = sc.nextLine();
                    if (res.equalsIgnoreCase("a") || res.equalsIgnoreCase("i") || res.equalsIgnoreCase("p")) {
                        flagRole = true;
                        success(res);
                    }
                }
            } else {        //1
                while (!flagRole) {
                    System.out.print("Please select a role ((i)nitiator,(p)articipant) :");
                    flag = true;
                    res = sc.nextLine();
                    if (res.equalsIgnoreCase("i") || res.equalsIgnoreCase("p")) {
                        flagRole = true;
                        success(res);
                    }
                }
            }
        }
        return res+"~"+input;
    }

    public void success(String res) {
        if (res.equalsIgnoreCase("a")) {
            System.out.println("You have signed in as Administrator.");
        } else if (res.equalsIgnoreCase("i")) {
            System.out.println("You have signed in as Initiator.");
        } else {
            System.out.println("You have signed in as Participant.");
        }
//        System.out.println("help<enter> : to get help");
    }

    @Override
    public void handleInput(Scanner sc) {
        //Kamus
        String input;
        String[] inputParse;
        AbstractUserViewer view;

        //Algoritma
        while (!this.exitCalled) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Waiting for your command. Type 'help' to show command list");
            System.out.print(">");
            input = sc.nextLine();
            inputParse = input.split(" ");
            if (inputParse[0].equals("help")) {
                System.out.print(this.getHelp());
            } else if (inputParse[0].equals("logout")) {
                System.out.println("Thank you, Logging out...");
//                System.out.println("Silahkan login jika ingin masuk kembali atau exit untuk keluar dari program..");
                System.out.println("");
                this.exit();
            } else {
                view = this.getView(inputParse[0]);
                if (view != null) {
                    view.init();
                    if (inputParse.length == 1) {
                        view.handleInput(sc);
                    } else {
                        view.handleInput(sc, inputParse[1]);
                    }
                }
            }
        }
    }

    public AbstractUserViewer getView(String command) {
        List<ViewMap> viewMap = Config.userViewMap;
        ViewMap findFirst = viewMap.stream()
                .filter(x -> x.getTrigger().equals(command))
                .findFirst().orElse(null);
        if (findFirst != null) {
            return (AbstractUserViewer) findFirst.getView();
        } else {
            return null;
        }
    }

    @Override
    public String getHelp() {
        return "list-user : Get list user\n"
                + "add-user : Add user to database\n"
                + "delete-user <email_user> : Remove user from database\n"
                + "edit-user <email_user> : Edit user in database\n"
                + "logout : Exit to login menu\n";
    }

}
