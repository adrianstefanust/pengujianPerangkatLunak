/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.view.invitation;

import com.mscheduler.Config;
import com.mscheduler.ViewMap;
import java.util.List;
import java.util.Scanner;

public class InvitationMenuViewer extends AbstractInvitationViewer {
    
    //singleton + constructor
        private static InvitationMenuViewer instance;

        static{
            instance = new InvitationMenuViewer();
        }

        public static InvitationMenuViewer getInstance(){
          return instance;
        }
        
        private InvitationMenuViewer() {
            super();
            this.init();
        }
    //end of singleton
        
    @Override
    public void handleInput(Scanner sc) {
        //Kamus
        String input;
        String[] inputParse;
        AbstractInvitationViewer view;

        //Algoritma
        while (!this.exitCalled) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Waiting for your command. Type 'help' to show command list");
            System.out.print(">");
            input = sc.nextLine();
            inputParse = input.split(" ");
            if (inputParse[0].equalsIgnoreCase("help")) {
                System.out.print(this.getHelp());
            }else if (inputParse[0].equals("logout")) {
                System.out.println("Thank you, Logging out...");
                System.out.println("");
                this.exit();
            }else{
                view = this.getView(inputParse[0]);
                if (view != null) {
                    view.init();
                    if (inputParse.length == 1) {
                        view.handleInput(sc);
                    }else{
                        view.handleInput(sc,Integer.parseInt(inputParse[1]));
                    }
                }
            }
            
        }
    }
    
    public AbstractInvitationViewer getView(String command){
        List<ViewMap> viewMap = Config.invitationViewMap;
        ViewMap findFirst = viewMap.stream()
                .filter(x->x.getTrigger().equalsIgnoreCase(command))
                .findFirst().orElse(null);
        if (findFirst != null) {
            return (AbstractInvitationViewer) findFirst.getView();
        }else{
            return null;
        }
        
    }
    
    @Override
    public String getHelp() {
        return "list-invitation : Get list invitation\n"
                + "detail-invitation <meeting_id> : Get detail invitation by meeting_id\n"
                + "accept-invitation <meeting_id> : Accept invitation by meeting_id\n"
                + "reject-invitation <meeting_id> : Reject invitation by meeting_id\n"
                + "logout : Exit to login menu\n";
    }

}
