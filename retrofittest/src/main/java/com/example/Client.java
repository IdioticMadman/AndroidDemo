package com.example;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * Created by robert on 2016/11/9.
 */

@ClientEndpoint
public class Client {

    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText("onOpen: 开始连接");
            System.out.println("Connected to endpoint: " + session.getBasicRemote());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
