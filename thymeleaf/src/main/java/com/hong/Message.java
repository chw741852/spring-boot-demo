package com.hong;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Calendar;

/**
 * Created by Hongwei on 2016/1/10.
 */
public class Message {
    private Long id;

    @NotEmpty(message = "Message is required.")
    private String message;

    @NotEmpty(message = "Summary is required.")
    private String summary;

    private Calendar created = Calendar.getInstance();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
