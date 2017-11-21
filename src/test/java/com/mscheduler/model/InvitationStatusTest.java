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
public class InvitationStatusTest {
    
    public InvitationStatusTest() {
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
     * Test of values method, of class InvitationStatus.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        InvitationStatus[] expResult = {InvitationStatus.waiting,InvitationStatus.accepted,InvitationStatus.rejected,InvitationStatus.canceled,InvitationStatus.confirmed,InvitationStatus.finished};
        InvitationStatus[] result = InvitationStatus.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of valueOf method, of class InvitationStatus.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "accepted";
        InvitationStatus expResult = InvitationStatus.accepted;
        InvitationStatus result = InvitationStatus.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
