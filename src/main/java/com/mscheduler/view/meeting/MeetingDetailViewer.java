package com.mscheduler.view.meeting;

import com.mscheduler.model.Meeting;
import java.util.Scanner;

public class MeetingDetailViewer extends AbstractMeetingViewer {

    //singleton + constructor
    private static MeetingDetailViewer instance;

    static {
        instance = new MeetingDetailViewer();
    }

    public static MeetingDetailViewer getInstance() {
        return instance;
    }

    public MeetingDetailViewer() {
        super();
        this.init();
    }
    //end of singleton

    @Override
    public String getText(int id) {
        Meeting meeting = this.meetingController.detailMeeting(id);
        if (meeting == null) {
            return "Meeting not found";
        } else {
            return meeting + "\n";
        }
    }

    @Override
    public void handleInput(Scanner sc, int meetingId) {
        System.out.print(this.getText(meetingId));
        this.exit();
    }

    @Override
    public String getHelp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
