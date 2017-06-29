package com.test.action.tcp.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by pangming on 2017/5/5.
 */
public class TimeClient {

    public static void main(String[] args) {
        PrintWriter out = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8090);
            out = new PrintWriter(socket.getOutputStream());
            out.print("hello bio");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                out.close();
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
