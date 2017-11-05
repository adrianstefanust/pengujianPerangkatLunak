package com.mscheduler.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mscheduler.Config;
import com.mscheduler.model.Invitation;
import com.mscheduler.model.InvitationStatus;
import com.mscheduler.model.LastIndex;
import com.mscheduler.model.ListInvitationViewModel;
import com.mscheduler.model.ListMeetingViewModel;
import com.mscheduler.model.Meeting;
import com.mscheduler.model.MeetingStatus;
import com.mscheduler.model.Schedule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MeetingController extends AbstractController {

    //singleton + constructor
    private static MeetingController instance;
    private InvitationController ic;

    static {
        instance = new MeetingController();
        instance.ic = InvitationController.getInstance();
    }

    public static MeetingController getInstance() {
        return instance;
    }

    private MeetingController() {
        super();
    }

//    private MeetingController(InvitationController ic) {
//        super();
//        this.ic = ic;
//    }
    //end of singleton
    public boolean createMeeting(Meeting meeting) {
        List<Meeting> meetings = this.loadMeetings();
        meeting.setId(this.getLastMeetingIndex().getLastNo());
        meeting.setStatus(MeetingStatus.negotiating);

        LastIndex l = this.getLastMeetingIndex();
        l.setLastNo(l.getLastNo() + 1);
        meetings.add(meeting);

        List<Invitation> invitations = ic.loadInvitations();
        for (int i = 0; i < meeting.getImportant_participants().size(); i++) {
            Invitation iv = new Invitation();
            iv.setMeeting_id(meeting.getId());
            iv.setStatus(InvitationStatus.waiting);
            iv.setTo(meeting.getImportant_participants().get(i));

            invitations.add(iv);
        }

        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            Invitation iv = new Invitation();
            iv.setMeeting_id(meeting.getId());
            iv.setStatus(InvitationStatus.waiting);
            iv.setTo(meeting.getParticipants().get(i));
            invitations.add(iv);
        }

        if (this.saveMeetings(meetings) && ic.saveInvitations(invitations) && this.updateLastMeetingIndex(l)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Meeting> listMeeting() {
        return this.loadMeetings().stream()
                .collect(Collectors.toList());
    }

    public Meeting detailMeeting(int meeting_id) {
        return this.loadMeetings().stream()
                .filter(x -> x.getId() == meeting_id)
                .findFirst().orElse(null);
    }

    public boolean editMeeting(Meeting m) {
        List<Meeting> meetings = this.loadMeetings();
        int indexFound = -1;

        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getId() == m.getId()) {
                indexFound = i;
            }
        }

        if (indexFound != -1) {
            meetings.set(indexFound, m);
            List<Invitation> invitations = ic.loadInvitations();
            List<Invitation> invTemp = new ArrayList();
            List<Invitation> invTemp2 = new ArrayList();

            for (int i = 0; i < invitations.size(); i++) {
                if (invitations.get(i).getMeeting_id() == m.getId()) {
                    invTemp.add(invitations.get(i));
                }
            }
            
            for (int i = 0; i < m.getImportant_participants().size(); i++) {
                Invitation iv = new Invitation();
                iv.setMeeting_id(m.getId());
                iv.setStatus(InvitationStatus.waiting);
                iv.setTo(m.getImportant_participants().get(i));
                invTemp2.add(iv);
            }

            for (int i = 0; i < m.getParticipants().size(); i++) {
                Invitation iv = new Invitation();
                iv.setMeeting_id(m.getId());
                iv.setStatus(InvitationStatus.waiting);
                iv.setTo(m.getParticipants().get(i));
                invTemp2.add(iv);
            }

            if (invTemp.size() > 0) {
                for (int i = 0; i < invTemp.size(); i++) {
                    int j = 0;
                    while (j < invTemp2.size()) {
                        if (invTemp.get(i).getTo().equals(invTemp2.get(j).getTo())) {
                            invitations.add(invTemp.get(i));
                            invTemp2.remove(j);
                            invTemp.remove(i);
                        }
                        j++;
                    }
                }
                for (int j = 0; j < invTemp2.size(); j++) {
                    invitations.add(invTemp2.get(j));
                }
            } else {
                for (int j = 0; j < invTemp2.size(); j++) {
                    invitations.add(invTemp2.get(j));
                }
            }

            if (this.saveMeetings(meetings) && ic.saveInvitations(invitations)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean updateMeeting(Meeting m, MeetingStatus ms, InvitationStatus is) {
        boolean invite = true;
        List<Meeting> meetings = this.loadMeetings();

        int indexFound = -1;

        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).getId() == m.getId()) {
                indexFound = i;
            }
        }

        if (indexFound != -1) {
            meetings.set(indexFound, m);
            if (is != null) {
                List<Invitation> invitations = ic.loadInvitations();
                List<Invitation> filter_invitations;
                
                filter_invitations = invitations.stream()
                        .filter(x -> x.getMeeting_id() == m.getId())
                        .collect(Collectors.toList());
                for (Invitation inv : filter_invitations) {
                    
                    if (is == InvitationStatus.confirmed && inv.getStatus() != InvitationStatus.accepted) {
                        inv.setStatus(InvitationStatus.rejected);
                    }else if(is == InvitationStatus.finished && inv.getStatus() == InvitationStatus.rejected){
                        
                    }else{
                        inv.setStatus(is);
                    }
                }
                invite = this.ic.saveInvitations(invitations);
            }
        }

        return this.saveMeetings(meetings) && invite;
    }

    public boolean updateMeetingStatus(Meeting m) {
        if (m == null) {
            return false;
        } else {
            MeetingStatus ms = m.getStatus();
            switch (ms) {
                case canceled:
                    return this.updateMeeting(m, ms, InvitationStatus.canceled);
                case confirmed:
                    return this.updateMeeting(m, ms, InvitationStatus.confirmed);
                case finished:
                    return this.updateMeeting(m, ms, InvitationStatus.finished);
                default:
                    return this.updateMeeting(m, ms, null);
            }
        }
    }

    public boolean cancelMeeting(int meeting_id) {
        Meeting m = this.loadMeetings().stream()
                .filter(x -> x.getId() == meeting_id).findFirst().orElse(null);
        m.setStatus(MeetingStatus.canceled);
        return this.updateMeeting(m, MeetingStatus.canceled, InvitationStatus.canceled);
//        List<Meeting> meetings = this.loadMeetings();
//        Meeting m1 = this.loadMeetings().stream()
//                .filter(x -> x.getId() == meeting_id)
//                .collect(Collectors.toList()).get(0);
//        m1.setStatus(MeetingStatus.canceled);
//
//        int indexFound = -1;
//        for (int i = 0; i < meetings.size(); i++) {
//            if (meetings.get(i).getId() == meeting_id) {
//                indexFound = i;
//            }
//        }
//        if (indexFound != -1) {
//            meetings.set(indexFound, m1);
//            
//            List<Invitation> invitations = ic.loadInvitations();
//            List<Invitation> filter_invitations = invitations.stream()
//                    .filter(x -> x.getMeeting_id() == meeting_id)
//                    .collect(Collectors.toList());
//            for(Invitation inv : filter_invitations){
//                inv.setStatus(InvitationStatus.canceled);
//            }
//            if (this.saveMeetings(meetings) && ic.saveInvitations(invitations)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
    }

    public List<ListMeetingViewModel> listMeetingView() {
        //Kamus
        List<Meeting> list;
        List<ListMeetingViewModel> result;
        String meeting_title;

        //Algoritma
        result = new ArrayList();
        list = this.loadMeetings();
        for (Meeting m : list) {
            meeting_title = this.getMeetingTitle(m.getId());
            result.add(new ListMeetingViewModel(m.getId(), meeting_title, m.getStatus()));
        }
        return result;
    }

    public int runScheduler(int meeting_id) {
        Meeting m = this.detailMeeting(meeting_id);
        if (m != null) {
            SchedulerController sc = new SchedulerController();
            Schedule sch = sc.runSchedule(meeting_id);
            if (sch != null) {
                m.setAgreed_time(sch.getDate());
                m.setStatus(MeetingStatus.confirmed);
                if (this.updateMeetingStatus(m)) {
                    return 1;
                }else{
                    return 0;
                }
            }else{
                m.setStatus(MeetingStatus.canceled);
                if (this.updateMeetingStatus(m)) {
                    return 2;
                }else{
                    return 0;
                }
            }
            
        }else{
            return 0;
        }
    }

    public String getMeetingTitle(int meeting_id) {
        return this.loadMeetings().stream()
                .filter(x -> x.getId() == meeting_id)
                .collect(Collectors.toList()).get(0).getTitle();
    }

    public List<Meeting> loadMeetings() {
        try {
            ArrayList<Meeting> meeting_list = mapper.readValue(new File(Config.DATA_MEETING_INDEX), new TypeReference<ArrayList<Meeting>>() {
            });
            return meeting_list;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean saveMeetings(List<Meeting> meeting_list) {
        try {
            this.mapper.writeValue(new File(Config.DATA_MEETING_INDEX), meeting_list);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public LastIndex getLastMeetingIndex() {
        try {
            LastIndex lastIndex = mapper.readValue(new File(Config.DATA_MEETING_LAST_INDEX), new TypeReference<LastIndex>() {
            });
            return lastIndex;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean updateLastMeetingIndex(LastIndex index) {
        try {
            this.mapper.writeValue(new File(Config.DATA_MEETING_LAST_INDEX), index);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public LastIndex getLastInvitationIndex() {
        try {
            LastIndex lastIndex = mapper.readValue(new File(Config.DATA_INVITATION_INDEX), new TypeReference<LastIndex>() {
            });
            return lastIndex;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean updateLastInvitationIndex(LastIndex index) {
        try {
            this.mapper.writeValue(new File(Config.DATA_INVITATION_INDEX), index);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MeetingController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
