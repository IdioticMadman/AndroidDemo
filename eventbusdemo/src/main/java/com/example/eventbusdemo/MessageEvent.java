package com.example.eventbusdemo;

/**
 * Created by robert on 2016/5/25.
 */
public class MessageEvent {
    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
