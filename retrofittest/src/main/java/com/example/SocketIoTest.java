package com.example;

import java.net.URI;
import java.util.Map;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import io.socket.client.Socket;

/**
 * Created by robert on 2016/11/9.
 */

public class SocketIoTest {
    static Socket socket = null;

    public static void main(String[] args) {
        String socketUrl = "http://192.168.1.202:8080/YiZhuOA/sockjs/socketServer";
       /* try {
            socket = IO.socket(socketUrl);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("foo", "hi");
                    socket.disconnect();
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println(args[0].toString());
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println(args[0].toString());
                }
            });
            Socket connect = socket.connect();
            System.out.println(connect.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
            String uri = "ws://192.168.1.202:8080/YiZhuOA/socketServer";
            Session session = container.connectToServer(Client.class, new URI(uri)); // 连接会话
            String protocolVersion = session.getProtocolVersion();

            Map<String, String> pathParameters = session.getPathParameters();
            for (String str : pathParameters.keySet()) {
                System.out.println(pathParameters.get(str));
            }
            System.out.println("protocolVersion:" + protocolVersion);
            session.getBasicRemote().sendText("123132132131"); // 发送文本消息
            session.getBasicRemote().sendText("4564546");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
