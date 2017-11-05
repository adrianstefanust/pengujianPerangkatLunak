package com.mscheduler;

import com.mscheduler.controller.InvitationController;
import com.mscheduler.model.DateRange;
import com.mscheduler.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.mscheduler.view.invitation.InvitationMenuViewer;
import com.mscheduler.view.meeting.MeetingMenuViewer;
import com.mscheduler.view.user.UserMenuViewer;
import com.mscheduler.controller.*;
import com.mscheduler.model.Schedule;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //Kamus
        String exitMain;
        Scanner sc;
        boolean exitCalled, flagFirst;

        //Algoritma
        sc = new Scanner(System.in);

//        Main.sandBox(sc);

        //login
        exitCalled = false;
        flagFirst = true;
        while (!exitCalled) {
            if (flagFirst) {
                Main.progres(sc);
                flagFirst = false;
            } else {
                System.out.println("login : Login using another role.\n"
                        + "exit : Exit from application.\n");
                System.out.print(">");
                exitMain = sc.nextLine(); 
                if (exitMain.equalsIgnoreCase("login")) {
                    Main.progres(sc);
                } else if (exitMain.equalsIgnoreCase("exit")) {
                    exitCalled = true;
                } 
            }
        }
        System.exit(0);
    }

    public static void progres(Scanner sc) {
        String role, roleEmail, email;
        String[] res;
        UserMenuViewer umv;
        InvitationMenuViewer imv;
        MeetingMenuViewer mmv;
        User usr;
        UserController uc;
        MeetingController mc;
        InvitationController ic;

        usr = new User();
        umv = UserMenuViewer.getInstance();
        mmv = MeetingMenuViewer.getInstance();
        imv = InvitationMenuViewer.getInstance();
        uc = UserController.getInstance();
        mc = MeetingController.getInstance();
        ic = InvitationController.getInstance();

        //login
        roleEmail = umv.handleInputLogin(sc);
        res = roleEmail.split("~");
        role = res[0];
        email = res[1];
        usr = usr.readUserOne(email);
        uc.setSession(usr);
        mc.setSession(usr);
        ic.setSession(usr);
        
        
        switch (role) {
            case "a":
                umv.init();
                umv.handleInput(sc);
                break;
            case "i":
                mmv.init();
                mmv.handleInput(sc);
                break;
            case "p":
                imv.init();
                imv.handleInput(sc);
                break;
            default:
                break;
        }
    }

    public static void sandBox(Scanner sc) {
//        DateRange range = new DateRange("16/04/2017 07 - 21/04/2017 11");
//        LocalDateTime date;
//        List<DateRange> qwer = new ArrayList<>();
//        for (date = range.localDateStart(); date.isBefore(range.localDateEnd().minusHours(2)); date = date.plusDays(1)) {
//           if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
//                for (date = date.withHour(7); date.getHour() < 18; date = date.plusHours(1)) {
//                    qwer.add(new DateRange(date,date.plusHours(2)));
//                }
//            }
//        }
//        
//        
//        List<DateRange> asdf = new ArrayList<>();
//        asdf.add(new DateRange("16/04/2017 07 - 18/04/2017 11"));
//        
//        List<DateRange> asdf2 = new ArrayList<>();
//        asdf2.add(new DateRange("18/04/2017 07 - 20/04/2017 11"));
////        for(DateRange a : asdf){
//            
//                qwer = qwer.stream()
//                    .filter(x-> x.isBetweenAny(asdf))
//                    .collect(Collectors.toList());
//                qwer = qwer.stream()
//                    .filter(x-> x.isBetweenAny(asdf2))
//                    .collect(Collectors.toList());
//                System.out.println(qwer);
////        System.out.println(new DateRange("18/04/2017 07 - 18/04/2017 09").isBetweenAny(asdf));
//                for(DateRange a : qwer){
//                    System.out.println(a);
//                    System.out.println(a.isBetweenAny(asdf));
//                }
////        }
//        asdf.add(new DateRange("01/01/2000 08 - 04/01/2000 10"));
////        asdf.add(new DateRange("01/01/2000 04 - 01/01/2000 07"));
////        asdf.add(new DateRange("01/01/2000 03 - 01/01/2000 08"));
////        asdf.add(new DateRange("01/01/2000 02 - 01/01/2000 09"));
//        
//        InvitationController ic = InvitationController.getInstance();
//        
//        asdf = ic.unionDateRange(asdf);
//        System.out.println(asdf);
        //sandbox
//        InvitationController as = InvitationController.getInstance();
//        List<Invitation> lst = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            lst.add(new Invitation(i+1,"Nama"));
//        }
//        as.saveInvitations(lst);

//        SchedulerController a = new SchedulerController();
//        a.runSchedule();
    }
}
