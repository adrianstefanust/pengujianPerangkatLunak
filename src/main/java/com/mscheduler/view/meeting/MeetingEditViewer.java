package com.mscheduler.view.meeting;

import com.mscheduler.Utilities;
import com.mscheduler.model.DateRange;
import com.mscheduler.model.Meeting;
import com.mscheduler.model.MeetingStatus;
import static com.mscheduler.view.meeting.MeetingCreateViewer.isInteger;
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

public class MeetingEditViewer extends AbstractMeetingViewer {

    //singleton + constructor
    private static MeetingEditViewer instance;

    static {
        instance = new MeetingEditViewer();
    }

    public static MeetingEditViewer getInstance() {
        return instance;
    }

    public MeetingEditViewer() {
        super();
        this.init();
    }
    //end of singleton

    @Override
    public String getText(int id) {
        System.out.println("Edit this meeting");
        Meeting meeting = this.meetingController.detailMeeting(id);
        return meeting.toString() + "\n";
    }

    @Override
    public void handleInput(Scanner sc, int meeting_id) {
        System.out.println(this.meetingController.detailMeeting(meeting_id).getStatus());
        if (this.meetingController.detailMeeting(meeting_id).getStatus() == MeetingStatus.negotiating) {
            System.out.println(this.getText(meeting_id));
            System.out.println("Edit Mode, Type 'help' to show list of command");
            System.out.print("> ");
            String command = sc.nextLine();
            Meeting m = this.meetingController.detailMeeting(meeting_id);
            String text[];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

            if (m != null) {
                while (!command.toLowerCase().equals("end")) {
                    System.out.println("Edit Mode, Type 'help' to show list of command");
                    if (command.toLowerCase().equals("all")) {
                        Meeting mNew = editAllMeeting(sc, meeting_id);
                        if (mNew != null) {
                            m = mNew;
                        }
                    } else {
                        if (command.split(" ")[0].toLowerCase().equals("title")) {
                            text = command.split(" ");
                            if (text.length > 1) {
                                String allText = "";
                                for (int i = 1; i < text.length; i++) {
                                    if (i == 1) {
                                        allText += text[i];
                                    } else {
                                        allText += " " + text[i];
                                    }
                                }
                                System.out.println("Old Title : " + m.getTitle());
                                m.setTitle(allText);
                                System.out.println("New Title : " + m.getTitle());
                            } else {
                                System.out.println("Title can't empty.");
                            }
                        } else {
                            if (command.split(" ")[0].toLowerCase().equals("agenda")) {
                                text = command.split(" ");
                                if (text.length > 1) {
                                    String allText = "";
                                    for (int i = 1; i < text.length; i++) {
                                        if (i == 1) {
                                            allText += text[i];
                                        } else {
                                            allText += " " + text[i];
                                        }
                                    }
                                    System.out.println("Old Agenda : " + m.getAgenda());
                                    m.setAgenda(allText);
                                    System.out.println("New Agenda : " + m.getAgenda());
                                } else {
                                    System.out.println("Agenda can't empty.");
                                }
                            } else {
                                if (command.split(" ")[0].toLowerCase().equals("location")) {
                                    text = command.split(" ");
                                    if (text.length > 1) {
                                        String allText = "";
                                        for (int i = 1; i < text.length; i++) {
                                            if (i == 1) {
                                                allText += text[i];
                                            } else {
                                                allText += " " + text[i];
                                            }
                                        }
                                        System.out.println("Old Location : " + m.getLocation());
                                        m.setLocation(allText);
                                        System.out.println("New Location : " + m.getLocation());
                                    } else {
                                        System.out.println("Location can't empty.");
                                    }
                                } else {
                                    if (command.split(" ")[0].toLowerCase().equals("duration")) {
                                        text = command.split(" ");
                                        if (text.length > 1) {
                                            if (Integer.parseInt(text[1]) > 0 && Integer.parseInt(text[1]) <= 10) {
                                                System.out.println("Old Duration : " + m.getDuration() + " hour(s)");
                                                m.setDuration(Integer.parseInt(text[1]));
                                                System.out.println("New Duration : " + m.getDuration() + " hour(s)");
                                            } else {
                                                System.out.println("Duration must > 0 and <= 10");
                                            }
                                        } else {
                                            System.out.println("Duration can't empty.");
                                        }
                                    } else {
                                        if (command.split(" ")[0].toLowerCase().equals("deadline")) {
                                            text = command.split(" ");
                                            if (text.length > 1) {
                                                if (Utilities.isValidDate(text[1]) && Utilities.checkDateRange(text[1], m.getProposed_date_range().getDate_start().toString())) {
                                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                                                    try {
                                                        System.out.println("Old Negotiation Deadline : " + m.getNegotiation_deadline().toString());
                                                        m.setNegotiation_deadline(sdf.parse(text[1]));
                                                        System.out.println("New Negotiation Deadline : " + m.getNegotiation_deadline().toString());
                                                    } catch (ParseException ex) {
                                                        Logger.getLogger(MeetingEditViewer.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                } else {
                                                    System.out.println("Invalid Date Format");
                                                }
                                            } else {
                                                System.out.println("Input format : dd/mm/yyyy and must before proposed date");
                                            }
                                        } else {
                                            if (command.split(" ")[0].equalsIgnoreCase("participant")) {
                                                text = command.split(" ");
                                                if (text.length > 1) {
                                                    List<String> participants = new ArrayList();
                                                    int i = 1;
                                                    boolean c = true;
                                                    String tempMail = "";
                                                    while (i < text.length && c) {
                                                        if (!Utilities.isValidEmail(text[i]) || !userController.existUser(text[i]) || (userController.getUserByEmail(text[i]) == null)) {
                                                            c = false;
                                                            tempMail = text[i];
                                                        } else {
                                                            if (userController.getSession() != userController.getUserByEmail(text[i]) && !participants.contains(text[i]) && !m.getParticipants().contains(text[i])) {
                                                                participants.add(text[i]);
                                                            } else {
                                                                c = false;
                                                                tempMail = text[i];
                                                            }
                                                        }
                                                        i++;
                                                    }
                                                    if (!c) {
                                                        System.out.println("User : " + tempMail + " invalid.");
                                                    } else {
                                                        List<String> otherParticipant = m.getParticipants();
                                                        System.out.println("Old Participant(s) : ");
                                                        for (int j = 0; j < otherParticipant.size(); j++) {
                                                            System.out.println(otherParticipant.get(j));
                                                        }
                                                        System.out.println("New Participant(s) : ");
                                                        m.setParticipants(participants);
                                                        otherParticipant = m.getParticipants();

                                                        for (int j = 0; j < otherParticipant.size(); j++) {
                                                            System.out.println(otherParticipant.get(j));
                                                        }
                                                        System.out.println("");
                                                    }
                                                } else {
                                                    System.out.println("Input format : email1 email2 email3 .. emailn");
                                                }
                                            } else {
                                                if (command.split(" ")[0].equalsIgnoreCase("important-participant")) {
                                                    text = command.split(" ");
                                                    if (text.length > 1) {
                                                        List<String> participants = new ArrayList();
                                                        int i = 1;
                                                        boolean c = true;
                                                        String tempMail = "";
                                                        while (i < text.length && c) {
                                                            if (!Utilities.isValidEmail(text[i]) || !userController.existUser(text[i]) || (userController.getUserByEmail(text[i]) == null)) {
                                                                c = false;
                                                                tempMail = text[i];
                                                            } else {
                                                                if (userController.getSession() != userController.getUserByEmail(text[i]) && !participants.contains(text[i]) && !m.getParticipants().contains(text[i])) {
                                                                    participants.add(text[i]);
                                                                } else {
                                                                    c = false;
                                                                    tempMail = text[i];
                                                                }
                                                            }
                                                            i++;
                                                        }
                                                        if (!c) {
                                                            System.out.println("User : " + tempMail + " invalid or already input.");
                                                        } else {
                                                            List<String> importantParticipants = m.getImportant_participants();
                                                            System.out.println("Old Important Participant(s) : ");
                                                            for (int j = 0; j < importantParticipants.size(); j++) {
                                                                System.out.println(importantParticipants.get(j));
                                                            }
                                                            System.out.println("New Important Participant(s) : ");
                                                            m.setImportant_participants(participants);
                                                            importantParticipants = m.getImportant_participants();

                                                            for (int j = 0; j < importantParticipants.size(); j++) {
                                                                System.out.println(importantParticipants.get(j));
                                                            }
                                                            System.out.println("");
                                                        }
                                                    } else {
                                                        System.out.println("Input format : email1 email2 email3 .. emailn");
                                                    }
                                                } else {
                                                    if (command.split(" ")[0].equalsIgnoreCase("help")) {
                                                        System.out.println(this.getHelp());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.print("> ");
                    command = sc.nextLine();
                }
                if (meetingController.editMeeting(m)) {
                    System.out.println("Meeting updated");
                }
            } else {
                System.out.println("Meeting not found");
            }
        }
        else {
            System.out.println("Edit just for meeting with negotiating status");
        }

    }

    public Meeting editAllMeeting(Scanner sc, int meetingId) {
        String title, agenda, location, participant, importantParticipant, duration,
                proposedDateStr, negotiationDeadlineStr;
        int i, durationInt;
        boolean check, checkAll, exitStat;
        List<String> participantList, importantParticipantList, invalidParticipantList;
        Date proposeDateStart, proposeDateEnd;
        Date negotiationDeadline;

        Meeting m = meetingController.detailMeeting(meetingId);
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
                        } else {
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
                            System.out.println("Invalid important participant email / or already input : ");
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
            return null;
        } else {
            return m;
        }
    }

    public boolean isEmpty(String text) {
        return (text.equals(""));
    }

    public boolean isExit(String text) {
        return (text.toLowerCase().equals("end"));
    }

    @Override
    public String getHelp() {
        return "all : Edit whole meeting\n"
                + "title <meeting-title> : Edit meeting title\n"
                + "agenda <meeting-agenda> : Edit meeting agenda\n"
                + "location <meeting-location> : Edit meeting location\n"
                + "deadline <deadline-date(dd/mm/yyyy)> : Edit Negotiation Deadline\n"
                + "duration <meeting-duration> : Edit meeting duration\n"
                + "participant <email1> <email2> <emailn> : Edit meeting participant\n"
                + "important-participant <email1> <email2> ... <emailn> : Edit important meeting participant\n"
                + "end : exit and save meeting data";
    }
}
