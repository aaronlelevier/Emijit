package com.bwldr.emijit.data;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class EmailTest {

    @Test
    public void email() {
        String emailStr = "a@a.com";

        Email email = new Email(emailStr);

        assertEquals(email.getEmail(), emailStr);
    }

    @Test
    public void date() {
        Email email = new Email("foo@bar.com");

        Date date = new Date(email.getDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertTrue(cal.get(Calendar.YEAR) >= 2017);
        assertTrue(cal.get(Calendar.MONTH) >= 1);
    }
}
