package models;

import java.util.Date;

public class UserMessage {
    private String username;
    private String msg;
    private Date timestamp;

    public UserMessage(String username, String msg) {
        this.username = username;
        this.msg = msg;
        this.timestamp = new Date();
    }

    public UserMessage(String username, String msg, Date timestamp) {
        this.username = username;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public String getMsg() { return msg; }
    public Date getTimeStamp() { return timestamp; }
}

/*
 * In scala this would be:
 * case class UserMessage(username: String, msg: String, timestamp: Date = new Date())
 */