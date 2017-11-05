package com.mscheduler.view.user;

import com.mscheduler.Config;
import com.mscheduler.Utilities;
import java.util.Scanner;
import java.util.List;
import com.mscheduler.model.User;

public class UserListViewer extends AbstractUserViewer {
    private int page;
    private int maxPage;

    //singleton + constructor
        private static UserListViewer instance;

        static{
            instance = new UserListViewer();
        }

        public static UserListViewer getInstance(){
          return instance;
        }
        
        private UserListViewer() {
            super();
            this.init();
        }
    //end of singleton
    
    
    @Override
    public void init() {
        super.init();
        this.page = 1;
        this.maxPage = 0;
        this.menu = "list-user";
    }

    @Override
    public String getText() {
        return this.getText(this.page);
    }
    
    public String getText(int page) {
        //Kamus
        String str,detail;
        List<User> list_user;
        
        //Algoritma
        list_user = uc.listUser();
        detail = "";
        
        boolean first = true;
        for(User u : list_user){
            if (first) {
                detail += u.getName() +" : "+ u.getEmail();
                first = false;
            }else{
                detail += "\n"+u.getName() +" : "+ u.getEmail();
            }
        }
        
        this.maxPage = Utilities.countMaxPage(detail);
        str = "";
        if (this.maxPage == 1) {
            str = Utilities.getPage(detail,1)+"\n";
            this.exit();
        }else {
            str = "-----------\n"
                + "Page " + page + " of "+this.maxPage+"\n"
                + "-----------\n";
            
            if (page < this.maxPage) {
                str += Utilities.getPage(detail,page)+"\n"
                    + "----more----\n";
            }else{
                str += Utilities.getPage(detail,page)+"\n";
            }
        }
        return str;
    }
    
    public void handleInput(Scanner sc) {
        super.handleInput(sc);
        //Kamus
        String input;
        String[] splitString;
        int pageSearch;
        
        //Algoritma
        while (!this.exitCalled) {
            this.printMenu();
            input = sc.nextLine();
            switch (input) {
                case "end":
                    this.exit();
                    break;
                case "help":
                    System.out.print(this.getHelp());
                    break;
                case "n":
                    this.page++;
                    if (this.page <= maxPage) {
                        System.out.print(this.getText());
                    }else{
                        this.exit();
                    }
                    break;
                default:
                    splitString = input.split(" ");
                    if (splitString[0].equalsIgnoreCase("p")) {
                        pageSearch = Integer.parseInt(splitString[1]);
                        if (pageSearch <= maxPage) {
                            this.page = pageSearch;
                            System.out.print(this.getText());
                        }
                    }
                    break;
            }
        }
        System.out.println("Exiting...");
   
    }
    
    @Override
    public String getHelp() {
        return "n: next page\n"
                + "p <number>: go to page <number>\n"
                + "help: help menu\n"
                + "end: exit view list\n";
    }
}
