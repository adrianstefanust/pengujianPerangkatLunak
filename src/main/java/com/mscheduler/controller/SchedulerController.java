/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.controller;

import com.mscheduler.model.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SchedulerController {
    private MeetingController mc;
    private InvitationController ic;
    
    public SchedulerController(){
        this.mc = MeetingController.getInstance();
        this.ic = InvitationController.getInstance();
    }
    
    //asumsi senin-jumat 7-18
    /*
        1. generate smua jam yg mungkin
        2. masukin ke list kelas schedule
        3. iterate important participant ->
            if rejected -> break -> done
            else ambil irisan array jam dengan availability
            if irisan 0 -> break -> done
        4. iterate participant
            irisan , count p + 1
        5. if total cm 1 cancel
            else sort by total participate
            get max, if same get date start first
            
        
        note irisan = start <= avail start & end >= avail end
        */
    public Schedule runSchedule(int meeting_id){
        //Kamus
        int durasi;
        List<Schedule> listSchedule;
        Schedule resultSchedule;
        Meeting m;
        DateRange range;
        
        //Algoritma
        m = this.mc.detailMeeting(meeting_id);
        range = m.getProposed_date_range();
        durasi = m.getDuration();
        listSchedule = this.generateRange(range,durasi,m);
        
        listSchedule = this.discardConflictedRange(listSchedule,range);
        
        
        listSchedule = this.intersectWithImportantParticipant(listSchedule,m);
        if (listSchedule == null) {
            return null;
        }
        listSchedule = this.updateAcceptParticipant(listSchedule,m);
        
        //sort
        Comparator<Schedule> byTotalParticipant = Comparator.comparing(
            x -> x.getTotalParticipant()
        );
        Comparator<Schedule> byDate = Comparator.comparing(
            x -> x.getDate().getDate_end()
        );
        resultSchedule = listSchedule.stream()
                .filter(x->x.getTotalParticipant() > 1).sorted(byTotalParticipant.thenComparing(byDate)).findFirst().orElse(null); 
        if (resultSchedule != null) {
            
            return resultSchedule; 
        }else{
            return null;
        }
    }
    
    private List<Schedule> generateRange(DateRange range, int durasi, Meeting meeting){
        //Kamus
        List<Schedule> listSchedule;
        LocalDateTime date;
        
        //Algoritma
        listSchedule = new ArrayList<Schedule>();
        for (date = range.localDateStart(); date.isBefore(range.localDateEnd().minusHours(durasi)); date = date.plusDays(1)) {
           if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                for (date = date.withHour(7); date.getHour() < 18; date = date.plusHours(1)) {
                    listSchedule.add(new Schedule(meeting,new DateRange(date,date.plusHours(durasi))));
                }
            }
        }
        return listSchedule;
    }
    
    private List<Schedule> intersectWithImportantParticipant(List<Schedule> listSchedule,Meeting m){
        List<Invitation> getImportantParticipant = this.ic.listInvitation(m.getId(), m.getImportant_participants());
        for(Invitation inv : getImportantParticipant){
            if (inv.getStatus() == InvitationStatus.rejected || inv.getStatus() == InvitationStatus.waiting) {
                return null;
            }else if(inv.getStatus() == InvitationStatus.accepted){
                listSchedule = listSchedule.stream()
                    .filter(x-> x.getDate().isBetweenAny(inv.getAvailability()))
                    .collect(Collectors.toList());
                if (listSchedule.isEmpty()) {
                    return null;
                }else{
                    for (Schedule sch : listSchedule){
                        sch.addImportantParticipant();
                    }
                }
            }
        }
        return listSchedule;
    }
    
    private List<Schedule> updateAcceptParticipant(List<Schedule> listSchedule,Meeting m){
        List<Schedule> tempList;
        
        List<Invitation> getParticipant = this.ic.listInvitation(m.getId(), m.getParticipants());
        for(Invitation inv : getParticipant){
            if(inv.getStatus() == InvitationStatus.accepted){
                tempList = listSchedule.stream()
                    .filter(x-> x.getDate().isBetweenAny(inv.getAvailability()))
                    .collect(Collectors.toList());
                if (!tempList.isEmpty()) {
                    for (Schedule sch : tempList){
                        sch.addParticipant();
                    }
                }
            }
        }
        return listSchedule;
    }

    private List<Schedule> discardConflictedRange(List<Schedule> listSchedule,DateRange range) {
        
        List<DateRange> conflicted_meeting_time;
        //buang yg bentrok
        conflicted_meeting_time = this.mc.loadMeetings().stream()
                .filter(x->(x.getStatus() == MeetingStatus.confirmed || 
                            x.getStatus() == MeetingStatus.running || 
                            x.getStatus() == MeetingStatus.finished
                        ) && range.isOverlap(x.getAgreed_time()))
                .map(x->x.getAgreed_time())
                .collect(Collectors.toList());
        return listSchedule.stream()
                .filter(x-> !x.getDate().isOverlapAny(conflicted_meeting_time))
                .collect(Collectors.toList());
    }
}
