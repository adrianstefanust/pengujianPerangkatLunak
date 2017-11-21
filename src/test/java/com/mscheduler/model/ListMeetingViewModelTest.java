/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steven
 *
 */
public class ListMeetingViewModelTest {

    ListMeetingViewModel instance;
    int meeting_id;
    String meeting_title;
    MeetingStatus meeting_status;

    public ListMeetingViewModelTest() {
        meeting_id = 1;
        meeting_title = "meeting 10";
        meeting_status = MeetingStatus.negotiating;
        instance = new ListMeetingViewModel(meeting_id, meeting_title, meeting_status);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getMeeting_id method, of class ListMeetingViewModel.
     */
    @Test
    public void testGetMeeting_id() {
        System.out.println("getMeeting_id");
        int expResult = 1;
        int result = instance.getMeeting_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of setMeeting_id method, of class ListMeetingViewModel.
     */
    @Test
    public void testSetMeeting_id() {
        System.out.println("setMeeting_id");
        int new_meeting_id = 0;
        instance.setMeeting_id(new_meeting_id);
        assertEquals(new_meeting_id, instance.getMeeting_id());
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getMeeting_title method, of class ListMeetingViewModel.
     */
    @Test
    public void testGetMeeting_title() {
        System.out.println("getMeeting_title");
        String expResult = meeting_title;
        String result = instance.getMeeting_title();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of setMeeting_title method, of class ListMeetingViewModel.
     */
    @Test
    public void testSetMeeting_title() {
        System.out.println("setMeeting_title");
        instance.setMeeting_title(meeting_title);
        assertEquals(meeting_title, instance.getMeeting_title());
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of getMeeting_status method, of class ListMeetingViewModel.
     */
    @Test
    public void testGetMeeting_status() {
        System.out.println("getMeeting_status");
        MeetingStatus expResult = MeetingStatus.negotiating;
        MeetingStatus result = instance.getMeeting_status();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of setMeetingStatus method, of class ListMeetingViewModel.
     */
    @Test
    public void testSetMeetingStatus() {
        System.out.println("setMeetingStatus");
        MeetingStatus new_meeting_status = MeetingStatus.finished;
        instance.setMeetingStatus(new_meeting_status);
        assertEquals(new_meeting_status, instance.getMeeting_status());
        // TODO review the generated test code and remove the default call to fail.

    }

}
