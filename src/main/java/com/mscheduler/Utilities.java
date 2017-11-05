/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import com.mscheduler.view.meeting.MeetingCreateViewer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilities {

    public static String getPage(String str, int page) {
        //Kamus
        String[] splitString;
        String result;

        //Algoritma
        splitString = str.split("\n");
        result = "";
        for (int i = Config.ROW_PER_PAGE * (page - 1); i < Config.ROW_PER_PAGE * page; i++) {
            if (i < splitString.length) {
                if (i == Config.ROW_PER_PAGE * (page - 1)) {
                    result += splitString[i];
                } else {
                    result += "\n" + splitString[i];
                }
            }
        }
        return result;
    }

    public static int countMaxPage(String str) {
        //Kamus
        String[] splitString;

        //Algoritma
        splitString = str.split("\n");

        return (splitString.length - 1) / Config.ROW_PER_PAGE + 1;
    }

    public static boolean validDateRange(String date) {
        //Kamus
        boolean validDateString;
        String[] dateList;

        //Algoritma
        validDateString = true;
        dateList = date.split(" - ");
        if (dateList.length == 2) {
            for (String dateString : dateList) {
                validDateString = validDateString && Utilities.isValidDateTimeString(dateString);
            }
            
            return validDateString && Utilities.isFirstDateBeforeSecond(dateList[0],dateList[1]);
        } else {
            return false;
        }
    }
    
    public static boolean isValidDateTimeString(String date) {
        return Utilities.isValidDateTimeString(date, Config.DATETIME_INPUT_FORMATTER);
    }
    
    public static boolean isValidDateTimeString(String date, DateTimeFormatter format) {
        try {
            LocalDateTime.parse(date, Config.DATETIME_INPUT_FORMATTER);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }
	
    public static boolean isValidDate(String date) {
//        System.out.println(Utilities.isValidDateTimeString(date, Config.DATE_INPUT_FORMATTER));
//        return Utilities.isValidDateTimeString(date, Config.DATE_INPUT_FORMATTER);
        String ePattern = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(date);
        return m.matches();
    }
     
    public static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean checkDateRange(String dt1, String dt2) {
        boolean check = false;
        try {
            if (dt1 != dt2) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                check = sdf.parse(dt1).before(sdf.parse(dt2));
            } else {
                check = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(MeetingCreateViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;

//        LocalDate parse1,parse2;
//        parse1 = LocalDate.parse(date1, Config.DATE_OUTPUT_FORMATTER);
//        parse2 = LocalDate.parse(date2, Config.DATE_OUTPUT_FORMATTER);
//        return !parse1.isAfter(parse2);
    }
    
    public static boolean isDateAfterToday(String dt1) {
        String dt2 = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        boolean check = false;
        
        try {
            if (dt1 != dt2) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                check = sdf.parse(dt1).before(sdf.parse(dt2));
            } else {
                check = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(MeetingCreateViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
        
//        LocalDate parse1,parse2;
//        parse1 = LocalDate.parse(date1, Config.DATE_OUTPUT_FORMATTER);
//        parse2 = LocalDate.parse(date2, Config.DATE_OUTPUT_FORMATTER);
////        System.out.println(parse1 + " "+parse);
//        return !parse1.isAfter(parse2);
    }

    private static boolean isFirstDateBeforeSecond(String date1, String date2) {
        LocalDateTime parse1,parse2;
        parse1 = LocalDateTime.parse(date1, Config.DATETIME_INPUT_FORMATTER);
        parse2 = LocalDateTime.parse(date2, Config.DATETIME_INPUT_FORMATTER);
        return !parse1.isAfter(parse2);
    }
}
