package com.mscheduler.model;

import java.util.ArrayList;
import java.util.List;

public class Invitation {

    private int meeting_id;
    private String to;
    private List<DateRange> availability;
    private InvitationStatus status;
    
    public Invitation() {
        
    }

    public Invitation(int meeting_id, String to) {
        this.meeting_id = meeting_id;
        this.to = to;
        this.availability = new ArrayList<DateRange>();
        this.status = InvitationStatus.waiting;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<DateRange> getAvailability() {
        return this.availability;
    }
    
    public void setAvailability(List<DateRange> availability) {
       this.availability = availability;
    }
    
    public void addAvailability(DateRange availability) {
        this.availability.add(availability);
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
