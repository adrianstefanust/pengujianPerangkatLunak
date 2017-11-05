package com.mscheduler.view.user;

import com.mscheduler.model.User;
import java.util.Scanner;

public class UserDeleteViewer extends AbstractUserViewer {

    //singleton + constructor
    private static UserDeleteViewer instance;

    static {
        instance = new UserDeleteViewer();
    }

    public static UserDeleteViewer getInstance() {
        return instance;
    }

    private UserDeleteViewer() {
        super();
    }
    //end of singleton

    public String getText(String email) {
        return "You have selected to delete user \""
                + email + "\".\n";
    }

//    public void handleInput(Scanner sc, String a) {
//        //kamus
//        int stat;
//        String yes;
//        
//        //init
//        stat = 0;
//        
//        //algoritma
//        System.out.print("Are you sure (y/n)?");
//        yes = sc.nextLine();
//        if(yes.equalsIgnoreCase("y")){
//            stat = uc.delUser(a);  //not exist/0,suces1,2admin
//            if (stat == 0) {
//                System.out.println("Email not exist !");
//            } else if (stat == 1) {
//                System.out.println("Delete user success !");
//            } else if (stat == 2) {
//                System.out.println("Last Admin, can't deleted !");
//            } else {
//                System.out.println("User exist in invitation, can't remove !");
//            }
//        }
//    }
    @Override
    public void handleInput(Scanner sc, String email) {
        User usrE;
        usrE = new User();
        if (!usrE.BooleanReadUser(email)) {
            System.out.println("Email not found.");
            return;
        }  
        super.handleInput(sc, email);
        //Kamus
        int stat;
        String input;
        

        //init
        stat = 0;

        //Algoritma
                      
        System.out.println("Are you sure (y/n)? Your answer : ");
        do {
            input = sc.next();
            if (input.equalsIgnoreCase("y")) {
                stat = uc.delUser(email);  //not exist/0,suces1,2admin
                if (stat == 0) {
                    System.out.println("Email not exist !");
                } else if (stat == 1) {
                    System.out.println("Delete user success !");
                } else if (stat == 2) {
                    System.out.println("Last Admin, can't deleted !");
                } else {
                    System.out.println("User exist in invitation, can't remove !");
                }
            } else if (!input.equalsIgnoreCase("n")) {
                System.out.print(this.getHelp());
            }
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
        sc.nextLine();
    }

    @Override
    public String getHelp() {
        return "(y/n)\n>";
    }

}
