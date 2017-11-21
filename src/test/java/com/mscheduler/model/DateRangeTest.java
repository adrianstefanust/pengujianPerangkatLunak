/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
public class DateRangeTest {
    
    public DateRangeTest() {
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
     * Test of dateToLocalDateTime method, of class DateRange.
     */
    @Test
    public void testDateToLocalDateTime() {
        System.out.println("dateToLocalDateTime");
        Date date = new Date("10/10/2010");
        DateRange instance = new DateRange();
        LocalDateTime expResult = new LocalDateTimeDate(Date.parse("10/10/2010"));
        LocalDateTime result = instance.dateToLocalDateTime(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of localDateTimeToDate method, of class DateRange.
     */
    @Test
    public void testLocalDateTimeToDate() {
        System.out.println("localDateTimeToDate");
        LocalDateTime ldate = null;
        DateRange instance = new DateRange();
        Date expResult = null;
        Date result = instance.localDateTimeToDate(ldate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getDate_start method, of class DateRange.
     */
    @Test
    public void testGetDate_start() {
        System.out.println("getDate_start");
        DateRange instance = new DateRange();
        Date expResult = instance.getDate_start();
        Date result = instance.getDate_start();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setDate_start method, of class DateRange.
     */
    @Test
    public void testSetDate_start() {
        System.out.println("setDate_start");
        Date date_start = new Date("10/10/2010");
        DateRange instance = new DateRange();
        instance.setDate_start(date_start);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getDate_end method, of class DateRange.
     */
    @Test
    public void testGetDate_end() {
        System.out.println("getDate_end");
        DateRange instance = new DateRange();
        Date expResult = instance.getDate_end();
        Date result = instance.getDate_end();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setDate_end method, of class DateRange.
     */
    @Test
    public void testSetDate_end() {
        System.out.println("setDate_end");
        Date date_end = null;
        DateRange instance = new DateRange();
        instance.setDate_end(date_end);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of localDateStart method, of class DateRange.
     */
    @Test
    public void testLocalDateStart() {
        System.out.println("localDateStart");
        DateRange instance = new DateRange();
        LocalDateTime expResult = null;
        LocalDateTime result = instance.localDateStart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setLocalDate_start method, of class DateRange.
     */
    @Test
    public void testSetLocalDate_start() {
        System.out.println("setLocalDate_start");
        LocalDateTime date_start = null;
        DateRange instance = new DateRange();
        instance.setLocalDate_start(date_start);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of localDateEnd method, of class DateRange.
     */
    @Test
    public void testLocalDateEnd() {
        System.out.println("localDateEnd");
        DateRange instance = new DateRange();
        LocalDateTime expResult = null;
        LocalDateTime result = instance.localDateEnd();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setLocalDate_end method, of class DateRange.
     */
    @Test
    public void testSetLocalDate_end() {
        System.out.println("setLocalDate_end");
        LocalDateTime date_end = null;
        DateRange instance = new DateRange();
        instance.setLocalDate_end(date_end);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of length method, of class DateRange.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        DateRange instance = new DateRange();
        int expResult = 0;
        int result = instance.length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isBetweenAny method, of class DateRange.
     */
    @Test
    public void testIsBetweenAny() {
        System.out.println("isBetweenAny");
        List<DateRange> date = null;
        DateRange instance = new DateRange();
        boolean expResult = instance.isBetweenAny(date);
        boolean result = instance.isBetweenAny(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isDateRangeBetween method, of class DateRange.
     */
    @Test
    public void testIsDateRangeBetween() {
        System.out.println("isDateRangeBetween");
        DateRange date = new DateRange();
        DateRange instance = new DateRange();
        boolean expResult = true;
        boolean result = instance.isDateRangeBetween(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isDateBetween method, of class DateRange.
     */
    @Test
    public void testIsDateBetween() {
        System.out.println("isDateBetween");
        LocalDateTime date = null;
        DateRange instance = new DateRange();
        boolean expResult = false;
        boolean result = instance.isDateBetween(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of toString method, of class DateRange.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DateRange instance = new DateRange();
        String expResult = instance.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of toString2 method, of class DateRange.
     */
    @Test
    public void testToString2() {
        System.out.println("toString2");
        DateRange instance = new DateRange();
        String expResult = instance.toString2();
        String result = instance.toString2();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isOverlap method, of class DateRange.
     */
    @Test
    public void testIsOverlap() {
        System.out.println("isOverlap");
        DateRange dr2 = new DateRange();
        DateRange instance = new DateRange();
        boolean expResult = true;
        boolean result = instance.isOverlap(dr2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of mergeDate method, of class DateRange.
     */
    @Test
    public void testMergeDate() {
        System.out.println("mergeDate");
        DateRange dr =  new DateRange();
        DateRange instance = new DateRange();
        instance.mergeDate(dr);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of isOverlapAny method, of class DateRange.
     */
    @Test
    public void testIsOverlapAny() {
        System.out.println("isOverlapAny");
        List<DateRange> conflicted_meeting_time = null;
        DateRange instance = new DateRange();
        boolean expResult = false;
        boolean result = instance.isOverlapAny(conflicted_meeting_time);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
