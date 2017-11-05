package com.mscheduler.view.meeting;

import com.mscheduler.model.Meeting;
import com.mscheduler.model.MeetingStatus;
import java.util.Scanner;

public class MeetingStatusViewer extends AbstractMeetingViewer {

    //singleton + constructor
    private static MeetingStatusViewer instance;

    static {
        instance = new MeetingStatusViewer();
    }

    public static MeetingStatusViewer getInstance() {
        return instance;
    }

    public MeetingStatusViewer() {
        super();
        this.init();
    }
    //end of singleton

//    @Override
//    public String getText(int id) {
//        Meeting meeting = this.meetingController.detailMeeting(id);
//        if (meeting == null) {
//            return "Meeting not found";
//        } else {
//            return meeting + "";
//        }
//    }
    
    public String getText(Meeting meeting) {
        if (null == meeting.getStatus()) {
            return "";
        }else switch (meeting.getStatus()) {
            case confirmed:
                return meeting
                        + "change status for this meeting to running (y/n)? Your answer : ";
            case running:
                return meeting
                        + "change status for this meeting to finished (y/n)? Your answer : ";
            default:
                return "";
        }
    }
    
    @Override
    public void handleInput(Scanner sc, int meeting_id) {
        //Kamus
        String input;
        Meeting meeting;
        
        //Algoritma
        meeting = this.meetingController.detailMeeting(meeting_id);
        if (meeting != null) {
            if ((meeting.getStatus() == MeetingStatus.confirmed || meeting.getStatus() == MeetingStatus.running)) {
                System.out.print(this.getText(meeting));
                do {
                    input = sc.next();
                    if (input.equalsIgnoreCase("y")) {
                        if (meeting.getStatus() == MeetingStatus.confirmed) {
                            meeting.setStatus(MeetingStatus.running);
                            System.out.println("Status changed to running.");
                        }else if(meeting.getStatus() == MeetingStatus.running){
                            meeting.setStatus(MeetingStatus.finished);
                            System.out.println("Status changed to finished.");
                        }
                        meetingController.updateMeetingStatus(meeting);
                    } else if (!input.equalsIgnoreCase("n")) {
                        System.out.print(this.getHelp());
                    }
                } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
                sc.nextLine();
            }else{
                System.out.println("Cannot change status. Status must confirmed or running");
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
//    public void handleInput(Scanner sc, int meetingId) {
//        System.out.println(this.getText(meetingId));
//        System.out.print("Change Meeting status to : (r)unning / (f)inished\n");
//        String meetingStatus = sc.next();
//
//        if (meetingStatus.equalsIgnoreCase("r") || meetingStatus.equalsIgnoreCase("f")) {
//            Meeting m = meetingController.detailMeeting(meetingId);
//            if (meetingStatus.equalsIgnoreCase("r")) {
//                if (m.getStatus() == MeetingStatus.confirmed) {
//                    m.setStatus(MeetingStatus.running);
//                } else {
//                    System.out.println("Meeting status must confirmed");
//                }
//            } else {
//                if (meetingStatus.equalsIgnoreCase("f")) {
//                    if (m.getStatus() == MeetingStatus.running) {
//                        m.setStatus(MeetingStatus.finished);
//                    } else {
//                        System.out.println("Meeting status must running");
//                    }
//                }
//            }
//            meetingController.updateMeetingStatus(m);
//        }
//        this.exit();
//    }

//    @Override
//    public String getHelp() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
}
