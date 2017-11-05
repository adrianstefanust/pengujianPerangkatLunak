package com.mscheduler.model;

import com.mscheduler.Config;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DateRange {
    
    private Date date_start;
    private Date date_end;
    
    public DateRange()
    {
        this(new Date(), new Date());
    }

    public DateRange(Date date_start, Date date_end) {
        this.date_start = date_start;
        this.date_end = date_end;
    }
//
    public DateRange(String date) {
        //Kamus
        String[] dateList;
        
        //Algoritma
        dateList = date.split(" - ");
        DateFormat df = new SimpleDateFormat(Config.DATETIME_FORMAT_INPUT_STRING); 
        try {
            this.date_start = df.parse(dateList[0]);
            this.date_end = df.parse(dateList[1]);
        } catch (ParseException ex) {
            Logger.getLogger(DateRange.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DateRange(LocalDateTime date_start, LocalDateTime date_end) {
        this.date_start = Date.from(date_start.atZone(ZoneId.systemDefault()).toInstant());
        this.date_end = Date.from(date_end.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    
    public Date localDateTimeToDate(LocalDateTime ldate){
        return Date.from(ldate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }
    

    public LocalDateTime localDateStart() {
        return this.dateToLocalDateTime(this.date_start);
    }

    public void setLocalDate_start(LocalDateTime date_start) {
        this.date_start = Date.from(date_start.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDateTime localDateEnd() {
        return this.dateToLocalDateTime(this.date_end);
    }

    public void setLocalDate_end(LocalDateTime date_end) {
        this.date_end = Date.from(date_end.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public int length() {
        return 0;
    }
    
    public boolean isBetweenAny(List<DateRange> date) {
        return date.stream().anyMatch(x->this.isDateRangeBetween(x));
    }
    
    public boolean isDateRangeBetween(DateRange date) {
        return !this.localDateStart().isBefore(date.localDateStart()) && !this.localDateEnd().isAfter(date.localDateEnd());
    }
    
    public boolean isDateBetween(LocalDateTime date) {
        return !this.localDateStart().isBefore(date) && !this.localDateEnd().isAfter(date);
    }
    
    @Override
    public String toString() {
        return this.localDateStart().format(Config.DATETIME_OUTPUT_FORMATTER) 
                + " - "+ this.localDateEnd().format(Config.DATETIME_OUTPUT_FORMATTER);
    }
    
    public String toString2() {
        return this.localDateStart().format(Config.DATE_OUTPUT_FORMATTER) 
                + " - "+ this.localDateEnd().format(Config.DATE_OUTPUT_FORMATTER);
    }

    public boolean isOverlap(DateRange dr2) {
        return dr2.isDateRangeBetween(this) || this.isDateBetween(dr2.localDateStart()) || this.isDateBetween(dr2.localDateEnd()); 
    }

    public void mergeDate(DateRange dr) {
        if (dr.localDateStart().isBefore(this.localDateStart())) {
            this.setLocalDate_start(dr.localDateStart());
        }
        if (dr.localDateEnd().isAfter(this.localDateEnd())) {
            this.setLocalDate_end(dr.localDateEnd());
        }
    }

    public boolean isOverlapAny(List<DateRange> conflicted_meeting_time) {
        boolean check;
        
        check = false;
        
        for(DateRange dr : conflicted_meeting_time){
            check = check || this.isOverlap(dr);
        }
        return check;
    }

}
