/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.model;

public class ListInvitationViewModel {
    public int meeting_id;
    public String meeting_title;
    public InvitationStatus invitation_status;

    public ListInvitationViewModel(int meeting_id, String meeting_title, InvitationStatus invitation_status) {
        this.meeting_id = meeting_id;
        this.meeting_title = meeting_title;
        this.invitation_status = invitation_status;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_title() {
        return meeting_title;
    }

    public void setMeeting_title(String meeting_title) {
        this.meeting_title = meeting_title;
    }

    public InvitationStatus getInvitation_status() {
        return invitation_status;
    }

    public void setInvitation_status(InvitationStatus invitation_status) {
        this.invitation_status = invitation_status;
    }
    
}
