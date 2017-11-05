package com.mscheduler.model;

import com.mscheduler.controller.InvitationController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Meeting {

    private int id;
    private String title;
    private String agenda;
    private String location;
    private int duration;
    private DateRange proposed_date_range;
    private Date negotiation_deadline;
    private List<String> participants;
    private List<String> important_participants;
    private MeetingStatus status;
    private DateRange agreed_time;
    private Boolean pastDeadline;

    public Meeting(int id, String title, String agenda, String location, int duration, DateRange proposed_date_range, Date negotiation_deadline, List<String> participants, List<String> important_participants, MeetingStatus status, DateRange agreed_time, boolean pastDeadline) {
        this.id = id;
        this.title = title;
        this.agenda = agenda;
        this.location = location;
        this.duration = duration;
        this.proposed_date_range = proposed_date_range;
        this.negotiation_deadline = negotiation_deadline;
        this.participants = participants;
        this.important_participants = important_participants;
        this.status = status;
        this.agreed_time = agreed_time;
        this.pastDeadline = pastDeadline;
    }

    public Meeting() {

    }

    @Override
    public String toString() {
        InvitationController ic = InvitationController.getInstance();
        List<Invitation> invitation = ic.loadInvitations().stream()
                .filter(x -> x.getMeeting_id() == this.id).collect(Collectors.toList());
        String dateStart = "";
        String dateEnd = "";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        if(this.agreed_time != null) {
            dateStart = this.agreed_time.getDate_start().toString();
            dateEnd = this.agreed_time.getDate_end().toString();
        }
        
        String text = "ID : " + this.id + "\n";
        text += "Title : " + this.title + "\n";
        text += "Agenda : " + this.agenda + "\n";
        text += "Location : " + this.location + "\n";
        text += "Duration : " + this.duration + " hours\n";
        text += "Proposed Date Range : " + df.format(this.getProposed_date_range().getDate_start()) + " - " + df.format(this.getProposed_date_range().getDate_end())+"\n";
        text += "Negotiation Deadline : " + df.format(this.getNegotiation_deadline())+"\n";
        text += "Status : " + this.getStatus()+"\n";
        text += "Agreed Time : " + dateStart + " - " + dateEnd+"\n";
        
        text += "Important Participant List : \n";
        for (int i = 0; i < important_participants.size(); i++) {
            String participant = important_participants.get(i);
            Invitation iv = invitation.stream().filter(x -> x.getTo().equals(participant))
                                          .findFirst().orElse(null);
            if(iv != null) {
                text += iv.getTo()+" - "+iv.getStatus()+"\n";
            }
        }
        
        text += "\nParticipant List : \n";
        for (int i = 0; i < participants.size(); i++) {
            String participant = participants.get(i);
            Invitation iv = invitation.stream().filter(x -> x.getTo().equals(participant))
                                          .findFirst().orElse(null);
            if(iv != null) {
                text += iv.getTo()+" - "+iv.getStatus()+"\n";
            }
        }
        return text;
    }

    public boolean isPastDeadline() {
        if(this.negotiation_deadline.equals(proposed_date_range.getDate_end()) || this.negotiation_deadline.before(proposed_date_range.getDate_end())) {
            return true;
        }
        else {
            return false;        
        }
    }

    public boolean isImportantParticipant(String email) {
        String mail = this.important_participants.stream().filter(x->x.equals(email)).findFirst().orElse(null);
        if(mail != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isNormalParticipant(String email) {
        String mail = this.participants.stream().filter(x->x.equals(email)).findFirst().orElse(null);
        if(mail != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgenda() {
        return agenda;
    }

    public Boolean getPastDeadline() {
        return pastDeadline;
    }

    public void setPastDeadline(Boolean pastDeadline) {
        this.pastDeadline = pastDeadline;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public DateRange getProposed_date_range() {
        return proposed_date_range;
    }

    public void setProposed_date_range(DateRange proposed_date_range) {
        this.proposed_date_range = proposed_date_range;
    }

    public Date getNegotiation_deadline() {
        return negotiation_deadline;
    }

    public void setNegotiation_deadline(Date negotiation_deadline) {
        this.negotiation_deadline = negotiation_deadline;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<String> getImportant_participants() {
        return important_participants;
    }

    public void setImportant_participants(List<String> important_participants) {
        this.important_participants = important_participants;
    }

    public MeetingStatus getStatus() {
        return status;
    }

    public void setStatus(MeetingStatus status) {
        this.status = status;
    }

    public DateRange getAgreed_time() {
        return agreed_time;
    }

    public void setAgreed_time(DateRange agreed_time) {
        this.agreed_time = agreed_time;
    }
}
