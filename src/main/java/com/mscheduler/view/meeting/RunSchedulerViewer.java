package com.mscheduler.view.meeting;

import com.mscheduler.controller.MeetingController;
import com.mscheduler.model.Meeting;
import com.mscheduler.model.MeetingStatus;
import com.mscheduler.model.Schedule;
import java.util.Scanner;

public class RunSchedulerViewer extends AbstractMeetingViewer {
    //singleton + constructor
    
    private static RunSchedulerViewer instance;

    static {
        instance = new RunSchedulerViewer();
    }

    public static RunSchedulerViewer getInstance() {
        return instance;
    }

    public RunSchedulerViewer() {
        super();
        this.init();
    }

    //end of singleton
    
    public String getText(Meeting meeting) {
        return meeting
                    + "Run scheduler for this meeting (y/n)? Your answer : ";
    }

    @Override
    public void handleInput(Scanner sc, int meeting_id) {
        //Kamus
        String input;
        Meeting meeting;
        int respon;
        
        //Algoritma
        meeting = this.meetingController.detailMeeting(meeting_id);
        if (meeting != null) {
            if (meeting.getStatus() == MeetingStatus.negotiating) {
                System.out.print(this.getText(meeting));
                
                do {
                    input = sc.next();
                    if (input.equalsIgnoreCase("y")) {
                        respon = this.meetingController.runScheduler(meeting_id);
                        switch(respon){
                            case 0 : System.out.println("Failed run scheduler.");break;
                            case 1 : System.out.println("Meeting confirmed.");break;
                            case 2 : System.out.println("Meeting canceled.");break;
                        }
                    } else if (!input.equalsIgnoreCase("n")) {
                        System.out.print(this.getHelp());
                    }
                } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
                sc.nextLine();
            }else{
                System.out.println("Cannot change status. Status must negotiating");
            }
        }else{
             System.out.println("Invalid meeting id");
        }
        
    }

    @Override
    public String getHelp() {
        return "(y/n)\n>";
    }
    
//    @Override
//    public String getText(int meetingId) {
//        Meeting m = this.meetingController.detailMeeting(meetingId);
//        String detailMeeting = "";
//        
//        if(m != null) {
//            detailMeeting = m.toString();
//        }
//        
//        return detailMeeting;
//    }

//    @Override
//    public void handleInput(Scanner sc,int meetingId) {
//        
//        String detailMeeting = getText(meetingId);
//        Meeting m = meetingController.detailMeeting(meetingId);
//        
//        if(detailMeeting.equals("")){
//            System.out.println("Meeting Not Found.");
//        }
//        else {
//            System.out.print(getText(meetingId));
//            System.out.println("");
//            System.out.println("Run scheduler for this meeting ? (y/n) : ");
//            String answer = sc.nextLine();
//            
//            while(!answer.equalsIgnoreCase("y") || !answer.equalsIgnoreCase("n")){
//                System.out.println("Invalid Input");
//                System.out.println("Run scheduler for this meeting ? (y/n) : ");
//                String answer = sc.nextLine();
//            }
//            
//            if(answer.equalsIgnoreCase("y")) {
//                Schedule sch =schedulerController.runSchedule(meetingId);
//                m.setAgreed_time(sch.getDate());
//                m.setStatus(MeetingStatus.confirmed);
//                
////                meetingController.
//                
//            }
//        }
//    }

}
