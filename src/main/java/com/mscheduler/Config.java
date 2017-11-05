/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler;

import com.mscheduler.view.invitation.*;
import com.mscheduler.view.meeting.MeetingCancelViewer;
import com.mscheduler.view.meeting.MeetingCreateViewer;
import com.mscheduler.view.meeting.MeetingDetailViewer;
import com.mscheduler.view.meeting.MeetingEditViewer;
import com.mscheduler.view.meeting.MeetingListViewer;
import com.mscheduler.view.meeting.MeetingStatusViewer;
import com.mscheduler.view.meeting.RenegotiateMeetingViewer;
import com.mscheduler.view.meeting.RunSchedulerViewer;
import java.time.format.DateTimeFormatter;
import com.mscheduler.view.user.*;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final int ROW_PER_PAGE = 10;
    
    public static final String DATA_USER_INDEX = "data/user.json";
    public static final String DATA_MEETING_INDEX = "data/meeting.json";
    public static final String DATA_INVITATION_INDEX = "data/invitation.json";
    public static final String DATA_MEETING_LAST_INDEX = "data/meeting_index.json";

    public static final String DATE_FORMAT_INPUT_STRING = "ddMMyyyy";
    public static final String DATE_FORMAT_OUTPUT_STRING = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern(Config.DATE_FORMAT_INPUT_STRING);
    public static final DateTimeFormatter DATE_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(Config.DATE_FORMAT_OUTPUT_STRING);
    
    public static final String DATETIME_FORMAT_INPUT_STRING = "dd/MM/yyyy HH";
    public static final String DATETIME_FORMAT_OUTPUT_STRING = "dd/MM/yyyy HH";
    public static final DateTimeFormatter DATETIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern(Config.DATETIME_FORMAT_INPUT_STRING);
    public static final DateTimeFormatter DATETIME_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(Config.DATETIME_FORMAT_OUTPUT_STRING);

    public static List<ViewMap> userViewMap;
    public static List<ViewMap> meetingViewMap;
    public static List<ViewMap> invitationViewMap;
    
    static {
        Config.userViewMap = new ArrayList<>();
        Config.userViewMap.add(new ViewMap("add-user",UserAddViewer.getInstance()));
        Config.userViewMap.add(new ViewMap("delete-user",UserDeleteViewer.getInstance()));
        Config.userViewMap.add(new ViewMap("edit-user",UserEditViewer.getInstance()));
        Config.userViewMap.add(new ViewMap("list-user", UserListViewer.getInstance()));
        
        Config.meetingViewMap = new ArrayList<>();
        Config.meetingViewMap.add(new ViewMap("list-meeting",MeetingListViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("detail-meeting",MeetingDetailViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("edit-meeting",MeetingEditViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("create-meeting",MeetingCreateViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("run-scheduler",RunSchedulerViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("cancel-meeting",MeetingCancelViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("status-meeting",MeetingStatusViewer.getInstance()));
        Config.meetingViewMap.add(new ViewMap("renegotiate-meeting",RenegotiateMeetingViewer.getInstance()));
        
        Config.invitationViewMap = new ArrayList<>();
        Config.invitationViewMap.add(new ViewMap("list-invitation",InvitationListViewer.getInstance()));
        Config.invitationViewMap.add(new ViewMap("detail-invitation",InvitationDetailVewer.getInstance()));
        Config.invitationViewMap.add(new ViewMap("accept-invitation",InvitationAcceptViewer.getInstance()));
        Config.invitationViewMap.add(new ViewMap("reject-invitation",InvitationRejectViewer.getInstance()));
    }
    
}
