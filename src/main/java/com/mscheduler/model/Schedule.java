/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.model;

import com.mscheduler.Config;

public class Schedule {
    private Meeting meeting;
    private DateRange date;
    private int count_im; 
    private int count_p;

    public Schedule(Meeting meeting, DateRange date) {
        this.meeting = meeting;
        this.date = date;
        this.count_im = 0;
        this.count_p = 0;
    }

    public DateRange getDate() {
        return date;
    }

    public void setDate(DateRange date) {
        this.date = date;
    }
    
    public void addImportantParticipant(){
        this.count_im++;
    }
    
     public void addParticipant(){
        this.count_p++;
    }
     
     public int getTotalParticipant(){
         return this.count_im + this.count_p;
     }
     
    public boolean isValid(){
        //im != 0 
        return false;
    }
    
    //isvalid final im = 1, p = 0
    
    
}
