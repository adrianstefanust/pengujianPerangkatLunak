/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steven
 */
public class ListInvitationViewModelTest {

    ListInvitationViewModel instance;
    int meeting_id;
    String meeting_title;
    InvitationStatus invitation_status;

    public ListInvitationViewModelTest() {
        meeting_id = 2;
        meeting_title = "Meeting 2";
        invitation_status = InvitationStatus.confirmed;
        instance = new ListInvitationViewModel(meeting_id, meeting_title, invitation_status);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMeeting_id method, of class ListInvitationViewModel.
     */
    @Test
    public void testGetMeeting_id() {
        System.out.println("getMeeting_id");
        int expResult = 2;
        int result = instance.getMeeting_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setMeeting_id method, of class ListInvitationViewModel.
     */
    @Test
    public void testSetMeeting_id() {
        System.out.println("setMeeting_id");
        int new_meeting_id = 100;
        instance.setMeeting_id(new_meeting_id);
        assertEquals(new_meeting_id, instance.getMeeting_id());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getMeeting_title method, of class ListInvitationViewModel.
     */
    @Test
    public void testGetMeeting_title() {
        System.out.println("getMeeting_title");
        String expResult = "Meeting 2";
        String result = instance.getMeeting_title();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setMeeting_title method, of class ListInvitationViewModel.
     */
    @Test
    public void testSetMeeting_title() {
        System.out.println("setMeeting_title");
        String new_meeting_title = "Changed Meeting 2";
        instance.setMeeting_title(new_meeting_title);
        assertEquals(new_meeting_title, instance.getMeeting_title());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getInvitation_status method, of class ListInvitationViewModel.
     */
    @Test
    public void testGetInvitation_status() {
        System.out.println("getInvitation_status");
        InvitationStatus expResult = InvitationStatus.confirmed;
        InvitationStatus result = instance.getInvitation_status();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setInvitation_status method, of class ListInvitationViewModel.
     */
    @Test
    public void testSetInvitation_status() {
        System.out.println("setInvitation_status");
        InvitationStatus new_invitation_status = InvitationStatus.accepted;
        instance.setInvitation_status(new_invitation_status);
        assertEquals(new_invitation_status, instance.getInvitation_status());
        // TODO review the generated test code and remove the default call to fail.
    }

}
