package com.mscheduler.view.meeting;

import com.mscheduler.Config;
import com.mscheduler.Utilities;
import com.mscheduler.controller.MeetingController;
import com.mscheduler.model.DateRange;
import com.mscheduler.model.Meeting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeetingCreateViewer extends AbstractMeetingViewer {

    //singleton + constructor
    private static MeetingCreateViewer instance;

    static {
        instance = new MeetingCreateViewer();
    }

    public static MeetingCreateViewer getInstance() {
        return instance;
    }

    public MeetingCreateViewer() {
        super();
        this.init();
    }
    //end of singleton

    @Override
    public String getText() {
        return "You have selected to create meeting. Please input meeting detail.";
    }

    @Override
    public void handleInput(Scanner sc) {
        String title, agenda, location, participant, importantParticipant, duration,
                proposedDateStr, negotiationDeadlineStr;
        int i, durationInt;
        boolean check, checkAll, exitStat;
        List<String> participantList, importantParticipantList, invalidParticipantList;
        Date proposeDateStart, proposeDateEnd;
        Date negotiationDeadline;

        Meeting m = new Meeting();
        exitStat = false;

        System.out.print("Title : ");
        title = sc.nextLine();
        if (!isExit(title)) {
            while (isEmpty(title)) {
                System.out.println("Please input meeting title");
                System.out.print("Title : ");
                title = sc.nextLine();
            }
            m.setTitle(title);
        } else {
            exitStat = true;
        }

        if (!exitStat) {
            System.out.print("Agenda : ");
            agenda = sc.nextLine();
            if (!isExit(agenda)) {
                while (isEmpty(agenda)) {
                    System.out.println("Please input meeting agenda");
                    System.out.print("Agenda : ");
                    agenda = sc.nextLine();
                }
                m.setAgenda(agenda);
            } else {
                exitStat = true;
            }
        }

        if (!exitStat) {
            System.out.print("Location : ");
            location = sc.nextLine();
            if (!isExit(location)) {
                while (isEmpty(location)) {
                    System.out.println("Please input meeting location.");
                    System.out.print("Location : ");
                    location = sc.nextLine();
                }
                m.setLocation(location);
            } else {
                exitStat = true;
            }
        }

        if (!exitStat) {
            System.out.print("Duration (hours) : ");
            duration = sc.nextLine();
            checkAll = false;
            if (!isExit(duration)) {
                while (!checkAll) {
                    while (isEmpty(duration)) {
                        System.out.println("Please input meeting duration.");
                        System.out.print("Duration (hours) : ");
                        duration = sc.nextLine();
                    }
                    while (!isInteger(duration)) {
                        System.out.println("Not a number. Please try again.");
                        System.out.print("Duration (hours) : ");
                        duration = sc.nextLine();
                    }

                    if (Integer.parseInt(duration) > 0 && Integer.parseInt(duration) <= 10) {
                        checkAll = true;
                        durationInt = Integer.parseInt(duration);
                        m.setDuration(durationInt);
                    } else {
                        if (Integer.parseInt(duration) > 10) {
                            System.out.println("Maximum Hours <= 10. Please Try Again");
                            System.out.print("Duration (hours) : ");
                            duration = sc.nextLine();
                        }
                        else {
                            System.out.println("Invalid input. Please Try Again");
                            System.out.print("Duration (hours) : ");
                            duration = sc.nextLine();
                        }
                    }
                }
            } else {
                exitStat = true;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (!exitStat) {
            System.out.print("Proposed Date Range (dd/mm/yyyy dd/mm/yyyy) : ");
            proposedDateStr = sc.nextLine();
            checkAll = false;
            if (!isExit(proposedDateStr)) {
                while (!checkAll) {
                    while (isEmpty(proposedDateStr)) {
                        System.out.println("Please input proposed date range.");
                        System.out.print("Proposed Date Range (dd/mm/yyyy dd/mm/yyyy) : ");
                        proposedDateStr = sc.nextLine();
                    }
                    String dt[] = proposedDateStr.split(" ");
                    if (dt.length == 2) {
                        if (Utilities.isValidDate(dt[0]) && Utilities.isValidDate(dt[1])) {
                            //&& Utilities.checkDateRange(dt[0], dt[1]) && Utilities.isDateAfterToday(dt[0])
                            try {
                                proposeDateStart = sdf.parse(dt[0]);
                                proposeDateEnd = sdf.parse(dt[1]);
                                DateRange dr = new DateRange();
                                dr.setDate_start(proposeDateStart);
                                dr.setDate_end(proposeDateEnd);
                                m.setProposed_date_range(dr);
                                checkAll = true;
                            } catch (ParseException ex) {
                                Logger.getLogger(MeetingCreateViewer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("Invalid date input");
                            System.out.print("Proposed Date Range (dd/mm/yyyy dd/mm/yyyy) : ");
                            proposedDateStr = sc.nextLine();
                        }
                    } else {
                        System.out.println("Invalid date input");
                        System.out.print("Proposed Date Range (dd/mm/yyyy dd/mm/yyyy) : ");
                        proposedDateStr = sc.nextLine();
                    }
                }
            } else {
                exitStat = true;
            }
        }

        if (!exitStat) {
            System.out.print("Negotiation Deadline (dd/mm/yyyy) : ");
            negotiationDeadlineStr = sc.nextLine();
            checkAll = false;
            if (!isExit(negotiationDeadlineStr)) {
                while (!checkAll) {
                    try {
                        while (isEmpty(negotiationDeadlineStr)) {
                            System.out.println("Please input negotiation deadline date.");
                            System.out.print("Negotiation Deadline (dd/mm/yyyy) : ");
                            negotiationDeadlineStr = sc.nextLine();
                        }
                        if (Utilities.isValidDate(negotiationDeadlineStr) && (sdf.parse(negotiationDeadlineStr).before(m.getProposed_date_range().getDate_start()) || sdf.parse(negotiationDeadlineStr).equals(m.getProposed_date_range().getDate_start()))) {
                            try {
                                negotiationDeadline = sdf.parse(negotiationDeadlineStr);
                                m.setNegotiation_deadline(negotiationDeadline);
                            } catch (ParseException ex) {
                                Logger.getLogger(MeetingCreateViewer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            checkAll = true;
                        } else {
                            if (!Utilities.isValidDate(negotiationDeadlineStr)) {
                                System.out.println("Invalid date input");
                                System.out.print("Negotiation Deadline (dd/mm/yyyy) : ");
                                negotiationDeadlineStr = sc.nextLine();
                            } else {
                                if ((!sdf.parse(negotiationDeadlineStr).before(m.getProposed_date_range().getDate_start()) || sdf.parse(negotiationDeadlineStr).equals(m.getProposed_date_range().getDate_start()))) {
                                    System.out.println("Negotiation Deadline must before proposed date range");
                                    System.out.print("Negotiation Deadline (dd/mm/yyyy) : ");
                                    negotiationDeadlineStr = sc.nextLine();
                                }
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(MeetingCreateViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                exitStat = true;
            }
        }

        if (!exitStat) {
            System.out.print("Important Participant email (email1 email2 email3 ...) : ");
            importantParticipant = sc.nextLine();
            importantParticipantList = new ArrayList();
            checkAll = false;
            m.setImportant_participants(importantParticipantList);
            
            while (!checkAll) {
                importantParticipantList = new ArrayList();
                invalidParticipantList = new ArrayList();
                if (!isExit(importantParticipant)) {
                    while (isEmpty(importantParticipant)) {
                        System.out.println("Please input participant list");
                        System.out.print("Important Participant (email1 email2 email3 ...) : ");
                        importantParticipant = sc.nextLine();
                    }
                    String split[] = importantParticipant.split(" ");
                    if (split.length == 0 && !importantParticipant.equals("")) {
                        split = new String[1];
                        split[0] = importantParticipant;
                    }
                    if (split.length > 0) {
                        i = 0;
                        check = true;
                        while (i < split.length) {
                            if (!Utilities.isValidEmail(split[i]) || !userController.existUser(split[i]) || (userController.getUserByEmail(split[i]) == null)) {
                                invalidParticipantList.add(split[i]);
                                check = false;
                            } else {
                                if (userController.getSession() != userController.getUserByEmail(split[i]) && !importantParticipantList.contains(split[i])) {
                                    importantParticipantList.add(split[i]);
                                } else {
                                    check = false;
                                    invalidParticipantList.add(split[i]);
                                }
                            }
                            i++;
                        }
                        if (check) {
                            m.setImportant_participants(importantParticipantList);
                            checkAll = true;
                        } else {
                            System.out.println("Invalid important participant email : ");
                            for (i = 0; i < invalidParticipantList.size(); i++) {
                                System.out.println(invalidParticipantList.get(i));
                            }
                            System.out.println("Please try again");
                            System.out.print("Important Participant email (email1 email2 email3 ...) : ");
                            importantParticipant = sc.nextLine();
                        }
                    }
                } else {
                    checkAll = true;
                    exitStat = true;
                }
            }
        }

        if (!exitStat) {
            System.out.print("Other Participant email (email1 email2 email3 ...) : ");
            participant = sc.nextLine();
            checkAll = false;
            participantList = new ArrayList();
            m.setParticipants(participantList);
            while (!checkAll) {
                participantList = new ArrayList();
                invalidParticipantList = new ArrayList();
                if (!isExit(participant)) {
                    while (isEmpty(participant)) {
                        System.out.println("Please input other participant list");
                        System.out.print("Other Participant email (email1 email2 email3 ...) : ");
                        participant = sc.nextLine();
                    }
                    String split[] = participant.split(" ");
                    if (split.length == 0 && !participant.equals("")) {
                        split = new String[1];
                        split[0] = participant;
                    }
                    if (split.length > 0) {
                        i = 0;
                        check = true;
                        while (i < split.length) {
                            if (!Utilities.isValidEmail(split[i]) || !userController.existUser(split[i]) || (userController.getUserByEmail(split[i]) == null)) {
                                check = false;
                                invalidParticipantList.add(split[i]);
                            } else {
                                if (userController.getSession() != userController.getUserByEmail(split[i]) && !m.getParticipants().contains(split[i]) && !m.getImportant_participants().contains(split[i])) {
                                    participantList.add(split[i]);
                                } else {
                                    check = false;
                                    invalidParticipantList.add(split[i]);
                                    System.out.println("Can't input initiator mail");
                                }
                            }
                            i++;
                        }
                        if (check) {
                            m.setParticipants(participantList);
                            checkAll = true;
                        } else {
                            System.out.println("Invalid or already exists participant email : ");
                            for (i = 0; i < invalidParticipantList.size(); i++) {
                                System.out.println(invalidParticipantList.get(i));
                            }
                            System.out.println("Please try again");
                            System.out.print("Other Participant Email (email1 email2 email3 ...)  : ");
                            participant = sc.nextLine();
                        }
                    }
                } else {
                    checkAll = true;
                    exitStat = true;
                }
            }
        }

        if (exitStat) {
            System.out.println("Input meeting canceled");
        } else {
            if (meetingController.createMeeting(m)) {
                System.out.println("Meeting Saved");
            }
        }
    }

    public boolean isEmpty(String text) {
        return (text.equals(""));
    }

    public boolean isExit(String text) {
        return (text.toLowerCase().equals("exit"));
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }
        return isValidInteger;
    }
}
