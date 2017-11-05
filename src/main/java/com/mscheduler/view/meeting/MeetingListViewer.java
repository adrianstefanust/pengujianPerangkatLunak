package com.mscheduler.view.meeting;

import com.mscheduler.Utilities;
import com.mscheduler.model.InvitationStatus;
import com.mscheduler.model.ListInvitationViewModel;
import com.mscheduler.model.ListMeetingViewModel;
import com.mscheduler.model.MeetingStatus;
import com.mscheduler.view.invitation.InvitationListViewer;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MeetingListViewer extends AbstractMeetingViewer {
    private int page;
    private int maxPage;
    
    //singleton + constructor
        private static MeetingListViewer instance;

        static{
            instance = new MeetingListViewer();
        }

        public static MeetingListViewer getInstance(){
          return instance;
        }
        
        public MeetingListViewer() {
            super();
            this.init();
        }
    //end of singleton
        
    @Override
    public void init() {
        super.init();
        this.page = 1;
        this.maxPage = 0;
        this.menu = "list-meeting ";
    }
    
    @Override
    public String getText() {
        return getText(this.page);
    }
    
    @Override
    public String getText(int page) {
        String str,detail, stringList;
        List<ListMeetingViewModel> list_meeting;
        
        //Algoritma
        list_meeting = meetingController.listMeetingView();
        detail = "";
        for(MeetingStatus status : MeetingStatus.values()){
            detail += status+":\n";
            stringList = list_meeting.stream()
                .filter(x -> x.getMeeting_status() == status)
                .map(p -> "(id="+p.getMeeting_id() +") "+ p.getMeeting_title()+"\n")
                .collect(Collectors.joining());
            if (stringList.equals("")) {
                detail += "Empty\n";
            }else{
                detail += stringList;
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

    @Override
    public void handleInput(Scanner sc) {
        super.handleInput(sc);
        //Kamus
        String input;
        String[] splitString;
        int pageSearch;
        //Algoritma
        while (!this.exitCalled) {
//            System.out.print(this.getText());
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
                    if (splitString[0].equals("p")) {
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
