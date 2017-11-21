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
public class MeetingStatusTest {
    
    public MeetingStatusTest() {
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
     * Test of values method, of class MeetingStatus.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        MeetingStatus[] expResult = {MeetingStatus.negotiating,MeetingStatus.canceled,MeetingStatus.confirmed,MeetingStatus.running,MeetingStatus.finished};
        MeetingStatus[] result = MeetingStatus.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of valueOf method, of class MeetingStatus.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "canceled";
        MeetingStatus expResult = MeetingStatus.canceled;
        MeetingStatus result = MeetingStatus.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
