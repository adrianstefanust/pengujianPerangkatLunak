package com.mscheduler.view.invitation;

import com.mscheduler.controller.MeetingController;
import java.util.Scanner;

public class InvitationRejectViewer extends AbstractInvitationViewer {
    
    
    //singleton + constructor
        private static InvitationRejectViewer instance;

        static{
            instance = new InvitationRejectViewer();
        }

        public static InvitationRejectViewer getInstance(){
          return instance;
        }
        
        private InvitationRejectViewer() {
            super();
        }
    //end of singleton
        
    @Override
    public String getText(int meeting_id) {
        String meetingTitle;

        meetingTitle = this.ic.getMeetingTitle(meeting_id);
        return "You have selected to reject invitation \""
                + meetingTitle + "\".\n"
                + "Are you sure (y/n)? Your answer : ";
    }

    @Override
    public void handleInput(Scanner sc, int meeting_id) {
        if (ic.isMeetingIdValid(meeting_id,true)) {
            super.handleInput(sc, meeting_id);
            //Kamus
            String input;

            //Algoritma
            do {
                input = sc.next();
                if (input.equalsIgnoreCase("y")) {
                    if (this.ic.rejectInvitation(meeting_id)) {
                        System.out.println("Data saved!");
                    }else{
                        System.out.println("Data not saved!");
                    }
                } else if (!input.equalsIgnoreCase("n")) {
                    System.out.print(this.getHelp());
                }
            } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
            sc.nextLine();
        }else{
             System.out.println("Invalid meeting id");
        }
        
    }

    @Override
    public String getHelp() {
        return "(y/n)\n>";
    }
}