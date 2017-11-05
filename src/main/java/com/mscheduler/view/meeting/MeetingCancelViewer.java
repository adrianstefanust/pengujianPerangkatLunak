package com.mscheduler.view.meeting;

import com.mscheduler.model.Meeting;
import com.mscheduler.model.MeetingStatus;
import java.util.Scanner;

public class MeetingCancelViewer extends AbstractMeetingViewer {
    
    //singleton + constructor
        private static MeetingCancelViewer instance;

        static{
            instance = new MeetingCancelViewer();
        }

        public static MeetingCancelViewer getInstance(){
          return instance;
        }
        
        public MeetingCancelViewer() {
            super();
            this.init();
        }
    //end of singleton
    
    @Override
    public String getText(int id) {
//        System.out.println("msk1");
        Meeting meeting = this.meetingController.detailMeeting(id);
        return meeting.toString() + "\n";
    }

    @Override
    public void handleInput(Scanner sc,int meeting_id) {
        String respon;
        String d = this.getText(meeting_id);
        Meeting m = this.meetingController.detailMeeting(meeting_id);
        
        if(m.getStatus() == MeetingStatus.canceled || m.getStatus() == MeetingStatus.finished || m.getStatus() == MeetingStatus.running){
            System.out.println("Error, meeting is already "+m.getStatus());
        }
        else {
            System.out.println(this.getText(meeting_id));
        
            System.out.print("Are you sure to cancel this meeting? (y/n) : ");
            respon = sc.next();

            while(!respon.toLowerCase().equals("y") && !respon.toLowerCase().equals("n")) {
                System.out.println(this.getHelp());
                System.out.print("Are you sure to cancel this meeting? (y/n) : ");
                respon = sc.next();
            }

            if(respon.toLowerCase().equals("y")) {
                this.meetingController.cancelMeeting(meeting_id);
                System.out.println("Meeting Canceled");
            }
        }
    }

    @Override
    public String getHelp() {
        return "(y/n)";
    }
}
