package com.mscheduler.view.invitation;

import com.mscheduler.Config;
import com.mscheduler.Utilities;
import com.mscheduler.controller.MeetingController;
import com.mscheduler.model.DateRange;
import com.mscheduler.model.Meeting;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvitationAcceptViewer extends AbstractInvitationViewer {
    MeetingController mc;

    //singleton + constructor
        private static InvitationAcceptViewer instance;

        static{
            instance = new InvitationAcceptViewer();
        }

        public static InvitationAcceptViewer getInstance(){
          return instance;
        }
        
        private InvitationAcceptViewer() {
            super();
            mc = MeetingController.getInstance();
            this.init();
        }
        
        public void init(){
            super.init();
            this.menu = "accept-invitation";
        }
    //end of singleton
    @Override
    public String getText(int meeting_id) {
        Meeting m = this.mc.detailMeeting(meeting_id);
        return "You have selected to accept invitation \""
                + m.getTitle() + "\".\n"
                + "Proposed date range : "+m.getProposed_date_range().toString2()+".\n"
                + "Are you sure (y/n)? Your answer : ";
    }

    @Override
    public void handleInput(Scanner sc, int meeting_id) {
        if (ic.isMeetingIdValid(meeting_id,true)) {
            super.handleInput(sc, meeting_id);
        
            //Kamus
            String input;
            String date;
            List<DateRange> dateRangeList;

            //Algoritma
            do {
                input = sc.next();
                if (input.equalsIgnoreCase("y")) {
                    System.out.println("Successfully accept invitation.\n"
                            + "Please enter availability date["+Config.DATETIME_FORMAT_INPUT_STRING+" - "+Config.DATETIME_FORMAT_INPUT_STRING+"]:");
                    sc.nextLine();
                    dateRangeList = new ArrayList<DateRange>();
                    do {
                        this.printMenu();
                        date = sc.nextLine();
                        if (Utilities.validDateRange(date)) {
                            dateRangeList.add(new DateRange(date));
                            System.out.println("Please input next date.");
                        }else if(date.equalsIgnoreCase("help")){
                            System.out.println(this.getHelp());
                        }else if(!date.equalsIgnoreCase("end")){
                            System.out.println("Invalid date input.");
                        }else if(date.equalsIgnoreCase("end") && dateRangeList.isEmpty()){
                            System.out.println("Date cannot empty.");
                        }
                    } while (!date.equalsIgnoreCase("end") || dateRangeList.isEmpty());
                    if (this.ic.acceptInvitation(meeting_id,dateRangeList)) {
                        System.out.println("Data saved! You can re-entry availability with this menu.");
                    } else {
                        System.out.println("Data not saved!");
                    }
                } else if (!input.equalsIgnoreCase("n")) {
                    System.out.print(this.getHelp2());
                }
            } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
//            sc.nextLine();
        }else{
            System.out.println("Invalid meeting id");
        }
    }

    @Override
    public String getHelp() {
        return "["+Config.DATETIME_FORMAT_INPUT_STRING+" "+Config.DATETIME_FORMAT_INPUT_STRING+"] : Format date\n"
                + "end : Finish input date\n";
    }
    
    public String getHelp2() {
        return "(y/n)\n>";
    }

}
