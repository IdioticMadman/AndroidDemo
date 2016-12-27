package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.SocketServer
 * @author: robert
 * @date: 2016-09-26 15:41
 */

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4567);
            Socket socket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            while (true) {
                String str = bufferedReader.readLine();
                System.out.println("client：" + str);

                printWriter.println("alarm");
                printWriter.flush();
                if (str.equals("end")) {
                    break;
                }
            }
            printWriter.close();
            bufferedReader.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
