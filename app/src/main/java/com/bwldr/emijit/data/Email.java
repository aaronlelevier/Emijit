package com.bwldr.emijit.data;

/**
 * Class representing a single Firebase DB Email record
 */

public class Email {

    /** Valid email address */
    private String email;

    /**
     * Auto-generate datetime of 'now' in seconds. Is used to store
     * when the email was first recorded.
     * */
    private long date;

    public Email() {
    }

    public Email(String email) {
        this.email = email;
        this.date = System.currentTimeMillis();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDate() {
        return date;
    }
}
