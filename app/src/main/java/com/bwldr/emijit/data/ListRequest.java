package com.bwldr.emijit.data;

/**
 * Represents a 'list-request' Firebase Record that Firebase will
 * listen on, and send an email with that User's email list.
 *
 * Essentially represents a queue because after the email list
 * is requested, the record is removed from the table, i.e. popped
 * off the queue, since it's already be processed.
 */

public class ListRequest {

    private String id;
    private String email;

    public ListRequest() {
    }

    public ListRequest(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
