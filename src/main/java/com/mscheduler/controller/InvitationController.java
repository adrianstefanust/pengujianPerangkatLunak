package com.mscheduler.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mscheduler.Config;
import com.mscheduler.model.DateRange;
import com.mscheduler.model.Invitation;
import com.mscheduler.model.InvitationStatus;
import com.mscheduler.model.ListInvitationViewModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class InvitationController extends AbstractController {
    private MeetingController mc;
    
    //singleton + constructor
        private static InvitationController instance;

        static{
            instance = new InvitationController();
            instance.mc = MeetingController.getInstance();
        }

        public static InvitationController getInstance(){
            return instance;
        }
        
         private InvitationController() {
            super();
        }
    //end of singleton
    
    public List<ListInvitationViewModel> listInvitationView() {
        //Kamus
        String email;
        List<Invitation> list;
        List<ListInvitationViewModel> result;
        String meeting_title;
        
        //Algoritma
        email = (this.getSession() != null)? this.getSession().getEmail() : ""; 
        result = new ArrayList<>();
        list = this.loadInvitations().stream()
                .filter(x -> x.getTo().equals(email))
                .collect(Collectors.toList());
        for (Invitation invit : list) {
            meeting_title = this.getMeetingTitle(invit.getMeeting_id());
            result.add(new ListInvitationViewModel(invit.getMeeting_id(),meeting_title,invit.getStatus()));
        }
        return result;
    }
    
    public List<Invitation> listInvitation(int meeting_id, List<String> emailList){
        return this.loadInvitations().stream()
                .filter(x -> x.getMeeting_id() == meeting_id && 
                        emailList.stream().anyMatch(y->y.equals(x.getTo()))
                    )
                .collect(Collectors.toList());
    }

    public String detailInvitation(int meeting_id) {
        return mc.detailMeeting(meeting_id).toString();
    }
    
    public List<DateRange> unionDateRange(List<DateRange> dateList){
        boolean isOverlap = false;
        for(DateRange dr : dateList){
            isOverlap = false;
            for(DateRange dr2 : dateList){
                if (!dr.equals(dr2) && dr.isOverlap(dr2)) {
                    isOverlap = true;
                    dr2.mergeDate(dr);
                    break;
                }
            }
            if (isOverlap) {
                dateList.remove(dr);
                break;
            }
        }
        if (isOverlap) {
            return unionDateRange(dateList);
        }else{
            return dateList;
        }
    }
    
    public boolean acceptInvitation(int meeting_id, List<DateRange> availability) {
        return this.updateInvitation(meeting_id, InvitationStatus.accepted,availability,true);
    }

    public boolean rejectInvitation(int meeting_id) {
        return this.updateStatus(meeting_id, InvitationStatus.rejected,true);
    }
    
    private boolean updateStatus(int meeting_id, InvitationStatus status, boolean isRespond){
        return this.updateInvitation(meeting_id, status,null, isRespond);
    }
    
    private boolean updateInvitation(int meeting_id, InvitationStatus status, List<DateRange> availability, boolean isRespond){
        //Kamus
        String email;
        List<Invitation> invitation_list;
        Invitation invitation_detail;
                
        //Algoritma
        email = (this.getSession() != null)? this.getSession().getEmail() : ""; 
        invitation_list = this.loadInvitations();
        invitation_detail = invitation_list.stream()
                .filter(x->x.getMeeting_id() == meeting_id && x.getTo().equals(email)).findFirst().orElse(null);
        if (invitation_detail != null) {
            if ((isRespond && (invitation_detail.getStatus() == InvitationStatus.waiting || invitation_detail.getStatus() == InvitationStatus.accepted || invitation_detail.getStatus() == InvitationStatus.rejected)) || !isRespond) {
                if(status !=null){
                    invitation_detail.setStatus(status);
                }
                if (availability != null) {
                    availability = this.unionDateRange(availability);
                    invitation_detail.setAvailability(availability);
                }
                return this.saveInvitations(invitation_list);
            }
        }
        return false;
    }
    
    public String getMeetingTitle(int meeting_id){
        return mc.getMeetingTitle(meeting_id);
    }
    
    public boolean isMeetingIdValid(int meeting_id,boolean respon){
        //Kamus
        String email;
        Invitation inv;
        
        //Algoritma
        email = (this.getSession() != null)? this.getSession().getEmail() : ""; 
        inv = this.loadInvitations().stream()
                .filter(x -> x.getTo().equals(email) && x.getMeeting_id() == meeting_id)
                .findFirst().orElse(null);
        if (!respon) {
            return inv != null;
        }else{
            return inv != null && (inv.getStatus() == InvitationStatus.waiting || inv.getStatus() == InvitationStatus.accepted || inv.getStatus() == InvitationStatus.rejected);
        }
    }
    
    public List<Invitation> loadInvitations() {
        try {
            ArrayList<Invitation> invitations_list = mapper.readValue(new File(Config.DATA_INVITATION_INDEX), new TypeReference<ArrayList<Invitation>>() {
            });
            return invitations_list;
        } catch (IOException ex) {
            Logger.getLogger(InvitationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean saveInvitations(List<Invitation> invitations_list) {
        try {
            this.mapper.writeValue(new File(Config.DATA_INVITATION_INDEX), invitations_list);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(InvitationController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}