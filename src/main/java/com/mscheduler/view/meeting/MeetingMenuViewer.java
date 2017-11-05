/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.view.meeting;

import com.mscheduler.Config;
import com.mscheduler.ViewMap;
import com.mscheduler.view.invitation.AbstractInvitationViewer;
import com.mscheduler.view.invitation.InvitationMenuViewer;
import java.util.List;
import java.util.Scanner;

public class MeetingMenuViewer extends AbstractMeetingViewer {

    //singleton + constructor
    private static MeetingMenuViewer instance;

    static {
        instance = new MeetingMenuViewer();
    }

    public static MeetingMenuViewer getInstance() {
        return instance;
    }

    private MeetingMenuViewer() {
        super();
        this.init();
    }
    //end of singleton
    
    @Override
    public void handleInput(Scanner sc) {
        //Kamus
        String input;
        String[] inputParse;
        AbstractMeetingViewer view;

        System.out.println("Waiting for your command. Type 'help' to show command list");
        //Algoritma
        while (!this.exitCalled) {
            System.out.print("> ");
            input = sc.nextLine();
            inputParse = input.split(" ");
            if (inputParse[0].equals("help")) {
                System.out.println(this.getHelp());
            } else {
                if (inputParse[0].equals("logout")) {
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
                            view.handleInput(sc, Integer.parseInt(inputParse[1]));
                        }
                    }
                }
            }
            System.out.println("\nWaiting for your command. Type 'help' to show command list");
        }
    }

    public AbstractMeetingViewer getView(String command) {
        List<ViewMap> viewMap = Config.meetingViewMap;
        ViewMap findFirst = viewMap.stream()
                .filter(x -> x.getTrigger().equals(command))
                .findFirst().orElse(null);
        if (findFirst != null) {
            return (AbstractMeetingViewer) findFirst.getView();
        } else {
            return null;
        }

    }

    @Override
    public String getHelp() {
        return "create-meeting : Create New Meeting\n"
                + "edit-meeting <meeting-id> : Edit Meeting\n"
                + "list-meeting : List all meeting\n"
                + "detail-meeting <meeting-id> : View Meeting Details \n"
                + "status-meeting <meeting-id> : Change Meeting Status \n"
                + "cancel-meeting <meeting-id> : Cancel Meeting\n"
                + "renegotiate-meeting <meeting-id> : Renegotiate meeting\n"
                + "run-scheduler <meeting-id> : Run Scheduler for specific meeting\n"
                + "logout : Exit to login menu";
    }
}
